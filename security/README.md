Security
---------------------
This is a Security service

Description
-----------

- Security api provides authentication and authorisation services.
- Security api run as a EurekaClient
- Security api is designed to access through Proxy service.

Endpoint Detail
-----------

- login endpoint /api/login receives json {"userName": "user01", "password": "some-password" } and return JWT token
- hasAccess endpoint /api/{token}/{service} validates the access

## Docker build, and run

- Steps to build individual service image or run docker-compose build from root directory
- mvn -N io.takari:maven:wrapper
- ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=telecom/security-service
- docker-compose up (to run all services)
- docker-compose down (to stop all services)

## Requirements

- Implemented and tested using Java 8

- Tests require JUnit and Mockito

- Project dependencies and compiling managed by Maven

## Compile, Test, Run and Packaging

- Compile: mvn compile

- Test: mvn test

- Packaging: mvn package, compiled jar in *target/* folder

- Run using jar: java -jar security-0.0.1-SNAPSHOT.jar from *target/* folder
 
