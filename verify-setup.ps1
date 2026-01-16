Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Online Compiler - Startup Verification" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check Docker
Write-Host "Checking Docker..." -ForegroundColor Yellow
try {
    $dockerVersion = docker --version 2>$null
    if ($dockerVersion) {
        Write-Host "OK Docker is installed: $dockerVersion" -ForegroundColor Green
    }
}
catch {
    Write-Host "X Docker is not installed" -ForegroundColor Red
}

Write-Host ""

# Check Docker Compose
Write-Host "Checking Docker Compose..." -ForegroundColor Yellow
try {
    $composeVersion = docker-compose --version 2>$null
    if ($composeVersion) {
        Write-Host "OK Docker Compose: $composeVersion" -ForegroundColor Green
    }
}
catch {
    Write-Host "X Docker Compose not available" -ForegroundColor Red
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "LANGUAGE RUNTIMES" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check Java
Write-Host "Checking Java..." -ForegroundColor Yellow
try {
    $javaCheck = java -version 2>&1 | Select-String "version"
    if ($javaCheck) {
        Write-Host "OK Java is installed" -ForegroundColor Green
    }
}
catch {
    Write-Host "X Java not found" -ForegroundColor Red
}

# Check Python
Write-Host "Checking Python..." -ForegroundColor Yellow
try {
    $pythonCheck = python --version 2>&1
    if ($pythonCheck) {
        Write-Host "OK Python: $pythonCheck" -ForegroundColor Green
    }
}
catch {
    Write-Host "X Python not found" -ForegroundColor Yellow
}

# Check Node
Write-Host "Checking Node.js..." -ForegroundColor Yellow
try {
    $nodeCheck = node --version 2>&1
    if ($nodeCheck) {
        Write-Host "OK Node.js: $nodeCheck" -ForegroundColor Green
    }
}
catch {
    Write-Host "X Node.js not found" -ForegroundColor Red
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "NEXT STEPS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "To start with Docker:" -ForegroundColor Yellow
Write-Host "  docker-compose up -d" -ForegroundColor White
Write-Host ""  
Write-Host "Access at:" -ForegroundColor Cyan
Write-Host "  http://localhost:3000" -ForegroundColor White
Write-Host ""
