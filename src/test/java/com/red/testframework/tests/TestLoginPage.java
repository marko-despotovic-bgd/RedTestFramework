package com.red.testframework.tests;

import com.red.testframework.pages.*;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import com.red.testframework.utils.TestGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.red.testframework.utils.Utils;

public class TestLoginPage {

    private LoginPage loginPage;
    private Utils utils;
    private static Logger log = LoggerFactory.getLogger(BasePage.class);

    boolean loginSuccessful = false;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("CHROME") String browser) {
        loginPage = Utils.setUpWebBrowser(browser); // Running this class only will default to chrome. When called via testng.xml, CHROME will be ignored and
        // all tests will be treated respecting xml's config.
        //Log.startTest(new Object() {}.getClass().getEnclosingMethod().getName()); // Reading enclosing method name
        utils = new Utils();
    }

    /*
     * Test validates that login page is opened by checking if log in button is
     * visible on the page
     */
    @Test(groups = {TestGroups.CRITICAL})
    public void testLoginPageIsOpened() {
        log.info(new Object() {}.getClass().getEnclosingMethod().getName());
        loginPage.verifyLoginPageIsDisplayed();
    }

    @Test(groups = {TestGroups.CRITICAL})
    public void testSuccessfulLogIn() {
        Log.info(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.verifyLoginPageIsDisplayed();
        loginPage.adminLogIn();
        loginSuccessful = true;
    }


    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.HIGH})
    public void testUnsuccessfulLogin() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.verifyLoginPageIsDisplayed();
        loginPage = loginPage.logInWithInvalidCredentials("hacker", utils.getProperty("app.url"));
    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.MEDIUM})
    public void testUnsuccessfulLoginMedium() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.verifyLoginPageIsDisplayed();
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
        Log. endTest(new Object() {
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
        Log.endTest("That's all, folks! ");
    }
}