FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/Client-0.0.1-SNAPSHOT.jar client.jar
ENTRYPOINT ["java", "-jar", "client.jar"]