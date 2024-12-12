fix this the jar files are built to the target folder # Use the OpenJDK 21 slim image as the base
FROM openjdk:21-jdk-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Build the project and package it as a JAR (skip tests for build)
RUN apt-get update && apt-get install -y maven && \
    mvn clean package && mvn clean install

# Use a lightweight image for the runtime
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/expense-tracker-0.0.1-SNAPSHOT.jar expense-tracker.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "expense-tracker-0.0.1-SNAPSHOT.jar", "expense-tracker.jar"]
