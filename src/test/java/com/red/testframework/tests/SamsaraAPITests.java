package com.red.testframework.tests; /*******************************************************************
 Users API
 U1. GET /api/users - Get list of all users.
 U2. GET /api/users/{id} - Get list of a user with specific id.
 U3. GET /api/users/exists/{username} - Check if user exists.
 U4. GET /api/users/findByUsername/{username} - Find specific user by username.
 U5. POST /api/users/[JSON object] - Create new user.
 U6. PUT /api/users/[JSON object] - Update user.
 U7. DELETE /api/users/{id} - Delete user with specified id.
 Heroes API
 H1. GET /api/heroes - Get list of all heroes.
 H2. GET /api/heroes/{id} - Get list of a hero with specific id.
 H3. GET /api/heroes/{type} - Get list of all user with specific type (class).
 ***********************************************************************/

import com.red.testframework.utils.TestConfiguration;
import org.json.JSONObject;
import org.testng.annotations.Test;
//import org.json.simple.JSONValue;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SamsaraAPITests {

    TestConfiguration testConfiguration;
    //BaseApi baseApi = new BaseApi();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
    String timestamp = sdf.format(new Timestamp(new Date().getTime())); // To secure non-redundancy in user/hero creating
    String username1 = "adespot" + timestamp, username2 = username1 + "2", username3 = username1 + "3", firstName = "Marko", lastName = "Despotovic", about = "despot",
            secretQuestion = "marko", secretAnswer = "despotovic", password = "Password1", conirfmPassword = "Password1";
    // All input data follow restriction of the original app
    String hero1Name = "aMarko_" + timestamp, hero2Name = "A" + hero1Name + timestamp, hero3Name = "B" + hero1Name + timestamp,
            level = "80", heroClass = "Guardian";

    boolean hero1Created, hero2Created, hero3Created, user1Created, user2Created = false;

//    public com.red.testframework.tests.SamsaraAPITests() throws IOException {
//        baseUri = super.baseUri + "/api";
//    }

//
//    @BeforeClass
//    public void setBaseUri() {
//
//        Log.info("In Before Class");
//    }

    @Test
    public void testResult() {


//        RestAssured.baseURI = "localhost:8080";
//        RequestSpecification request = RestAssured.given();
//
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("FirstName", "Virender"); // Cast
//        requestParams.put("LastName", "Singh");
//        requestParams.put("UserName", "sdimpleuser2dd2011");
//        requestParams.put("Password", "password1");
//        requestParams.put("Email", "sample2ee26d9@gmail.com");
//
//        request.body(requestParams.toString());
//        Response response = request.get("/register");
//
//        int statusCode = response.getStatusCode();
//        System.out.println("The status code recieved: " + statusCode);
//
//        System.out.println("Response body: " + response.body().asString());
//
//    }
//}
        String message = null;

        JSONObject user = new JSONObject();
        user.put("firstName", "Finn");
        user.put("lastName", "Mertens");
        user.put("username", "finsasa");
        user.put("email", "finn@mail.com");
        user.put("password", "$2a$10$c4k24Pk4lNy/v9wEZRsuT.LrTsYRLK7Jj7.mLahhCZwCgoWwAY7IW");
        user.put("about", "About Me Text");
        user.put("secretQuestion", "pitanje");
        user.put("secretAnswer", "odgovor");
        message = user.toString();
        System.out.println(message);
        JSONObject json = new JSONObject(message);
        System.out.println(json);
    }
}
//        RestAssured
//                .when().get(baseUri)
//                .then().statusCode(200); // extract the response
//        Response res  =given().param ("query", "Churchgate Station")
//                .param ("key", "Xyz")
//                .when()
//                .get ("/maps/api/place/textsearch/json").then()
//                .contentType(ContentType.JSON)
//                .extract()
//                .path ("results[0].formatted_address");
//
//        Assert.assertEquals (res, "Maharshi Karve Rd, Churchgate, Mumbai, Maharashtra 400020");

//    }


