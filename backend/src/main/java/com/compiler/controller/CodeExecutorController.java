package com.compiler.controller;

import com.compiler.model.CodeExecutionRequest;
import com.compiler.model.CodeExecutionResponse;
import com.compiler.model.CodeSubmission;
import com.compiler.repository.CodeSubmissionRepository;
import com.compiler.service.CodeExecutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/execute")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "${cors.allowed-origins}")
public class CodeExecutorController {

    private final CodeExecutorService executorService;
    private final CodeSubmissionRepository submissionRepository;

    @PostMapping
    public ResponseEntity<CodeExecutionResponse> executeCode(
            @Valid @RequestBody CodeExecutionRequest request) {
        log.info("Executing code in language: {}", request.getLanguage());

        try {
            CodeExecutionResponse response = executorService.executeCode(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(CodeExecutionResponse.builder()
                            .error(e.getMessage())
                            .status("ERROR")
                            .build());
        } catch (Exception e) {
            log.error("Error executing code", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CodeExecutionResponse.builder()
                            .error("Internal server error")
                            .status("ERROR")
                            .build());
        }
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Map<String, String>>> getSupportedLanguages() {
        List<Map<String, String>> languages = List.of(
                Map.of("id", "java", "name", "Java", "version", "17"),
                Map.of("id", "python", "name", "Python", "version", "3.x"),
                Map.of("id", "javascript", "name", "JavaScript", "version", "Node.js"),
                Map.of("id", "cpp", "name", "C++", "version", "17"),
                Map.of("id", "c", "name", "C", "version", "11"));
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/share/{shareId}")
    public ResponseEntity<CodeSubmission> getSharedCode(@PathVariable String shareId) {
        return submissionRepository.findByShareId(shareId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<CodeSubmission>> getRecentSubmissions() {
        List<CodeSubmission> submissions = submissionRepository.findTop10ByOrderByCreatedAtDesc();
        return ResponseEntity.ok(submissions);
    }
}
