# Quick Reference Guide

## Quick Commands

### Docker (Recommended)
```bash
# Start everything
docker-compose up -d

# View logs
docker-compose logs -f

# Stop everything
docker-compose down
```

### Manual Start

#### Backend
```bash
cd backend
./mvnw spring-boot:run
```

#### Frontend
```bash
cd frontend
npm run dev
```

## Access URLs
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **API Docs**: http://localhost:8080/api/execute/languages

## Supported Languages
- ☕ Java 17
- 🐍 Python 3.x
- 🟨 JavaScript (Node.js)
- ⚙️ C++ (17)
- 🔧 C (11)

## Example API Requests

### Execute Python Code
```bash
curl -X POST http://localhost:8080/api/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "print(\"Hello, World!\")",
    "language": "python",
    "input": "",
    "saveToHistory": true
  }'
```

### Execute Java Code
```bash
curl -X POST http://localhost:8080/api/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }",
    "language": "java",
    "input": "",
    "saveToHistory": true
  }'
```

### Get Languages
```bash
curl http://localhost:8080/api/execute/languages
```

## Environment Variables

### Frontend (.env.local)
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

### Backend (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/online_compiler
    username: root
    password: root

code-executor:
  timeout: 10
  max-output-size: 10000
```

## Common Issues

### Port Already in Use
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Mac/Linux
lsof -ti:8080 | xargs kill -9
```

### MySQL Not Running
```bash
# Check MySQL status
docker ps | grep mysql

# Start MySQL only
docker-compose up -d mysql
```

### Clear Everything
```bash
# Frontend
cd frontend
rm -rf .next node_modules
npm install

# Backend
cd backend
./mvnw clean

# Docker
docker-compose down -v --rmi all
```

## Development Tips

### Hot Reload
- **Frontend**: Changes auto-reload in dev mode
- **Backend**: Use Spring DevTools or restart server

### Debug Mode
```bash
# Frontend with debug
npm run dev -- --debug

# Backend with debug
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### View Database
```bash
# Connect to MySQL in Docker
docker exec -it online-compiler-db mysql -uroot -proot online_compiler

# Or use MySQL Workbench
# Host: localhost
# Port: 3306
# User: root
# Password: root
```

## Production Build

### Frontend
```bash
cd frontend
npm run build
npm start
```

### Backend
```bash
cd backend
./mvnw clean package
java -jar target/online-compiler-1.0.0.jar
```

### Docker Production
```bash
docker-compose -f docker-compose.prod.yml up -d
```

## Testing Checklist
- [ ] Frontend loads at http://localhost:3000
- [ ] Backend responds at http://localhost:8080/api/execute/languages
- [ ] MySQL is accessible
- [ ] Java code execution works
- [ ] Python code execution works
- [ ] JavaScript code execution works
- [ ] C++ code execution works
- [ ] C code execution works
- [ ] Code sharing generates link
- [ ] Recent submissions visible

## Support
For detailed documentation, see:
- `README.md` - Overview and features
- `SETUP_GUIDE.md` - Detailed setup instructions
- `backend/README.md` - Backend specifics
