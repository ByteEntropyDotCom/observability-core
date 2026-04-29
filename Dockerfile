# Stage 1: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar from the build stage (Maven)
COPY target/observability-core-1.0.0.jar app.jar

# Expose the observability port
EXPOSE 8083

# Run with optimized memory settings for 99.99% stability
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]