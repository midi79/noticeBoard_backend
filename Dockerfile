# Use a lightweight Java runtime image
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the pre-built JAR from GitHub Actions
COPY build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]