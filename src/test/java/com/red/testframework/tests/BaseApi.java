package com.red.testframework.tests;


import com.red.testframework.utils.Utils;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import org.testng.annotations.Test;


public class BaseApi {

    /**
     * GET root / should return Status 200 OK
     * Prerequisite for all other tests.
     */
    @BeforeTest(description = "Testing GET status code 200 for base URL as a prerequisite for all tests",
            alwaysRun = true)
    @Test
    public void testBaseUrlStatusCode200Api() {
        //RestAssured.proxy("localhost", 8888); // Using Fiddler for network scanning
        RestAssured.baseURI = Utils.getProperty("app.url"); // RA uses baseURI + port mechanism, so Utils.getProperty("app.url") works here
        RestAssured.basePath = "/api/";
        RestAssured.port = 9010;

        RestAssured.requestSpecification = new RequestSpecBuilder().
        addHeader("Content-Type", "application/json").
        addHeader("Accept", "application/json").
        setAuth(RestAssured.basic(Utils.getProperty("admin.username"), Utils.getProperty("admin.password"))).
        build();
        // When working with authentication token that has to be processed through flow involving more than one API call,
        // Use String authenticationToken = given.when.then.extract();
        RestAssured.given().log().all()
                .when().get()
                .then().statusCode(200);
    }
}