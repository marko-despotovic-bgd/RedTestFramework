package com.red.testframework.tests;

import com.red.testframework.pages.SamsaraPage;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.Utils;
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
    @Test(groups = {Constants.CRITICAL})
    public void testSuccessfulAdminLogIn() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        SamsaraPage samsaraPage = loginPage.adminLogIn(); // This method itself is checking if Samsara page is opened
        assert samsaraPage.isSamsaraPageTitleDisplayed();
        loginSuccessful = true;
    }

    // Verify user login
    @Test(groups = {Constants.HIGH})
    public void testSuccessfulUserLogIn() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage.logIn(Utils.getProperty("user.username"), Utils.getProperty("password")); // This method itself is checking if Samsara page is opened
        loginSuccessful = true;
    }

    // Test Invalid Credentials with empty username and empty password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginEmptyUsernameEmptyPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials("", "");
    }

    // Test Invalid Credentials with empty username and correct password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginEmptyUsernameCorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials("", Utils.getProperty("password"));
    }

    // Test Invalid Credentials with empty username and incorrect password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginEmptyUsernameIncorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials("", "incorrectPassword");
    }

    // Test Invalid Credentials with correct username and empty password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginCorrectUsernameEmptyPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials(Utils.getProperty("user.username"), "");
    }

    // Test Invalid Credentials with incorrect username and empty password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginIncorrectUsernameEmptyPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials("hacker", "");
    }

    // Test Invalid Credentials with incorrect username and incorrect password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginIncorrectUsernameIncorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials("mr.robot", "letMeIn");
    }

    // Test Invalid Credentials with incorrect username and correct password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginIncorrectUsernameCorrectPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials(Utils.getProperty("user.username") + Utils.getProperty("user.username"), "");
    }

    // Test Invalid Credentials with correct username and 256 characters long password
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginCorrectUsernameLongPassword() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        String randomString = RandomStringUtils.random(256, true, true);
        loginPage = loginPage.logInWithInvalidCredentials(Utils.getProperty("user.username"), randomString);
    }

    // Test Invalid Credentials with correct username and 256 characters long password
    @Test(groups = {Constants.MEDIUM}, dataProvider = "UnsuccessfulLoginInputMatrix")
    public void testUnsuccessfulLoginWithDataProvider(String username, String password) {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        loginPage = loginPage.logInWithInvalidCredentials(username, password);
    }

    @DataProvider(name = "UnsuccessfulLoginInputMatrix")
    public Object[][] getDataFromDataProvider() {
        return new Object[][]
                {
                        {"", ""},
                        {"", Utils.getProperty("password")},
                        {"", "incorrectPassword"},
                        {Utils.getProperty("user.username"), ""},
                        {"hacker", ""},
                        {"mr.robot", "drowssap"},
                        {"neo", Utils.getProperty("password")},
                        {Utils.getProperty("user.username"), RandomStringUtils.random(256, true, true)},
                };
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        Log.endTest(new Object() {
        }.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
        if (loginSuccessful)
            loginPage.logOut();
        loginSuccessful = false;
    }
}