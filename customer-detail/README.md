Customer Detail
---------------------
This is a customer detail internal service.

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
 
