package com.red.testframework.tests;

import com.red.testframework.pages.*;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import com.red.testframework.utils.TestGroups;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.red.testframework.utils.Utils;

public class TestLoginPage extends DatabaseTest {

    private LoginPage loginPage;
    private Utils utils;

    boolean loginSuccessful = false;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("CHROME") String browser) {
        loginPage = Utils.setUpWebBrowser(browser); // Running this class only will default to chrome. When called via testng.xml, CHROME will be ignored and
        // all tests will be treated respecting xml's config.
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName()); // Reading enclosing method name
        utils = new Utils();
    }

    /*
     * Test validates that login page is opened by checking if log in button is
     * visible on the page
     */
    @Test(groups = {TestGroups.CRITICAL})
    public void testLoginPageIsOpened() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
        loginPage.verifyLogInButtonIsDisplayed();
    }

    @Test(groups = {TestGroups.CRITICAL})
    public void testSuccessfulLogIn() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
        loginPage.adminLogIn(); // This method itself is checking if Samsara page is opened
        loginSuccessful = true;
    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.HIGH})
    public void testUnsuccessfulLogin() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
        loginPage = loginPage.logInWithInvalidCredentials("hacker", utils.getProperty("app.url"));
    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.MEDIUM})
    public void testUnsuccessfulLoginMedium() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
        loginPage.logInWithInvalidCredentials("Mr Robot", "password");

    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.LOW})
    public void testUnsuccessfulLoginLow() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials("fail", "password");

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

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Cleaning after all test have been executed, regardless of outcome
        Log.endTest(new Object() {
        }.getClass().getEnclosingMethod().getName() + " in @AfterClass");
        loginPage.quitWebDriver();
        Log.endTest("=== That's all, folks! ===");
    }
}