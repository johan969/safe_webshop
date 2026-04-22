FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/safe_webshop-1.0.2.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]