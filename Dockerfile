# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Run the application
CMD ["java", "-jar", "target/Ohjelmistotuotantoprojekti-1.0-SNAPSHOT.jar"]
