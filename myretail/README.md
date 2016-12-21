# myretail

Overview

myretail is a RESTful CRUD api designed to maintain product information.  

Spring Boot and Maven are used to create and build the controller services and cassandra is used to persist the data.

For quickly up and running with this application, perform the below two steps.

1) Run the database script( database.cql ) from repository in cassandra database instance and for customized database port mappings update cassandra.properties inside source appropriately.

2) mvn clean install and mvn spring-boot:run 


Service API

HTTP GET

Request

URL: http://hostname:port/v1/products/{id}
Method: GET
Content-Type: application/json

Response
Success
Status: 200
Content-Type: application/json
{
  "id": 13860428,
  "name": "The Big Lebowski [Blu-ray]",
  "current_price": {
    "value": 26.37,
    "currency_code": "USD"
  }
}

HTTP GET

Request

URL: http://hostname:port/v1/products
Method: GET
Content-Type: application/json

Response
Success
Status: 200
Content-Type: application/json
[
  {
    "id": 13860433,
    "name": "The Big Lebowski [Blu-ray]",
    "current_price": {
      "value": 26.37,
      "currency_code": "USD"
    }
  },
  {
    "id": 13860435,
    "name": "The Big Lebowski [Blu-ray]",
    "current_price": {
      "value": 26.37,
      "currency_code": "USD"
    }
  }
]


URL: http://hostname:port/v1/products/{invalid-id}
Method: GET
Content-Type: application/json

Not Found

Status: 404
Content-Type: application/json

{
  "message": "Invalid Product Id",
  "errorCode": "Known Error"
}


HTTP POST

Request 

URL:  http://hostname:port/v1/products
Method: POST
Content-Type: application/json
Body:
{ 
  "name": "The Big Lebowski [Blu-rayuu]",
  "current_price": {
    "value": 26.37,
    "currency_code": "USD"
  }
}
Response

Success

Status: 201
Content-Type: application/json
{
  "id": 13860428,
  "name": "The Big Lebowski [Blu-rayuu]",
  "current_price": {
    "value": 26.37,
    "currency_code": "USD"
  }
}



HTTP PUT

Request 

URL:  http://hostname:port/v1/products/{id}
Method: PUT
Body:

{ 
  "name": "The Big Lebowski [Blu-rayuu]",
  "current_price": {
    "value": 26.37,
    "currency_code": "USD"
  }
}
Response

Success

Status: 200
Content-Type: application/json

{
  "id": 13860428,
  "name": "The Big Lebowski [Blu-rayuu]",
  "current_price": {
    "value": 26.37,
    "currency_code": "USD"
  }
}



HTTP DELETE

Request 

URL:  http://hostname:port/v1/products/{id}
Method: DELETE
Body:
{
}
Response

Success

Status: 200
Content-Type: application/json

Product 13860442 deleted



Integration Testing

REST Assured is used to implement integration tests

Execution

Run integration test (myretail service api is required to be up and running):
$ mvn test -Dtest=myretail



