Proxy
---------------------
This is a proxy rest service

Description
-----------

- Proxy service provides endpoint to login user
- Proxy service provides endpoints to get all phone numbers, get all phone numbers of a single customer, and activate a
  phone number
- Proxy service runs on port 8081.
- Proxy service run as a EurekaClient and serve as api gateway.
- Proxy service contains public and private endpoints, where private endpoint requires JWT to access. Header value
  Authorization: Bearer <JWT Token> is required for private endpoint.
- Spring security has been used for authentication and authorisation.
- user01 has been added to use this application with required role.

Endpoint Detail
-----------

- Endpoint /api/public/login receives json e.g., {"userName": "user01", "password": "some-password" } and return JWT
  token
- Endpoint /api/private/v1/customer/all/phones return all phone numbers with pagination capability
- Endpoint /api/private/v1/customer/name/{first-name} returns Customer details
- Endpoint /api/private/v1/customer/id/{customer-id} returns Customer details
- Endpoint /api/private/v1/phone/activate/{phone-id} activates phone number

## Compile, Test, Run and Packaging

- Compile: mvn compile

- Test: mvn test

- Packaging: mvn package, compiled jar in *target/* folder

- Run using jar: java -jar proxy-0.0.1-SNAPSHOT.jar from *target/* folder

## Docker build, and run

- Steps to build individual service image or run docker-compose build from root directory
- mvn -N io.takari:maven:wrapper
- ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=telecom/proxy-service
- docker-compose up (to run all services)
- docker-compose down (to stop all services)