//    @BeforeClass
//    public void beforeTests() {
//        // First to be executed, pages' classes initialisation and login as an admin
//        baseApi.baseUrlTest_StatusCode200();
//
//    }
//
//    @Test(description = "U1. GET /api/users - Get list of all users.")
//    public void getUsersAPITest() {
//        Log.debug("Entered getUsers API test!");
//        /*{
//  "_embedded" : {
//    "users" : [ {
//      "firstName" : "Finn",
//      "lastName" : "Mertens",
//      "username" : "finn",
//      "email" : "finn@mail.com",
//      "password" : "$2a$10$c4k24Pk4lNy/v9wEZRsuT.LrTsYRLK7Jj7.mLahhCZwCgoWwAY7IW",
//      "about" : "About Me Text",
//      "created" : "2018-12-17T10:55:03.000+0000",
//      "secretQuestion" : "pitanje",
//      "secretAnswer" : "odgovor",
//      "enabled" : true,
//      "heroCount" : 0,
//      "_links" : {
//        "self" : {
//          "href" : "http://localhost:8080/api/users/1"
//        },
//        "user" : {
//          "href" : "http://localhost:8080/api/users/1"
//        },
//        "roles" : {
//          "href" : "http://localhost:8080/api/users/1/roles"
//        },
//        "heroes" : {
//          "href" : "http://localhost:8080/api/users/1/heroes"
//        },
//        "events" : {
//          "href" : "http://localhost:8080/api/users/1/events"
//        }
//      }
//    },*/
//
//    }
//
//    @Test(description = "U2. GET /api/users/{id} - Get list of a user with specific id.")
//    public void getSpecificUserAPITest() {
//        Log.debug("Entered getSpecificUser API test!");
//        /*{
//  "firstName" : "Marceline",
//  "lastName" : "Abadeer",
//  "username" : "marceline",
//  "email" : "marceline@mail.com",
//  "password" : "$2a$10$c4k24Pk4lNy/v9wEZRsuT.LrTsYRLK7Jj7.mLahhCZwCgoWwAY7IW",
//  "about" : "About Me Text",
//  "created" : "2018-12-17T10:55:04.000+0000",
//  "secretQuestion" : "pitanje",
//  "secretAnswer" : "odgovor",
//  "enabled" : true,
//  "heroCount" : 0,
//  "_links" : {
//    "self" : {
//      "href" : "http://localhost:8080/api/users/2"
//    },
//    "user" : {
//      "href" : "http://localhost:8080/api/users/2"
//    },
//    "roles" : {
//      "href" : "http://localhost:8080/api/users/2/roles"
//    },
//    "heroes" : {
//      "href" : "http://localhost:8080/api/users/2/heroes"
//    },
//    "events" : {
//      "href" : "http://localhost:8080/api/users/2/events"
//    }
//  }
//}*/
//
//    }
//
//    @Test(description = "U3. GET /api/users/exists/{username} - Check if user exists.")
//    public void getDoesUserExistAPITest() {
//        Log.debug("Entered doesUserExist API test!");
//        // true/false
//
//    }
//
//    @Test(description = "U4. GET /api/users/findByUsername/{username} - Find specific user by username.")
//    public void getFindByUsernameAPITest() {
//        Log.debug("Entered findByUsername API test!");
////       {"userId":"402834817130871e01713b676e05005c","username":"admina","firstName":"Markoa","lastName":"Despotovic","email":null,"about":"about","heroes":[],"heroCount":0,"createdAt":1585839566000,"enabled":false,"displayName":"Markoa Despotovic"}
//    }
//
//    @Test(description = "U5. POST /api/users/[JSON object] - Create new user.")
//    public void postCreateNewUserAPITest() {
//        Log.debug("Entered postCreateNewUser API Test!");
///*
//@Test
//public void RegistrationSuccessful()
//{
// RestAssured.baseURI ="http://restapi.demoqa.com/customer";
// RequestSpecification request = RestAssured.given();
//
// JSONObject requestParams = new JSONObject();
// requestParams.put("FirstName", "Virender"); // Cast
// requestParams.put("LastName", "Singh");
// requestParams.put("UserName", "sdimpleuser2dd2011");
// requestParams.put("Password", "password1");
//
// requestParams.put("Email",  "sample2ee26d9@gmail.com");
// request.body(requestParams.toJSONString());
// Response response = request.post("/register");
//
// int statusCode = response.getStatusCode();
// Assert.assertEquals(statusCode, "201");
// String successCode = response.jsonPath().get("SuccessCode");
// Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
//}*/
//    }
//
//    @Test(description = "U6. PUT /api/users/[JSON object] - Update user.")
//    public void putUpdateUserAPITest() {
//        Log.debug("Entered putUpdateUser API test!");
//
//
//    }
//
//    @Test(description =  "U7. DELETE /api/users/{id} - Delete user with specified id.")
//    public void deleteUserAPITest() {
//        Log.debug("Entered deleteUser API test!");
//
//    }
//
//    @Test(description = "H1. GET /api/heroes - Get list of all heroes.")
//    public void getListOfAllHeroesAPITest() {
//        Log.debug("Entered getListOfAllHeroes API test!");
//    }
//
//    @Test(description = "H2. GET /api/heroes/{id} - Get list of a hero with specific id.")
//    public void getSpecificHeroAPITest() {
//        Log.debug("Entered getSpecificHero API test!");
//    }
//
//    @Test(description =  "H3. GET /api/heroes/{type} - Get list of all user with specific type (class).")
//    public void getHeroesOfSpecificClassAPITest() {
//        Log.debug("Entered getHeroesOfSpecificClass API test!");
//    }
//
//    @AfterMethod
//    public void tearDown(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            ScreenshotUtil.makeScreenshot(result);
//        }
//        BrowserDriver.getCurrentDriver().navigate().to("localhost:8080");
//    }
//
//    @AfterClass
//    public void afterTest() {
//        // Cleaning after all test have been executed, regardless of outcome
//
//        Log.debug("Reverting changes...");
//
//        if (hero1Created || hero2Created || hero3Created) {
//            if (hero1Created) {
//                Log.debug("Deleting " + hero1Name + "...");
//            }
//            if (hero2Created) {
//                Log.debug("Deleting " + hero2Name + "...");
//            }
//        }
//        if (user1Created || user2Created) {
//            if (user1Created) {
//                Log.debug("Deleting " + username1 + "...");
//            }
//            if (user2Created) {
//                Log.debug("Deleting " + username2 + "...");
//            }
//        }
//    }
//}
