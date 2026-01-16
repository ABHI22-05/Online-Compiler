package com.compiler.service;

import com.compiler.model.CodeExecutionRequest;
import com.compiler.model.CodeExecutionResponse;
import com.compiler.model.CodeSubmission;
import com.compiler.repository.CodeSubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeExecutorService {

    private final CodeSubmissionRepository submissionRepository;

    @Value("${code-executor.timeout:10}")
    private int timeout;

    @Value("${code-executor.temp-dir:./temp}")
    private String tempDir;

    @Value("${code-executor.max-output-size:10000}")
    private int maxOutputSize;

    public CodeExecutionResponse executeCode(CodeExecutionRequest request) {
        long startTime = System.currentTimeMillis();
        CodeSubmission submission = new CodeSubmission();
        submission.setCode(request.getCode());
        submission.setLanguage(request.getLanguage());
        submission.setInput(request.getInput());

        try {
            // Create unique directory for this execution
            String executionId = UUID.randomUUID().toString();
            Path executionDir = Paths.get(tempDir, executionId);
            Files.createDirectories(executionDir);

            CodeExecutionResponse response = switch (request.getLanguage().toLowerCase()) {
                case "java" -> executeJava(request, executionDir);
                case "python" -> executePython(request, executionDir);
                case "javascript", "js" -> executeJavaScript(request, executionDir);
                case "cpp", "c++" -> executeCpp(request, executionDir);
                case "c" -> executeC(request, executionDir);
                default -> throw new IllegalArgumentException("Unsupported language: " + request.getLanguage());
            };

            long executionTime = System.currentTimeMillis() - startTime;
            response.setExecutionTime(executionTime);

            // Save to database if requested
            if (request.isSaveToHistory()) {
                submission.setOutput(response.getOutput());
                submission.setError(response.getError());
                submission.setExecutionTime(executionTime);
                submission.setStatus(response.getStatus().equals("SUCCESS") ? CodeSubmission.ExecutionStatus.SUCCESS
                        : CodeSubmission.ExecutionStatus.ERROR);
                submission.setShareId(UUID.randomUUID().toString().substring(0, 8));
                submissionRepository.save(submission);
                response.setShareId(submission.getShareId());
            }

            // Cleanup
            deleteDirectory(executionDir);

            return response;

        } catch (Exception e) {
            log.error("Error executing code", e);
            submission.setError(e.getMessage());
            submission.setStatus(CodeSubmission.ExecutionStatus.ERROR);

            return CodeExecutionResponse.builder()
                    .output("")
                    .error(e.getMessage())
                    .executionTime(System.currentTimeMillis() - startTime)
                    .status("ERROR")
                    .build();
        }
    }

    private CodeExecutionResponse executeJava(CodeExecutionRequest request, Path workDir) throws Exception {
        // Extract class name from code
        String className = extractJavaClassName(request.getCode());
        if (className == null) {
            className = "Main";
        }

        // Write source file
        Path sourceFile = workDir.resolve(className + ".java");
        Files.writeString(sourceFile, request.getCode());

        // Compile - use just the filename since we're running in workDir
        ProcessResult compileResult = runProcess(workDir, timeout, "javac", className + ".java");
        if (compileResult.exitCode != 0) {
            return CodeExecutionResponse.builder()
                    .output("")
                    .error(compileResult.error)
                    .status("COMPILATION_ERROR")
                    .build();
        }

        // Execute
        ProcessResult runResult = runProcess(workDir, timeout, "java", className);
        if (!request.getInput().isBlank()) {
            // TODO: Handle input properly
        }

        return CodeExecutionResponse.builder()
                .output(truncateOutput(runResult.output))
                .error(runResult.error)
                .status(runResult.exitCode == 0 ? "SUCCESS" : "RUNTIME_ERROR")
                .build();
    }

    private CodeExecutionResponse executePython(CodeExecutionRequest request, Path workDir) throws Exception {
        Path sourceFile = workDir.resolve("script.py");
        Files.writeString(sourceFile, request.getCode());

        ProcessResult result = runProcess(workDir, timeout, "python", sourceFile.toString());

        return CodeExecutionResponse.builder()
                .output(truncateOutput(result.output))
                .error(result.error)
                .status(result.exitCode == 0 ? "SUCCESS" : "RUNTIME_ERROR")
                .build();
    }

    private CodeExecutionResponse executeJavaScript(CodeExecutionRequest request, Path workDir) throws Exception {
        Path sourceFile = workDir.resolve("script.js");
        Files.writeString(sourceFile, request.getCode());

        ProcessResult result = runProcess(workDir, timeout, "node", sourceFile.toString());

        return CodeExecutionResponse.builder()
                .output(truncateOutput(result.output))
                .error(result.error)
                .status(result.exitCode == 0 ? "SUCCESS" : "RUNTIME_ERROR")
                .build();
    }

    private CodeExecutionResponse executeCpp(CodeExecutionRequest request, Path workDir) throws Exception {
        Path sourceFile = workDir.resolve("program.cpp");
        Path outputFile = workDir.resolve("program.exe");
        Files.writeString(sourceFile, request.getCode());

        // Compile - use relative filenames
        ProcessResult compileResult = runProcess(workDir, timeout,
                "g++", "program.cpp", "-o", "program.exe");
        if (compileResult.exitCode != 0) {
            return CodeExecutionResponse.builder()
                    .output("")
                    .error(compileResult.error)
                    .status("COMPILATION_ERROR")
                    .build();
        }

        // Execute
        ProcessResult runResult = runProcess(workDir, timeout, "./program.exe");

        return CodeExecutionResponse.builder()
                .output(truncateOutput(runResult.output))
                .error(runResult.error)
                .status(runResult.exitCode == 0 ? "SUCCESS" : "RUNTIME_ERROR")
                .build();
    }

    private CodeExecutionResponse executeC(CodeExecutionRequest request, Path workDir) throws Exception {
        Path sourceFile = workDir.resolve("program.c");
        Path outputFile = workDir.resolve("program.exe");
        Files.writeString(sourceFile, request.getCode());

        // Compile - use relative filenames
        ProcessResult compileResult = runProcess(workDir, timeout,
                "gcc", "program.c", "-o", "program.exe");
        if (compileResult.exitCode != 0) {
            return CodeExecutionResponse.builder()
                    .output("")
                    .error(compileResult.error)
                    .status("COMPILATION_ERROR")
                    .build();
        }

        // Execute
        ProcessResult runResult = runProcess(workDir, timeout, "./program.exe");

        return CodeExecutionResponse.builder()
                .output(truncateOutput(runResult.output))
                .error(runResult.error)
                .status(runResult.exitCode == 0 ? "SUCCESS" : "RUNTIME_ERROR")
                .build();
    }

    private ProcessResult runProcess(Path workDir, int timeoutSeconds, String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(workDir.toFile());
            pb.redirectErrorStream(false);

            Process process = pb.start();

            // Read output in separate threads
            StringBuilder output = new StringBuilder();
            StringBuilder error = new StringBuilder();

            Thread outputThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                } catch (IOException e) {
                    log.error("Error reading output", e);
                }
            });

            Thread errorThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        error.append(line).append("\n");
                    }
                } catch (IOException e) {
                    log.error("Error reading error stream", e);
                }
            });

            outputThread.start();
            errorThread.start();

            boolean completed = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);

            if (!completed) {
                process.destroyForcibly();
                return new ProcessResult(-1, "", "Execution timed out after " + timeoutSeconds + " seconds");
            }

            outputThread.join();
            errorThread.join();

            return new ProcessResult(process.exitValue(), output.toString(), error.toString());

        } catch (Exception e) {
            log.error("Error running process", e);
            return new ProcessResult(-1, "", e.getMessage());
        }
    }

    private String extractJavaClassName(String code) {
        // Simple regex to extract public class name
        String[] lines = code.split("\n");
        for (String line : lines) {
            if (line.contains("public class")) {
                String[] parts = line.split("public class");
                if (parts.length > 1) {
                    String className = parts[1].trim().split("[\\s{]")[0];
                    return className;
                }
            }
        }
        return null;
    }

    private String truncateOutput(String output) {
        if (output.length() > maxOutputSize) {
            return output.substring(0, maxOutputSize) + "\n... (output truncated)";
        }
        return output;
    }

    private void deleteDirectory(Path directory) {
        try {
            if (Files.exists(directory)) {
                Files.walk(directory)
                        .sorted((a, b) -> b.compareTo(a))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                log.warn("Failed to delete: " + path, e);
                            }
                        });
            }
        } catch (IOException e) {
            log.warn("Failed to delete directory: " + directory, e);
        }
    }

    private record ProcessResult(int exitCode, String output, String error) {
    }
}
