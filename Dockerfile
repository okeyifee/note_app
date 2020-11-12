FROM openjdk:latest

COPY pom.xml /build/

WORKDIR /build/

COPY src /build/src/

EXPOSE 8080

ENTRYPOINT ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev"]