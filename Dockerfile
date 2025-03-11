FROM ubuntu:20.04

# Install OpenJDK 21 and required libraries for JavaFX
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    libgtk-3-0 \
    libasound2 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxxf86vm1 \
    libgl1-mesa-glx \
    libgl1-mesa-dri \
    x11-apps \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the fat JAR into the container
COPY target/ohjelmistotuotanto.jar /app/ohjelmistotuotanto.jar

# Set the DISPLAY environment variable to use the host's display server
ENV DISPLAY=host.docker.internal:0.0

# Run the JavaFX application
ENTRYPOINT ["java", "-jar", "/app/ohjelmistotuotanto.jar"]
