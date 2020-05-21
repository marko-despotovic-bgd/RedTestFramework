/*******************************************************************
 Users APIs
 UG1. GET /api/users - Get list of all users.
 UG2. GET /api/users/{id} - Get list of a user with specific id.
 UG3. GET /api/users/last5added - Get list of a 5 last added users.
 UG4. GET /api/users/exists/{username} - Check if user exists.
 UG5. GET /api/users/question/{username} - Get secret question for specific user.
 UG6. GET /api/users/last/{n} - Get last n users added.
 UG7. GET /api/users/first/{n} - Get first n users added.
 UG8. GET /api/users/findByUsername/{username} - Find specific user by username.
 UG9. GET /api/users/findByLastname/{lastName} - Find user by last name.
 UG10. GET /api/users/findByFirstname/{firstName} - Find user by first name.
 UG11. GET /api/users/enabled - Get all enabled users.
 UPo1. POST /api/users/[JSON object] - Create new user.
 UPu1. PUT /api/users/[JSON object] - Update user.
 D1. DELETE /api/users/{id} - Delete user with specified id.
 Heroes APIs
 H1. GET /api/heroes - Get list of all heroes.
 H2. GET /api/heroes/{id} - Get list of a hero with specific id.
 H3. GET /api/heroes/{type} - Get list of all user with specific type (class).
 H4. GET /api/heroes/{level} - Get list of all user with specific level.
 H5. GET /api/heroes/last10added - Get list of 10 last added heroes.
 ***********************************************************************/
package com.red.testframework.tests;

