# Online Compiler - Backend

## Overview
This is the Java Spring Boot backend for the Online Multi-Language Compiler application.

## Features
- RESTful API for code execution
- Support for multiple programming languages
- MySQL database integration
- Code history and sharing functionality
- Secure code execution with timeout protection

## Requirements
- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+
- Language compilers/interpreters:
  - `javac` and `java` (JDK 17+)
  - `python` (Python 3.x)
  - `node` (Node.js)
  - `gcc` and `g++` (GCC compiler)

## Configuration

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE online_compiler;
```

2. Update `src/main/resources/application.yml` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/online_compiler
    username: your_username
    password: your_password
```

### Application Properties
Edit `application.yml` to configure:
- Server port (default: 8080)
- Database connection
- JWT secret
- Code execution timeout
- CORS allowed origins

## Running the Application

### Development
```bash
./mvnw spring-boot:run
```

### Production Build
```bash
./mvnw clean package
java -jar target/online-compiler-1.0.0.jar
```

### Docker
```bash
docker build -t online-compiler-backend .
docker run -p 8080:8080 online-compiler-backend
```

## API Endpoints

### Execute Code
```http
POST /api/execute
Content-Type: application/json

{
  "code": "print('Hello, World!')",
  "language": "python",
  "input": "",
  "saveToHistory": true
}
```

### Get Languages
```http
GET /api/execute/languages
```

### Get Shared Code
```http
GET /api/execute/share/{shareId}
```

### Get Recent Submissions
```http
GET /api/execute/recent
```

## Project Structure
```
src/
├── main/
│   ├── java/com/compiler/
│   │   ├── model/              # Entity classes
│   │   ├── repository/         # JPA repositories
│   │   ├── service/            # Business logic
│   │   ├── controller/         # REST controllers
│   │   ├── config/             # Configuration classes
│   │   └── OnlineCompilerApplication.java
│   └── resources/
│       └── application.yml     # Configuration file
└── test/                       # Test classes
```

## Security
- Spring Security configured for stateless API
- CORS enabled for frontend origin
- Input validation on all endpoints
- Code execution timeout protection

## Troubleshooting

### MySQL Connection Issues
- Verify MySQL is running
- Check credentials in `application.yml`
- Ensure database exists

### Code Execution Failures
- Verify all language compilers are installed
- Check temp directory permissions
- Review timeout settings

### Port Already in Use
Change the port in `application.yml`:
```yaml
server:
  port: 8081
```

## License
MIT License
