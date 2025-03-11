# Use a lightweight OpenJDK 21 version
FROM openjdk:21-jdk-slim

# Update package list and install necessary libraries (GUI support)
RUN apt-get update && apt-get install -y --no-install-recommends \
    libgtk-3-0 \
    libasound2 \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Download and extract JavaFX SDK
RUN wget https://download2.gluonhq.com/openjfx/21.0.0/openjfx-21.0.0_linux-x64_bin-sdk.zip && \
    unzip openjfx-21.0.0_linux-x64_bin-sdk.zip -d /opt && \
    rm openjfx-21.0.0_linux-x64_bin-sdk.zip

# Set JavaFX environment variables
ENV PATH_TO_FX=/opt/javafx-sdk-21.0.0/lib
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 --add-modules=javafx.controls,javafx.fxml"

# Create directory for the application
WORKDIR /app

# Copy the JAR file
COPY target/ohjelmistotuotanto.jar my-javafx-app.jar

# Run the application
CMD ["java", "--module-path", "/opt/javafx-sdk-21.0.0/lib", "-jar", "my-javafx-app.jar"]
