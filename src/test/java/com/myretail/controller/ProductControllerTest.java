package com.myretail.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.http.ContentType;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class ProductControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductControllerTest.class);
	
	/**
	 * TODO
	 * construct url resources
	 * add more test case scenarios
	 */
	 
    @Test
    public void testGetProduct_Success() {
       given().
        when().
            get("http://localhost:8080/v1/products/13860428").
        then().
            //log().all().
            statusCode(200).
            contentType(ContentType.JSON).
            body("name", equalTo("The Big Lebowski [Blu-ray]")).
            body("current_price.value", equalTo(26.37f)).
            body("current_price.currency_code", equalTo("USD"));
   }

    @Test
    public void testGetProduct_NotFound() {

        given().
        when().
            get("http://localhost:8080/v1/products/13860425").
        then().
           // log().all().
            statusCode(404).
            contentType(ContentType.JSON).
            body("message", equalTo("Invalid Product Id"));
    }
    
    @Test
    public void testCreateProduct_Success() {
    	 given().
        // log().all().
         contentType("application/json").
     body("{"  +  "\"name\": \"The Big Lebowski [Blu-ray]\"," +
             "\"current_price\": {" +
                 "\"value\": 26.37," +
                 "\"currency_code\": \"USD\"" +
             "}" +
         "}").
     when().
         post("http://localhost:8080/v1/products").
     then().
        // log().all().
         statusCode(201);
   }
    
    
    

    @Test
    public void testUpdateProduct_Success() {
    	   given().
           //log().all().
           contentType("application/json").
       body("{"  +  "\"name\": \"The Big Lebowski [Blu-ray]\"," +
               "\"current_price\": {" +
                   "\"value\": 26.37," +
                   "\"currency_code\": \"USD\"" +
               "}" +
           "}").
       when().
           put("http://localhost:8080/v1/products/13860428").
       then().
          // log().all().
           statusCode(200);
    	 
    	   
    	   given().
           when().
               get("http://localhost:8080/v1/products/13860428").
           then().
             //  log().all().
               statusCode(200).
               contentType(ContentType.JSON).
               body("name", equalTo("The Big Lebowski [Blu-ray]")).
               body("current_price.value", equalTo(26.37f)).
               body("current_price.currency_code", equalTo("USD"));
    	   
    }

}
