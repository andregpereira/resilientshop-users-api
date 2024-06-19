FROM eclipse-temurin:21-jre-alpine
LABEL authors="André Garcia"
WORKDIR /app
COPY target/*.jar /app/resilientshop-user.jar
ENTRYPOINT ["java","-jar","resilientshop-user.jar"]