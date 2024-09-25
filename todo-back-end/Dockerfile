# Use OpenJDK as the base image
FROM openjdk:21-jdk

# Set the ARG JAR_FILE to point to the jar file in the target directory
ARG JAR_FILE=target/*.jar

# Copy the jar file to the Docker image
COPY ${JAR_FILE} app.jar

# Run the jar file when the Docker container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]
