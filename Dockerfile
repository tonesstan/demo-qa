FROM maven:3.9.8-eclipse-temurin-22 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN apt-get update && apt-get install -y maven
COPY pom.xml .
COPY src /app/src
CMD ["mvn", "clean", "test"]