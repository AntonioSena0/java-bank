FROM eclipse-temurin:17
LABEL maintainer="AntonioBSN"
WORKDIR /java-bank
COPY /target/*.jar /java-bank/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080