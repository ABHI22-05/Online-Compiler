FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Install necessary compilers and runtimes
RUN apk add --no-cache \
    python3 \
    py3-pip \
    nodejs \
    npm \
    gcc \
    g++ \
    make

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build application
RUN ./mvnw package -DskipTests

# Create temp directory for code execution
RUN mkdir -p /app/temp

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "target/online-compiler-1.0.0.jar"]
