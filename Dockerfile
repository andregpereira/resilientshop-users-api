FROM eclipse-temurin:17-jdk-alpine
LABEL authors="André Garcia"
WORKDIR /app
COPY target/*.jar /app/resilientshop-user.jar
ENTRYPOINT ["java","-jar","resilientshop-user.jar"]
EXPOSE 8090
