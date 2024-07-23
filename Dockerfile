## Stage 1: Build the API module
#FROM openjdk:17-jdk-slim AS build-api
#WORKDIR /api
#ARG API_SOURCE_DIR=api
#RUN apt-get update && apt-get install -y maven
#
#
#COPY ${API_SOURCE_DIR}/pom.xml ./
#COPY ${API_SOURCE_DIR}/src ./src
#RUN mvn clean install -DskipTests
#
## Stage 2: Build the main application
#FROM openjdk:17-jdk-slim AS build-app
#ARG APP_SOURCE_DIR=service-orchestrator
#RUN apt-get update && apt-get install -y maven
#WORKDIR /app
#
#COPY ${APP_SOURCE_DIR}/pom.xml ./
#COPY ${APP_SOURCE_DIR}/src ./src
#COPY --from=build-api /api/target/api-*.jar ./lib/
#COPY ${APP_SOURCE_DIR}/.env .env
#RUN mvn clean install -DskipTests
#
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#
#COPY --from=build-app /app/target/*.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Stage 1: Build the API module
FROM openjdk:17-jdk-slim AS build-app
RUN apt-get update && apt-get install -y maven
WORKDIR /app
ARG API_SOURCE_DIR=api
ARG APP_SOURCE_DIR=service-orchestrator

# Prepare folder structure
COPY ${API_SOURCE_DIR}/pom.xml ./api/
COPY ${API_SOURCE_DIR}/src ./api/src

COPY ${APP_SOURCE_DIR}/pom.xml ./service/
COPY ${APP_SOURCE_DIR}/src ./service/src
COPY ${APP_SOURCE_DIR}/.env .env

# Execute clean installs
WORKDIR /app/api
RUN mvn clean install -DskipTests -U

WORKDIR /app/service
RUN mvn clean install -DskipTests -U

WORKDIR /app
RUN cp ./service/target/*.jar ./app.jar

RUN ls ./

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# run this shit with
# docker build --build-arg API_SOURCE_DIR=api --build-arg APP_SOURCE_DIR=service-orchestrator -t service-orchestrator --progress=plain --no-cache .