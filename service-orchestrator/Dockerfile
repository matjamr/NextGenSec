FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY .env .env
RUN ./mvnw dependency:resolve

COPY ../api api

RUN ./mvnw clean install api

COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
WORKDIR app
COPY --from=build target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
