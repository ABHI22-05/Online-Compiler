# Online-Compiler

A full-stack online code compiler supporting multiple programming languages including Java, Python, JavaScript, C++, and C.

## 🚀 Features

- **Multi-language Support**: Execute code in Java, Python, JavaScript, C++, and C
- **Monaco Editor**: Professional code editor with syntax highlighting
- **Real-time Execution**: Fast code compilation and execution
- **Code History**: Save and retrieve previous code submissions
- **Share Code**: Generate unique share links for your code
- **Responsive UI**: Modern, responsive interface built with Next.js
- **RESTful API**: Clean Spring Boot backend with comprehensive API

## 📋 Tech Stack

### Frontend
- **Next.js 14** - React framework
- **TypeScript** - Type-safe development
- **Monaco Editor** - VS Code's editor
- **Tailwind CSS** - Utility-first styling

### Backend
- **Java Spring Boot 3.2.1** - Backend framework
- **Spring Data JPA** - Database ORM
- **H2/MySQL** - Database support
- **Maven** - Dependency management
- **Spring Security** - API security

## 🛠️ Installation & Setup

### Prerequisites
- **Node.js** 18+ and npm
- **Java** 17+
- **Maven** (or use included Maven wrapper)
- Language compilers/interpreters:
  - `javac` and `java` (JDK 17+)
  - `python` (Python 3.x)
  - `node` (Node.js)
  - `gcc` and `g++` (GCC compiler)

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

The frontend will run on `http://localhost:3000`

### Backend Setup

```bash
cd backend
# Windows
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=h2

# Linux/Mac
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=h2
```

The backend will run on `http://localhost:8080/api`

## 🔧 Configuration

### Backend Configuration

The backend supports both H2 (in-memory) and MySQL databases:

**H2 Database (Development)**
```bash
# Run with H2 profile
mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=h2
```

**MySQL Database (Production)**
1. Create MySQL database:
```sql
CREATE DATABASE online_compiler;
```

2. Update `backend/src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/online_compiler
    username: your_username
    password: your_password
```

## 📚 API Documentation

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

### Get Supported Languages
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

## 📁 Project Structure

```
OnlineCompiler/
├── frontend/               # Next.js frontend
│   ├── src/
│   │   ├── app/           # App router pages
│   │   ├── components/    # React components
│   │   └── lib/           # Utilities
│   └── package.json
│
├── backend/               # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/compiler/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── model/
│   │   │   │       └── repository/
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── pom.xml
│
└── README.md
```

## 🚀 Deployment

### Using Docker

```bash
# Build and run backend
cd backend
docker build -t online-compiler-backend .
docker run -p 8080:8080 online-compiler-backend

# Build and run frontend
cd frontend
docker build -t online-compiler-frontend .
docker run -p 3000:3000 online-compiler-frontend
```

## 🔒 Security Features

- Spring Security configuration
- CORS enabled for frontend origin
- Input validation on all endpoints
- Code execution timeout protection
- Isolated execution environments

## 🐛 Troubleshooting

### Backend Issues

**MySQL Connection Error**
- Verify MySQL is running
- Check credentials in `application.yml`
- Ensure database exists

**Code Execution Failures**
- Verify all language compilers are installed and in PATH
- Check temp directory permissions
- Review timeout settings

### Frontend Issues

**Backend Connection Error**
- Ensure backend is running on port 8080
- Check CORS configuration
- Verify API endpoint URL

## 📝 License

MIT License

## 👥 Contributors

- Your Name (@ABHI22-05)

## 🙏 Acknowledgments

Built with Spring Boot, Next.js, and Monaco Editor
