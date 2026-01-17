# Security Policy

## Supported Versions

We release patches for security vulnerabilities. Currently supported versions:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Reporting a Vulnerability

If you discover a security vulnerability within Online Compiler, please send an email to the maintainers. All security vulnerabilities will be promptly addressed.

**Please do not report security vulnerabilities through public GitHub issues.**

### What to Include

- Type of vulnerability
- Full paths of source file(s) related to the vulnerability
- Location of the affected source code (tag/branch/commit or direct URL)
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the vulnerability

### Response Timeline

- We will acknowledge receipt of your vulnerability report within 48 hours
- We will send an estimated timeline for a fix within 7 days
- We will notify you when the vulnerability is fixed

## Security Best Practices

When using this application:

1. **Code Execution**: Be aware that user code is executed on the server
2. **Input Validation**: Always validate and sanitize user inputs
3. **Timeouts**: Code execution has timeout protection
4. **Resource Limits**: Memory and CPU usage are limited per execution
5. **Isolation**: Each code execution runs in an isolated environment

## Known Security Considerations

- Code execution happens in the same environment as the backend
- Temporary files are cleaned up after execution
- H2 console should be disabled in production
- CORS is configured for specific origins

## Updates

Stay updated with security patches by:
- Watching this repository
- Checking releases regularly
- Following security advisories
