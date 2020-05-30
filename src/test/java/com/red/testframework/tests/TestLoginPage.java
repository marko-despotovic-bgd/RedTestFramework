package com.red.testframework.tests;

import com.red.testframework.dataproviders.LoginPageDataProvider;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestLoginPage extends BaseTest {

    /**  TC naming: test_state(page)_actions_expectedResult()  **/

    @Test(description = "Verify Login page is displayed, and Log In button is shown.", groups = {Constants.SANITY})
    public void test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
    }

    @Test(description = "Test login with admin's credentials.", groups = {Constants.CRITICAL}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithAdminCredentials_adminLogIn() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        samsaraPage = loginPage.adminLogin();
        assert samsaraPage.isSamsaraPageTitleDisplayed() : "Samsara Page title is not displayed";
        loginSuccessful = true;
    }

    @Test(description = "Test login with user's credentials.", groups = {Constants.HIGH}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithUserCredentials_userLogIn() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        samsaraPage = loginPage.userLogin();
        assert samsaraPage.isSamsaraPageTitleDisplayed() : "Samsara Page title is not displayed";
        loginSuccessful = true;
    }

    @Test(description = "Test Invalid Credentials with empty username and empty password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithEmptyUsernameAndEmptyPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials("", "");
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with empty username and correct password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithEmptyUsernameAndCorrectPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials("", Utils.getProperty("user.password"));
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with empty username and incorrect password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithEmptyUsernameAndIncorrectPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials("", "incorrectPassword");
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with correct username and empty password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithCorrectUsernameAndEmptyPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials(Utils.getProperty("user.username"), "");
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with incorrect username and empty password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithIncorrectUsernameEmptyPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials("hacker", "");
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with incorrect username and incorrect password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithIncorrectUsernameIncorrectPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials("mr.robot", "letMeIn");
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with incorrect username and correct password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithIncorrectUsernameCorrectPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials(Utils.getProperty("user.username") + Utils.getProperty("user.username"), "");
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with correct username and 256 characters long password.", groups = {Constants.LOW}, dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_loginWithCorrectUsernameLongPassword_invalidCredentialsErrorMessagePresent() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        String randomString = RandomStringUtils.random(256, true, true);
        loginPage.loginWithInvalidCredentials(Utils.getProperty("user.username"), randomString);
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @Test(description = "Test Invalid Credentials with correct username and 256 characters long password.", groups = {Constants.MEDIUM}, dataProviderClass = LoginPageDataProvider.class, dataProvider = "getUsersAndPasswordsDataProvider", dependsOnMethods = "test_loginPage_checkIfLoginPageTitleAndLoginButtonArePresent_loginPageIsOpened")
    public void test_loginPage_testUnsuccessfulLoginWithDataProvider_invalidCredentialsErrorMessagePresent(String username, String password) {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.loginWithInvalidCredentials(username, password);
        assert loginPage.isInvalidCredentialsErrorMessagePresent() : "\"Invalid username and password.\" message not present on the page!";
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(@NotNull ITestResult result) {
        Log.endTest(new Object(){}.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
        if (loginSuccessful)
            loginPage.logOut();
        loginSuccessful = false;
        }
    }
