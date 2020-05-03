package com.red.testframework.tests;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestLoginPage extends BaseTest {

    boolean loginSuccessful = false;

    // Verify Login page is displayed, and Log In button is show
    @Test(groups = {Constants.SANITY})
    public void testLoginPageIsOpened() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        assert loginPage.isLogInButtonDisplayed() : "Log In button is not displayed";
    }

    // Verify admin login
    @Test(groups = {Constants.CRITICAL}, dependsOnMethods = "testLoginPageIsOpened")
    public void testSuccessfulAdminLogIn() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        //loginPage.loginPageIsDisplayed();
        loginPage.adminLogIn(); // This method itself is checking if Samsara page is opened
        loginSuccessful = true;
    }

    // Verify user login
    @Test(groups = {Constants.HIGH}, dependsOnMethods = "testLoginPageIsOpened")
    public void testSuccessfulUserLogIn() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password")); // This method itself is checking if Samsara page is opened
        loginSuccessful = true;
    }

    // Test Invalid Credentials with empty username and empty password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginEmptyUsernameEmptyPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials("", "");
    }

    // Test Invalid Credentials with empty username and correct password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginEmptyUsernameCorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials("", utils.getProperty("password"));
    }

    // Test Invalid Credentials with empty username and incorrect password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginEmptyUsernameIncorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials("", "incorrectPassword");
    }

    // Test Invalid Credentials with correct username and empty password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginCorrectUsernameEmptyPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials(utils.getProperty("user.username"), "");
    }

    // Test Invalid Credentials with incorrect username and empty password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginIncorrectUsernameEmptyPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials("hacker", "");
    }

    // Test Invalid Credentials with incorrect username and incorrect password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginIncorrectUsernameIncorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials("mr.robot", "letMeIn");
    }

    // Test Invalid Credentials with incorrect username and correct password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginIncorrectUsernameCorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials(utils.getProperty("user.username") + utils.getProperty("user.username"), "");
    }

    // Test Invalid Credentials with correct username and 256 characters long password
    @Test(groups = {Constants.LOW}, dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginCorrectUsernameLongPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        String randomString = RandomStringUtils.random(256, true, true);
        loginPage = loginPage.logInWithInvalidCredentials(utils.getProperty("user.username"), randomString);
    }

    // Test Invalid Credentials with correct username and 256 characters long password
    @Test(groups = {Constants.MEDIUM}, dataProvider = "UnsuccessfulLoginInputMatrix", dependsOnMethods = "testLoginPageIsOpened")
    public void testUnsuccessfulLoginWithDataProvider(String username, String password) {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials(username, password);
    }

    @DataProvider(name = "UnsuccessfulLoginInputMatrix")
    public Object[][] getDataFromDataProvider() {
        return new Object[][]
                {
                        {"", ""},
                        {"", utils.getProperty("password")},
                        {"", "incorrectPassword"},
                        {utils.getProperty("user.username"), ""},
                        {"hacker", ""},
                        {"mr.robot", "drowssap"},
                        {"neo", utils.getProperty("password")},
                        {utils.getProperty("user.username"), RandomStringUtils.random(256, true, true)},
                };
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        Log.endTest(new Object() {
        }.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.makeScreenshot(result);
        }
        if (loginSuccessful)
            loginPage.logOut();
        loginSuccessful = false;
    }
}