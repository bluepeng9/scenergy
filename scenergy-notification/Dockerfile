FROM eclipse-temurin:21-jdk-jammy
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=prod"]