FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build


COPY src ./src
COPY pom.xml .


RUN mvn clean package -DskipTests


FROM bellsoft/liberica-openjdk-debian:17
WORKDIR /app


COPY --from=build /build/target/update-manager-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]


EXPOSE 8090


