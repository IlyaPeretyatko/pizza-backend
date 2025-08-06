FROM gradle:8.13.0-jdk21-jammy AS build

WORKDIR /pizza-menu

COPY . .

RUN gradle build

FROM openjdk:21

COPY --from=build /pizza-menu/build/libs/pizza-menu-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "pizza-menu-0.0.1-SNAPSHOT.jar", "--server.port=8082"]
