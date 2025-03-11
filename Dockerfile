# Use OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Install necessary libraries including xvfb and x11vnc
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
    x11vnc \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Set environment variable to force software rendering in JavaFX (prism)
ENV _JAVA_OPTIONS="-Dprism.order=sw -Dprism.verbose=true"

# Install your application
WORKDIR /app
COPY pom.xml /app/
COPY src /app/src

# Build the application
RUN mvn clean package dependency:copy-dependencies

# Expose VNC port for remote access
EXPOSE 5900

# Run xvfb and x11vnc in the background, then start your JavaFX application
CMD ["bash", "-c", "xvfb-run -n 99 -s '-screen 0 1024x768x24' x11vnc -create -forever & java --module-path /app/target/lib --add-modules javafx.controls,javafx.fxml -jar target/ohjelmistotuotanto.jar"]
