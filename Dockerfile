FROM eclipse-temurin:17-jdk
LABEL maintainer="AntonioBSN"
WORKDIR /java-bank
COPY /target/*.jar /java-bank/app.jar
CMD ["java", "-jar", "app.jar"]