import static io.restassured.RestAssured.*;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Utils;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class SamsaraAPITests extends BaseApi {

    public Response response;

    @Test(description = "UG1. GET /api/users - Get list of all users.", groups = {Constants.CRITICAL})
    public void testGetUsersAPI() {

        response = given().log().all().get("/users?size=200"); // Without size parameter, it would list only 20 users. So, practically, this number should be 20 * no_of_users
        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        JSONObject jsonObjectBody = new JSONObject(response.getBody().asString());

        System.out.println("List of users:");
        String firstName;
        for (int i = 0; i < jsonObjectBody.getJSONObject("_embedded").getJSONArray("users").length(); i++) {
            firstName = jsonObjectBody.getJSONObject("_embedded").
                    getJSONArray("users").getJSONObject(i).getString("firstName");
            System.out.println(firstName);
        }
    }

    @Test(description = "UG2. GET /api/users/{id} - Get list of a user with specific id.", groups = {Constants.CRITICAL})
    public void testGetSpecificUserAPI() {

        int randomId = ThreadLocalRandom.current().nextInt(1, 7);

        response = given().log().all().get("users/" + randomId);

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        System.out.println("User with id " + randomId + " is " + response.jsonPath().getString("username"));
        response.getBody().prettyPrint();
    }

    @Test(description = "UG3. GET /api/users/last5added - Get list of a 5 last added users.", groups = {Constants.CRITICAL})
    public void testGetLast5AddedUsersAPI() {

        response = given().log().all().get("/users/last5added");

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        String usernames = response.jsonPath().getString("username");
        System.out.println("Last 5 added users are: " + usernames);

        response.getBody().prettyPrint();
    }

    @Test(description = "UG4. GET /api/users/exists/{username} - Check if user exists.", dataProvider = "Users", groups = {Constants.CRITICAL})
    public void testGetDoesUserExistAPI(String username, String doesExist) {

        response = given().log().all().given().pathParam("username", username).get("users/exists/{username}");

        System.out.println("User with username '" + username + "' exists: " + response.asString());

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();
        assert response.asString().equals(doesExist) : "User does not exist!";
    }

    @Test(description = "UG5. GET /api/users/question/{username} - Get secret question for specific user.", dataProvider = "Users", groups = {Constants.CRITICAL})
    public void testGetUsersSecretQuestionAPI(String username, String doesExist) {

        response = given().pathParam("username", username).get("users/question/{username}");

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        if (response.asString().equals("Jedno od random fejk pitanja da zavaraju hakere... kao. Zato je bolje koristiti genericka pitanje a ne da sam klijent postavi.") && doesExist.equals("false"))
            System.out.println("User does not exist!");
        else
            System.out.println("User '" + username + "' set secret question: '" + response.asString() + "'");
    }

    @Test(description = "UG6. GET /api/users/last/{n} - Get last n users added.", groups = {Constants.CRITICAL})
    public void testGetLastNUsersAddedAPI() {
        int randomId = ThreadLocalRandom.current().nextInt(1, 200); // Bound is total number of users in DB

        response = given().log().all().get("/users/last/" + randomId); // List is inverted, FIRST N users are shown

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        System.out.println("List of last " + randomId + " added users:");
        String usernames = response.jsonPath().getString("username");
        System.out.println(usernames);

        response.getBody().prettyPrint();
    }

    @Test(description = "UG7. GET /api/users/first/{n} - Get first n users added.", groups = {Constants.CRITICAL})
    public void testGetFirstNUsersAddedAPI() {
        int randomId = ThreadLocalRandom.current().nextInt(1, 200); // Bound is total number of users in DB

        response = given().log().all().get("/users/first/" + randomId); // List is inverted, LAST N users are shown

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        System.out.println("List of first " + randomId + " added users:");
        String usernames = response.jsonPath().getString("username");
        System.out.println(usernames);

        response.getBody().prettyPrint();
    }

    @Test(description = "UG8. GET /api/users/findByUsername/{username} - Find specific user by username.", dataProvider = "Users", groups = {Constants.CRITICAL})
    public void testGetFindByUsernameAPI(String username, String doesExist) {

        if (doesExist.equals("true")) {
            response = given().log().all().pathParam("username", username).get("users/findByUsername/{username}");
            assert response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + response.getStatusCode();
            System.out.println("User " + username + ": ");
            response.getBody().prettyPrint();
        } else
            System.out.println("User does not exist");
    }

    @Test(description = "UG9. GET /api/users/findByLastname/{lastName} - Find user by last name.", dataProvider = "Users", groups = {Constants.CRITICAL})
    public void testGetFindByLastNameAPI(String username, String doesExist) { // doesExist is not used here, but need to be named due to DataProvider protocol

        response = given().log().all().pathParam("username", username).get("users/findByLastname/{username}");

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        System.out.println("User(s) with last name " + username + ": ");
        response.getBody().prettyPrint();
    }

    @Test(description = " UG10. GET /api/users/findByFirstname/{firstName} - Find user by first name.", dataProvider = "Users", groups = {Constants.CRITICAL})
    public void testGetFindByFirstNameAPI(String username, String doesExist) {  // doesExist is not used here, but need to be named due to DataProvider protocol

        response = given().log().all().pathParam("username", username).get("users/findByFirstname/{username}");

        System.out.println("User(s) with first name " + username + ": ");
        response.getBody().prettyPrint();
    }


    @Test(description = "UG11. GET /api/users/enabled - Get all enabled users.", groups = {Constants.CRITICAL})
    public void testGetAllEnabledUsersAPI() {

        response = given().log().all().get("users/enabled");

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        System.out.println("Users with 'enabled' flag set:");
        response.getBody().prettyPrint();
    }

//    @Test(description = "UPo1. POST /api/users/[JSON object] - Create new user.")
//    public void postCreateNewUserAPITest() {
    /**{
     "timestamp": 1589623530948,
     "status": 500,
     "error": "Internal Server Error",
     "exception": "org.springframework.http.converter.HttpMessageNotWritableException",
     "message": "Could not write JSON: (was java.lang.NullPointerException); nested exception is com.fasterxml.jackson.databind.JsonMappingException: (was java.lang.NullPointerException) (through reference chain: org.springframework.data.rest.webmvc.json.PersistentEntityJackson2Module$PersistentEntityResourceSerializer$1[\"content\"]->org.spectres.samsara.model.User[\"heroCount\"])",
     "path": "/api/users"
     }**/
//    }

//    @Test(description = "UPu1. PUT /api/users/[JSON object] - Update user.")
//    public void putUpdateUserAPITest() {
    /**{
     "timestamp": 1589623590600,
     "status": 404,
     "error": "Not Found",
     "message": "No message available",
     "path": "/api/users"
     }**/
//    }

//    @Test(description = "D1. DELETE /api/users/{id} - Delete user with specified id.")
//    public void deleteUserAPITest() {

    /**
     * {
     * "cause": {
     * "cause": {
     * "cause": null,
     * "message": "Cannot delete or update a parent row: a foreign key constraint fails (`samsara`.`user_role`, CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`))"
     * },
     * "message": "could not execute statement"
     * },
     * "message": "could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"
     * }
     **/
//    }
    @Test(description = "H1. GET /api/heroes - Get list of all heroes.", groups = {Constants.CRITICAL})
    public void testGetListOfAllHeroesAPI() {
        System.out.println("Testing API call to " + baseURI + basePath + "heroes");

        response = given().log().all().get("heroes");

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + response.getStatusCode();

        JSONObject jsonObjectBody = new JSONObject(response.getBody().asString());

        String heroName;
        System.out.println("List of heroes: ");
        for (int i = 0; i < jsonObjectBody.getJSONObject("_embedded").getJSONArray("heroes").length(); i++) {
            heroName = jsonObjectBody.getJSONObject("_embedded").
                    getJSONArray("heroes").getJSONObject(i).getString("name");
            System.out.println(heroName);
        }
    }

    @Test(description = "H2. GET /api/heroes/{id} - Get list of a hero with specific id.", groups = {Constants.CRITICAL})
    public void testGetSpecificHeroAPI() {
        int randomId = ThreadLocalRandom.current().nextInt(1, 12); // Due to issue with id's and redundancy with heroes/level API, bound is only 12, but should be id of the last hero in samsara.heroes
        System.out.println("Testing API call to " + baseURI + basePath + "heroes/" + randomId);

        response = given().log().all().get("heroes/" + randomId);
        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        System.out.println("Hero with id " + randomId + " is " + response.jsonPath().getString("name"));
        response.getBody().prettyPrint();
    }

//    @Test(description = "H3. GET /api/heroes/{type} - Get list of all user with specific type (class).")
//    public void testGetHeroesOfSpecificClassAPI() {
//        /**
//         * This localhost page can’t be found
//         * No webpage was found for the web address: http://localhost:9010/api/heroes/Warrior
//         * HTTP ERROR 404
//         * **/
//
//    }

//    @Test(description = "H4. GET /api/heroes/{level} - Get list of all user with specific level.")
//    public void testGetHeroesOfSpecificLevelAPI() {
//

    /**
     * This localhost page can’t be found
     * No webpage was found for the web address: http://localhost:9010/api/heroes/80
     * HTTP ERROR 404
     **/
//    }
    @Test(description = "H5. GET /api/heroes/last10added - Get list of 10 last added heroes.", groups = {Constants.CRITICAL})
    public void testGetLast10AddedHeroesAPI() {

        System.out.println("Testing API call to " + baseURI + basePath + "heroes/last10added");

        response = given().log().all().get("/heroes/last10added");

        assert response != null && response.getStatusCode() == 200 : "Error in status code! Expected 200, but received " + Objects.requireNonNull(response).getStatusCode();

        String heroNames = response.jsonPath().getString("name");
        System.out.println("Last 10 added heroes are: " + heroNames);

        response.getBody().prettyPrint();
    }

    @DataProvider(name = "Users")
    public Object[][] getUsersFromDataProvider() {
        return new Object[][]
                {
                        {"Finn", "true"},
                        {Utils.getProperty("admin.username"), "true"},
                        {"Marko", "true"},
                        {"Pera", "false"},
                        {"marceline", "true"},
                        {"Mika", "false"},
                        {"Despotovic", "false"},
                        {"adminaa", "true"},
                        {Utils.getProperty("user.username"), "true"},
                };
    }
}