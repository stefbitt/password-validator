FROM openjdk:21-alpine

WORKDIR /app

COPY target/password-validator.jar app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "app.jar"]
