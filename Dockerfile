FROM eclipse-temurin:latest

WORKDIR /app

COPY target/classes/planilhas/template.CSV  /app/planilhas/template.CSV
COPY target/*.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]