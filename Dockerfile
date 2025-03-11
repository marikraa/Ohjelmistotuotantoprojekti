# Use a lightweight OpenJDK 21 version
FROM openjdk:21-jdk-slim

# Update package list and install necessary libraries (GUI support)
RUN apt-get update && apt-get install -y --no-install-recommends \
    maven \
    libgtk-3-0 \
    libasound2 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxxf86vm1 \
    libgl1-mesa-glx \
    libgl1-mesa-dri \
    x11-xserver-utils \
    && rm -rf /var/lib/apt/lists/*

# Set environment variables for JavaFX
ENV _JAVA_OPTIONS="-Dprism.order=sw -Dprism.verbose=true"

# Create directory for the application
WORKDIR /app

# Copy the Maven project files
COPY pom.xml /app/
COPY src /app/src

# Build the application and dependencies
RUN mvn clean package dependency:copy-dependencies

# Run the application
CMD ["java", "--module-path", "/app/target/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "target/ohjelmistotuotanto.jar"]
