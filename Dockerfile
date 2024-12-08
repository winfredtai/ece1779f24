FROM amazoncorretto:21-al2023-headless
WORKDIR /app
COPY target/ece1779f24-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
