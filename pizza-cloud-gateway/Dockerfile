FROM gradle:8.13.0-jdk21-alpine AS build

WORKDIR /pizza-cloud-gateway

COPY . .

RUN gradle build

FROM openjdk:21

COPY --from=build /pizza-cloud-gateway/build/libs/pizza-cloud-gateway-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "pizza-cloud-gateway-0.0.1-SNAPSHOT.jar", "--server.port=8880"]
