FROM maven:3.9.4-amazoncorretto-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG EUREKA_SERVER=localhost
ARG RABBITMQ_SERVER=localhost

ENTRYPOINT java -jar -Dspring.profiles.active=production app.jar