# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application jar file into the container
COPY target/WealthWatch-0.0.1-SNAPSHOT.jar /app/WealthWatch-0.0.1-SNAPSHOT.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/WealthWatch-0.0.1-SNAPSHOT.jar"]
