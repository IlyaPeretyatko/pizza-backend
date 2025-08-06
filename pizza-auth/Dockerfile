FROM gradle:8.13.0-jdk21-jammy AS build

WORKDIR /pizza-auth

COPY . .

RUN gradle build

FROM openjdk:21

COPY --from=build /pizza-auth/build/libs/pizza-auth-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "pizza-auth-0.0.1-SNAPSHOT.jar", "--server.port=8081"]
