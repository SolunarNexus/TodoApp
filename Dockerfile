############ BUILD STAGE ############
FROM maven:3.9.11-eclipse-temurin-21-alpine AS build
COPY  . /app
WORKDIR /app
RUN mvn clean install -DskipTests && cp /app/target/*.jar /app/app.jar


FROM openjdk:21-ea
VOLUME /tmp
COPY --from=build /app/app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]