# Use a lightweight OpenJDK 21 version
FROM openjdk:21-jdk-slim

# Update package list and install necessary libraries (GUI support)
RUN apt-get update && apt-get install -y --no-install-recommends \
    libgtk-3-0 \
    libasound2 \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Create directory for the application
WORKDIR /app

# Copy the Maven project files
COPY pom.xml /app/
COPY src /app/src

# Build the application and dependencies
RUN mvn clean package dependency:copy-dependencies

# Run the application
CMD ["java", "--module-path", "/app/target/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "target/ohjelmistotuotanto.jar"]
