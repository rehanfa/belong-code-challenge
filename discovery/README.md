Discovery
---------------------
This is a Discovery server

Description
-----------

- Discovery sever used EurekaServer so other EurekaClients can register.
- Discovery sever runs on port 8761.

## Requirements

- Implemented and tested using Java 8

- Project dependencies and compiling managed by Maven

## Compile, Test, Run and Packaging

- Compile: mvn compile

- Packaging: mvn package, compiled jar in *target/* folder

- Run using jar: java -jar discovery-0.0.1-SNAPSHOT.jar from *target/* folder

## Docker build, and run

- Steps to build individual service image or run docker-compose build from root directory
- mvn -N io.takari:maven:wrapper
- ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=telecom/discovery-service
- docker-compose up (to run all services)
- docker-compose down (to stop all services)
