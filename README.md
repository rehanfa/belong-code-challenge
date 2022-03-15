Spring Boot Microservices
---------------------
This is a Spring Boot Microservices Project

Description
-----------

- Customer detail service is used to provide endpoints so user can get all phone numbers, get all phone numbers of a
  single customer, and activate a phone number. This api is accessible via proxy service.
- Security service is used to provide endpoints to authenticate and authorise user. This api is accessible via proxy
  service.
- Proxy api serves as api gateway so user can access public and private endpoints.
- Discovery server is EurekaServer to register EurekaClients.
- All services are Spring Boot maven based projects.
- Run all these project in this order.
    1. Discovery
    2. Security
    3. Customer Detail
    4. Proxy

## Docker compose build, and run

- docker-compose build (to build all docker images)
- docker-compose up (to run all services)
- docker-compose down (to stop all services)
---------------------

1.Discovery
---------------------

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
---------------------

2.Security
-----------

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
 -----------
 
 
 3.Customer Detail
---------------------

Description
-----------

- Customer Service provides endpoints to get all phone numbers, get all phone numbers of a single customer, and activate
  a phone number
- Customer Service run as a EurekaClient.
- Customer Service is designed to access through Proxy service.

Endpoint Detail
-----------

- Endpoint /api/internal/v1/customer/all/phones return all phone numbers with pagination capability
- Endpoint /api/internal/v1/customer/name/{first-name} returns customer details
- Endpoint /api/internal/v1/customer/id/{customer-id} returns customer details
- Endpoint /api/internal/v1/phone/activate/{phone-id} activates phone number
- H2 in memory database is used as a database
- Customer details has been added as a initial data
- Sample Customer detail is:
- {
  "customerId": 1,
  "firstName": "firstName",
  "lastName": "lastName",
  "middleName": "middleName",
  "dateOfBirth": null,
  "addresses": [
  {
  "addressId": 1,
  "line1": "line1",
  "line2": null,
  "city": "city",
  "state": "vic",
  "postCode": "3000"
  }
  ],
  "phones": [
  {
  "phoneId": 1,
  "phoneNumber": "1111111111",
  "status": "INACTIVE",
  "modifiedDate": null,
  "createdDate": "2021-09-13T03:56:30.271+00:00"
  }
  ]
  }

## Requirements

- Implemented and tested using Java 8

- Tests require JUnit and Mockito

- Project dependencies and compiling managed by Maven

## Compile, Test, Run and Packaging

- Compile: mvn compile

- Test: mvn test

- Packaging: mvn package, compiled jar in *target/* folder

- Run using jar: java -jar customer-detail-0.0.1-SNAPSHOT.jar from *target/* folder

## Docker build, and run

- Steps to build individual service image or run docker-compose build from root directory
- mvn -N io.takari:maven:wrapper
- ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=telecom/customer-detail-service
- docker-compose up (to run all services)
- docker-compose down (to stop all services)
---------------------


4.Proxy
---------------------

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





