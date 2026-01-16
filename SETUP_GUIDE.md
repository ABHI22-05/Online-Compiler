# Setup and Installation Guide

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Docker Setup (Recommended)](#docker-setup-recommended)
3. [Manual Setup](#manual-setup)
4. [Configuration](#configuration)
5. [Running the Application](#running-the-application)
6. [Testing](#testing)
7. [Troubleshooting](#troubleshooting)

## Prerequisites

### Required Software

#### For Docker Deployment
- [Docker Desktop](https://www.docker.com/products/docker-desktop) (Windows/Mac)
- Docker Compose (included with Docker Desktop)

#### For Manual Setup
##### Backend
- [Java JDK 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/download.cgi) (or use included wrapper)
- [MySQL 8.0+](https://dev.mysql.com/downloads/mysql/)

##### Frontend
- [Node.js 20+](https://nodejs.org/)
- npm (included with Node.js)

##### Language Compilers/Interpreters
- **Java**: JDK 17+ (already required for backend)
- **Python**: Python 3.x - [Download](https://www.python.org/downloads/)
- **Node.js**: Already installed for frontend
- **C/C++**: 
  - Windows: [MinGW-w64](https://www.mingw-w64.org/) or [MSYS2](https://www.msys2.org/)
  - Mac: Xcode Command Line Tools (`xcode-select --install`)
  - Linux: `sudo apt-get install build-essential`

## Docker Setup (Recommended)

### Step 1: Install Docker
1. Download and install Docker Desktop from [docker.com](https://www.docker.com/products/docker-desktop)
2. Start Docker Desktop
3. Verify installation:
   ```bash
   docker --version
   docker-compose --version
   ```

### Step 2: Build and Run
```bash
# Clone the repository
git clone <repository-url>
cd OnlineCompiler

# Build and start all services
docker-compose up --build

# Or run in detached mode
docker-compose up -d
```

### Step 3: Access the Application
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api
- MySQL: localhost:3306

### Step 4: View Logs
```bash
# View all logs
docker-compose logs

# View specific service logs
docker-compose logs frontend
docker-compose logs backend
docker-compose logs mysql

# Follow logs
docker-compose logs -f
```

### Step 5: Stop Services
```bash
# Stop services
docker-compose stop

# Stop and remove containers
docker-compose down

# Stop and remove containers, volumes, and images
docker-compose down -v --rmi all
```

## Manual Setup

### Backend Setup

#### Step 1: Install MySQL
1. Download and install MySQL 8.0+
2. Start MySQL server
3. Create database:
   ```sql
   CREATE DATABASE online_compiler;
   ```

#### Step 2: Configure Backend
1. Navigate to backend directory:
   ```bash
   cd backend
   ```

2. Update `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/online_compiler
       username: your_mysql_username
       password: your_mysql_password
   ```

#### Step 3: Build and Run Backend
```bash
# Using Maven wrapper (recommended)
./mvnw clean install
./mvnw spring-boot:run

# Or using system Maven
mvn clean install
mvn spring-boot:run
```

Backend will start on http://localhost:8080

### Frontend Setup

#### Step 1: Install Dependencies
```bash
cd frontend
npm install
```

#### Step 2: Configure Environment
Create `.env.local` file in the frontend directory:
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

#### Step 3: Run Development Server
```bash
npm run dev
```

Frontend will start on http://localhost:3000

## Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.yml`:

```yaml
# Server Configuration
server:
  port: 8080

# Database Configuration
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/online_compiler
    username: root
    password: your_password

# Code Execution Configuration
code-executor:
  timeout: 10          # Execution timeout in seconds
  max-memory: 256m     # Maximum memory
  max-output-size: 10000  # Maximum output characters

# CORS Configuration
cors:
  allowed-origins: http://localhost:3000
```

### Frontend Configuration

Create/edit `frontend/.env.local`:
```env
# Backend API URL
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

## Running the Application

### Development Mode

#### Terminal 1: Backend
```bash
cd backend
./mvnw spring-boot:run
```

#### Terminal 2: Frontend
```bash
cd frontend
npm run dev
```

### Production Mode

#### Backend
```bash
cd backend
./mvnw clean package
java -jar target/online-compiler-1.0.0.jar
```

#### Frontend
```bash
cd frontend
npm run build
npm start
```

## Testing

### Test Backend API

#### Using curl
```bash
# Test health
curl http://localhost:8080/api/execute/languages

# Execute code
curl -X POST http://localhost:8080/api/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "print(\"Hello, World!\")",
    "language": "python",
    "input": "",
    "saveToHistory": true
  }'
```

#### Using your browser
Navigate to:
- http://localhost:8080/api/execute/languages
- http://localhost:8080/api/execute/recent

### Test Frontend
1. Open http://localhost:3000
2. Select a language
3. Write some code
4. Click "Run Code"
5. Verify output appears

## Troubleshooting

### Backend Issues

#### Port Already in Use
```bash
# Windows: Find and kill process on port 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Mac/Linux
lsof -ti:8080 | xargs kill -9
```

Or change the port in `application.yml`:
```yaml
server:
  port: 8081
```

#### MySQL Connection Failed
- Verify MySQL is running
- Check credentials in `application.yml`
- Ensure database `online_compiler` exists
- Check MySQL is listening on port 3306

#### Compiler Not Found
Install the required compiler:
- **Python**: Add to PATH after installation
- **GCC/G++**: Install MinGW or build-essential
- **Node.js**: Already installed for frontend

### Frontend Issues

#### Module Not Found
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

#### Build Errors
```bash
cd frontend
rm -rf .next
npm run build
```

#### API Connection Failed
- Verify backend is running on port 8080
- Check `.env.local` has correct API URL
- Verify CORS is configured in backend
- Check browser console for errors

### Docker Issues

#### Container Won't Start
```bash
# View logs
docker-compose logs backend
docker-compose logs mysql

# Rebuild containers
docker-compose down
docker-compose up --build
```

#### MySQL Container Issues
```bash
# Remove volume and restart
docker-compose down -v
docker-compose up -d mysql
# Wait for MySQL to be ready, then start other services
docker-compose up backend frontend
```

#### Port Already in Use
Change ports in `docker-compose.yml`:
```yaml
services:
  backend:
    ports:
      - "8081:8080"  # Change 8081 to any available port
  frontend:
    ports:
      - "3001:3000"  # Change 3001 to any available port
```

## Next Steps

After successful setup:
1. Test all supported languages (Java, Python, JavaScript, C++, C)
2. Try the code sharing feature
3. Customize the UI in `frontend/app/globals.css`
4. Add more languages by following the customization guide in README.md
5. Set up production deployment

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Next.js Documentation](https://nextjs.org/docs)
- [Docker Documentation](https://docs.docker.com/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

## Getting Help

If you encounter issues not covered here:
1. Check the main README.md
2. Review backend/README.md
3. Search for similar issues on GitHub
4. Open a new issue with:
   - Your OS and version
   - Error messages
   - Steps to reproduce
   - Logs from the failing component
