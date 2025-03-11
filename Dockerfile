FROM ubuntu:20.04

RUN apt-get update && apt-get install -y openjdk-21-jdk openjfx x11-apps

WORKDIR /app


COPY target/ohjelmistotuotanto.jar /app/ohjelmistotuotanto.jar

ENV DISPLAY=host.docker.internal:0.0

# Run the JavaFX application
ENTRYPOINT ["java", "-jar", "/app/ohjelmistotuotanto.jar"]
