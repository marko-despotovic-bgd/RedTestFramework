package com.red.testframework.tests;

import com.red.testframework.db.DBConnection;
import com.red.testframework.db.DBQueries;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;

import org.testng.annotations.*;

public class TestDatabase {

    private final DBQueries dbQueries;
    private DBConnection dbConnection;

    TestDatabase() {
        dbQueries = DBQueries.createDBQueries();
    }

    /** Prerequisite for testing DB queries:
     * pom.xml: update suiteXmlFile to point to single-page-runner.xml in stead of testng.xml
     * start Samsara with red database **/

    // There is no much of a point testing DB with no data populated.
    // Testing -- DROP DATABASE IF EXISTS database_name -- has some side effects that I didn't manage to overcome through code
    // (while executing it on MySQL Workbench went without problem). Production-like environments certainly would have
    // safety triggers that would deny any DROP DATABASE requests, so moved on. Left dropAndRecreateDatabase method in DBQuery for new inputs.

    @Test(groups = {Constants.DB_SANITY})
    public void testDBConnectionCheck() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        dbQueries.connectionInfoDBQuery();
    }

    @Test(groups = {Constants.DB_CRITICAL}, dependsOnMethods = "testDBConnectionCheck")
    public void testDBShowDatabase() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        assert DBQueries.isResultSetEmpty(DBQueries.showDatabaseDBQuery()) : "No databases were found!";
    }

    @Test(groups = {Constants.DB_CRITICAL}, dependsOnMethods = "testDBShowDatabase")
    public void testDBShowTables() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        assert DBQueries.isResultSetEmpty(DBQueries.showTablesDBQuery()) : "No tables were found!";
    }

    @Test(groups = {Constants.DB_CRITICAL}, dependsOnMethods = "testDBShowTables")
    public void testDBTruncateTables() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        DBQueries.truncateTablesDBQuery();
        DBQueries.showTablesDBQuery();
    }

    @Test(groups = {Constants.DB_CRITICAL}, dependsOnMethods = "testDBTruncateTables")
    public void testDBInsertUsers() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isUserInserted = DBQueries.insertIntoUsersTableDBQuery(1, "Finn", "Martin", "finn");
        assert !isUserInserted : "User not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isUserInserted = DBQueries.insertIntoUsersTableDBQuery(2, "Jake", "The Dog", "jake");
        assert !isUserInserted : "User not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isUserInserted = DBQueries.insertIntoUsersTableDBQuery(3, "Marceline", "Abadeer", "marceline");
        assert !isUserInserted : "User not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        DBQueries.showTableData("users");
    }

    @Test(groups = {Constants.DB_CRITICAL}, dependsOnMethods = "testDBInsertUsers")
    public void testDBInsertHeroes() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isHeroInserted = DBQueries.insertIntoHeroesTableDBQuery(1, "Vojnik", "Warrior", 120, 1);
        assert !isHeroInserted : "Hero not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isHeroInserted = DBQueries.insertIntoHeroesTableDBQuery(2, "Baraba", "Thief", 100, 1);
        assert !isHeroInserted : "Hero not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isHeroInserted = DBQueries.insertIntoHeroesTableDBQuery(3, "Prevarant", "Mage", 32, 1);
        assert !isHeroInserted : "Hero not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isHeroInserted = DBQueries.insertIntoHeroesTableDBQuery(4, "Lovac", "Hunter", 120, 2);
        assert !isHeroInserted : "Hero not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isHeroInserted = DBQueries.insertIntoHeroesTableDBQuery(5, "Pravedni Mika", "Paladin", 120, 3);
        assert !isHeroInserted : "Hero not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isHeroInserted = DBQueries.insertIntoHeroesTableDBQuery(6, "Sestra Medicine Milica", "Cleric", 120, 3);
        assert !isHeroInserted : "Hero not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        isHeroInserted = DBQueries.insertIntoHeroesTableDBQuery(7, "Disko Mile", "Warrior", 14, 3);
        assert !isHeroInserted : "Hero not inserted!"; // TRUE indicates the result is a ResultSet and FALSE indicates it has the int value which denotes number of rows affected by the query.
        DBQueries.showTableData("heroes");
    }

    @Test(groups = {Constants.DB_MEDIUM}, dependsOnMethods = "testDBInsertHeroes")
    public void testDBSelectTask1() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        Log.info("Task 1 : Get the last name of user with id 1");
        DBQueries.selectDBQuery("last_name", "users", "user_id=1");
    }

    @Test(groups = {Constants.DB_MEDIUM}, dependsOnMethods = "testDBSelectTask1")
    public void testDBSelectTask2() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        Log.info("Task 2 : Get the first name, last name and username of user with id 1");
        DBQueries.selectDBQuery("first_name,last_name,username", "users", "user_id=1");
    }

    @Test(groups = {Constants.DB_MEDIUM}, dependsOnMethods = "testDBSelectTask1")
    //<-- Testing purpose , dependsOnMethods = "testDBConnection")
    public void testDBSelectTask3() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        Log.info("Task 3 : Get the first name, last name and username of user with id less than 3 and sort them ascended");
        DBQueries.selectDBQuery("first_name,last_name,username", "users", "user_id<3 ORDER BY user_id ASC"); //DESC
    }

    @Test(groups = {Constants.DB_MEDIUM}, dependsOnMethods = "testDBSelectTask1")
    //<-- Testing purpose , dependsOnMethods = "testDBConnection")
    public void testDBSelectTask4() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        Log.info("Task 4 : Get the hero names of all heroes that are Warrior Type");
        DBQueries.selectDBQuery("name", "heroes", "type='Warrior'");
    }

    @Test(groups = {Constants.DB_MEDIUM}, dependsOnMethods = "testDBSelectTask1")
    //<-- Testing purpose , dependsOnMethods = "testDBConnection")
    public void testDBSelectTask5() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        Log.info("Task 5 : Get the hero names of all heroes from user with id 3");
        DBQueries.selectDBQuery("name", "heroes as h JOIN users u ON u.user_id=h.fk_user_id", "h.fk_user_id=3");//1
    }

    @Test(groups = {Constants.DB_MEDIUM}, dependsOnMethods = "testDBSelectTask1")
    //<-- Testing purpose , dependsOnMethods = "testDBConnection")
    public void testDBSelectTask6() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        Log.info("Task 6 : Get the hero names of all heroes with hero username jake");
        DBQueries.selectDBQuery("name", "heroes as h JOIN users u ON u.user_id=h.fk_user_id", "u.username = 'Jake'");//1
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDBConnection() {
        Log.endTest(new Object() {
        }.getClass().getEnclosingMethod().getName());

        if (dbConnection != null)
            dbConnection.closeConnection();
    }
}