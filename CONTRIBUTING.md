# Contributing to Online Compiler

Thank you for your interest in contributing to the Online Compiler project! 🎉

## How to Contribute

### 1. Fork the Repository
Click the "Fork" button at the top right of the repository page:
```
https://github.com/ABHI22-05/Online-Compiler
```

### 2. Clone Your Fork
```bash
git clone https://github.com/YOUR-USERNAME/Online-Compiler.git
cd Online-Compiler
```

### 3. Create a Feature Branch
```bash
git checkout -b feature/your-feature-name
# Or for bug fixes:
git checkout -b fix/bug-description
```

### 4. Make Your Changes
- Write clean, documented code
- Follow existing code style
- Test your changes thoroughly

### 5. Commit Your Changes
```bash
git add .
git commit -m "Add: Clear description of your changes"
```

Commit message format:
- `Add:` for new features
- `Fix:` for bug fixes
- `Update:` for improvements to existing features
- `Docs:` for documentation changes

### 6. Push to Your Fork
```bash
git push origin feature/your-feature-name
```

### 7. Create a Pull Request
1. Go to your fork on GitHub
2. Click "Pull Request" button
3. Select your feature branch
4. Fill in the PR template with details
5. Submit the pull request

## Pull Request Guidelines

### Before Submitting
- [ ] Code compiles without errors
- [ ] All tests pass
- [ ] Code follows project style
- [ ] Documentation is updated
- [ ] Commit messages are clear

### PR Description Should Include
- **What**: What changes did you make?
- **Why**: Why were these changes necessary?
- **How**: How did you implement the solution?
- **Testing**: How did you test the changes?

### Example PR Description
```markdown
## Description
Added support for Rust programming language execution

## Motivation
Many users requested Rust support for competitive programming practice

## Changes
- Added Rust executor service
- Updated language dropdown to include Rust
- Added Rust syntax highlighting in Monaco Editor
- Updated documentation

## Testing
- Tested basic Rust programs
- Verified compilation errors are displayed correctly
- Tested with standard input/output
```

## Development Setup

### Frontend
```bash
cd frontend
npm install
npm run dev
```

### Backend
```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=h2
```

## Code Style

### Frontend (TypeScript/React)
- Use TypeScript for type safety
- Follow React functional component patterns
- Use meaningful variable names
- Add JSDoc comments for complex functions

### Backend (Java)
- Follow Java naming conventions
- Use Lombok to reduce boilerplate
- Add JavaDoc for public methods
- Keep methods focused and single-purpose

## Areas for Contribution

### 🎯 High Priority
- [ ] Add more programming languages (Go, Rust, Ruby, etc.)
- [ ] Implement user authentication
- [ ] Add code sharing with syntax highlighting
- [ ] Improve error messages for compilation failures

### 🛠️ Medium Priority
- [ ] Add dark/light theme toggle
- [ ] Implement code templates for common patterns
- [ ] Add execution time and memory usage metrics
- [ ] Support for multiple test cases

### 🎨 Nice to Have
- [ ] Code formatter integration
- [ ] Collaborative coding features
- [ ] Export code as Gist
- [ ] Mobile responsive improvements

## Questions?

Feel free to:
- Open an issue for bugs or feature requests
- Join discussions in existing issues
- Ask questions in pull request comments

## Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Help others learn and grow

Thank you for contributing! 🚀
