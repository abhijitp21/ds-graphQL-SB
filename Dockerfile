# Use Eclipse Temurin JDK 17 as the base image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the application's jar (built later) into the container
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 8081

# Command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
