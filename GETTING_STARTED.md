# 🚀 Getting Started with Online Compiler

Welcome! This guide will get you up and running with the Online Multi-Language Compiler in minutes.

## What You've Got

A complete, production-ready online code compiler with:
- ✅ **Frontend**: Beautiful Next.js UI with Monaco Editor
- ✅ **Backend**: Robust Java Spring Boot REST API
- ✅ **Database**: MySQL for code history
- ✅ **Docker**: Ready for containerized deployment

## Quick Setup (3 Steps)

### Option 1: Docker (Recommended - Easiest!)

```bash
# 1. Make sure Docker Desktop is installed and running
# 2. Navigate to project directory
cd C:\Projects\OnlineCompiler

# 3. Start everything
docker-compose up -d

# 4. Open your browser
# http://localhost:3000
```

That's it! Everything will be running including MySQL database.

### Option 2: Manual Setup

#### Prerequisites Check
Run the verification script:
```powershell
.\verify-setup.ps1
```

This will tell you what's installed and what's missing.

#### Required Installations
- **Java 17+**: [Download](https://adoptium.net/)
- **Node.js 20+**: [Download](https://nodejs.org/)
- **MySQL 8+**: [Download](https://dev.mysql.com/downloads/mysql/)
- **Python 3.x**: [Download](https://www.python.org/) (for Python execution)

#### Start Backend
```powershell
# Terminal 1 - Backend
cd backend

# Create MySQL database first:
# mysql -u root -p
# CREATE DATABASE online_compiler;
# exit;

# Start backend
./mvnw spring-boot:run
```

#### Start Frontend
```powershell
# Terminal 2 - Frontend  
cd frontend
npm install
npm run dev
```

## Your First Code Execution

1. Open http://localhost:3000
2. Select a language (Java, Python, JavaScript, C++, or C)
3. Write your code (or use the default Hello World)
4. Click "Run Code"
5. See the output!

## Example Codes to Try

### Java
```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from Java!");
    }
}
```

### Python
```python
def greet(name):
    return f"Hello, {name}!"

print(greet("World"))
```

### JavaScript
```javascript
const greet = (name) => `Hello, ${name}!`;
console.log(greet("World"));
```

### C++
```cpp
#include <iostream>
using namespace std;

int main() {
    cout << "Hello from C++!" << endl;
    return 0;
}
```

## Testing the Sharing Feature

1. Write and run some code
2. After successful execution, you'll see a "Share ID"
3. Click "Copy Link" to get a shareable URL
4. Share this URL with others!

## Common Issues & Solutions

### Backend won't start?
- **Check MySQL**: Make sure it's running and database exists
- **Port 8080 busy**: Change port in `backend/src/main/resources/application.yml`
- **Java not found**: Install Java 17+

### Frontend won't start?
- **Port 3000 busy**: Change port with `npm run dev -- -p 3001`
- **Module errors**: Delete `node_modules` and run `npm install` again

### Code execution fails?
- **Language not installed**: Install the compiler/interpreter
- **Timeout**: Increase timeout in `application.yml`

## Project Structure

```
OnlineCompiler/
├── frontend/          # Next.js app
│   ├── app/
│   │   └── page.tsx  # Main compiler UI
│   └── lib/          # Config & types
│
├── backend/          # Spring Boot app
│   └── src/main/
│       ├── java/     # Java code
│       └── resources/# Config files
│
├── README.md         # Full documentation
├── SETUP_GUIDE.md    # Detailed setup
└── QUICK_START.md    # Quick reference
```

## Development Workflow

### Making Changes

**Frontend Changes:**
- Edit files in `frontend/app/` or `frontend/lib/`
- Changes auto-reload in dev mode
- Build for production: `npm run build`

**Backend Changes:**
- Edit files in `backend/src/main/java/`
- Restart the backend to see changes
- Or use Spring DevTools for hot reload

**Database Changes:**
- Connection string: `backend/src/main/resources/application.yml`
- Tables auto-created by Hibernate

## Stopping the Application

### Docker
```bash
docker-compose down
```

### Manual
- Press `Ctrl+C` in both terminal windows

## What's Next?

### Learn More
- Read [README.md](README.md) for full documentation
- Check [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) for architecture details
- See [API Documentation](README.md#api-documentation) for API usage

### Customize
- Change colors in `frontend/app/globals.css`
- Add new languages by updating `CodeExecutorService.java`
- Modify timeout/limits in `application.yml`

### Deploy
- Use Docker Compose for production
- Deploy frontend to Vercel/Netlify
- Deploy backend to AWS/Azure/GCP
- Use managed MySQL or keep Docker

## Support & Documentation

📖 **Full Docs**: [README.md](README.md)
🔧 **Setup Guide**: [SETUP_GUIDE.md](SETUP_GUIDE.md)
⚡ **Quick Ref**: [QUICK_START.md](QUICK_START.md)
📊 **Architecture**: [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)

## Need Help?

1. Check the troubleshooting sections in the docs
2. Run `.\verify-setup.ps1` to check your setup
3. Review error messages in terminal
4. Check logs: `docker-compose logs -f`

---

**Ready to code? Start the app and go to http://localhost:3000** 🎉

Happy Coding! 💻
