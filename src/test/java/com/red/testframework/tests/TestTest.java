package com.red.testframework.tests;

import com.red.testframework.BaseTest;
import com.red.testframework.pages.LoginPage;
import com.red.testframework.pages.SamsaraPage;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import com.red.testframework.utils.TestGroups;
import com.red.testframework.utils.Utils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTest {
}
//package com.red.testframework.tests;
//
//        import com.red.testframework.BaseTest;
//        import com.red.testframework.pages.*;
//        import com.red.testframework.utils.Log;
//        import com.red.testframework.utils.ScreenshotUtil;
//        import com.red.testframework.utils.TestGroups;
//        import org.testng.ITestResult;
//        import org.testng.annotations.*;
//        import com.red.testframework.utils.Utils;
//
//        import java.sql.Timestamp;
//        import java.text.SimpleDateFormat;
//        import java.util.Date;
//
//public class TestLoginPage extends BaseTest {
//
//    private LoginPage loginPage;
//    private Utils utils;
//
//    String timestamp = new SimpleDateFormat("HHmmss").format(new Timestamp(new Date().getTime()));
//    // To secure non-redundancy in user/hero creating
//
//    boolean loginSuccessful = false;
//
//    @BeforeClass(alwaysRun = true)
//    @Parameters({"browser"})
//    public void setUp(@Optional("CHROME") String browser) {
//        loginPage = Utils.setUpWebBrowser(browser);
//        utils = new Utils();
//    }
//
//    /*
//     * Test validates that login page is opened by checking if log in button is
//     * visible on the page
//     */
//    @BeforeMethod(alwaysRun = true)
//    public void testLoginPageIsOpened() {
//        Log.startTest(new Object() {
//        }.getClass().getEnclosingMethod().getName());
//        loginPage.verifyLoginPageIsDisplayed();
//    }
//
//    @Test(groups = {TestGroups.CRITICAL})
//    public void testSuccessfulLogIn() {
//        Log.info(new Object() {
//        }.getClass().getEnclosingMethod().getName());
//        loginPage.verifyLoginPageIsDisplayed();
//        SamsaraPage samsaraPage = loginPage.adminLogIn();
//        loginSuccessful = true;
//    }
//
//
//    /*
//     * Test validates that proper error message is displayed upon unsuccessful login
//     */
//    @Test(groups = {TestGroups.HIGH})
//    public void testUnsuccessfulLogin() {
//        Log.startTest(new Object() {
//        }.getClass().getEnclosingMethod().getName());
//        loginPage.verifyLoginPageIsDisplayed();
//        loginPage = loginPage.logInWithInvalidCredentials("hacker", utils.getProperty("app.url"));
//    }
//
//    /*
//     * Test validates that proper error message is displayed upon unsuccessful login
//     */
//    @Test(groups = {TestGroups.MEDIUM})
//    public void testUnsuccessfulLoginMedium() {
//        Log.startTest(new Object() {
//        }.getClass().getEnclosingMethod().getName());
//        loginPage.verifyLoginPageIsDisplayed();
//        loginPage.logInWithInvalidCredentials("Mr Robot", "password");
//
//    }
//
//    /*
//     * Test validates that proper error message is displayed upon unsuccessful login
//     */
//    @Test(groups = {TestGroups.LOW})
//    public void testUnsuccessfulLoginLow() {
//        Log.startTest(new Object() {
//        }.getClass().getEnclosingMethod().getName());
//        loginPage = loginPage.logInWithInvalidCredentials("fail", "password");
//
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void tearDown(ITestResult result) {
//        Log.endTest(new Object() {
//        }.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
//
//        if (result.getStatus() == ITestResult.FAILURE) {
//            ScreenshotUtil.makeScreenshot(result);
//        }
//
//        if (loginSuccessful)
//            loginPage.logOut();
//
//        loginSuccessful = false; // Resetting
//    }
//
//    @AfterClass(alwaysRun = true)
//    public void tearDown() {
//        // Cleaning after all test have been executed, regardless of outcome
//        Log.endTest(new Object() {
//        }.getClass().getEnclosingMethod().getName() + " in @AfterClass");
//
//        if (loginPage != null)
//            loginPage.quitWebDriver();
//
//        Log.endTest("That's all, folks! ");
//    }
//}