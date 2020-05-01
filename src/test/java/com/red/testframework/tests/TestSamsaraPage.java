package com.red.testframework.tests;

import com.red.testframework.pages.HeroesPage;
import com.red.testframework.pages.LoginPage;
import com.red.testframework.pages.SamsaraPage;
import com.red.testframework.pages.UsersPage;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import com.red.testframework.utils.Utils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSamsaraPage {

    private LoginPage loginPage;
    private SamsaraPage samsaraPage;
    private UsersPage usersPage;
    private HeroesPage heroesPage;
    private Utils utils;

    String timestamp = new SimpleDateFormat("HHmmss").format(new Timestamp(new Date().getTime())); // To secure non-redundancy in user/hero creating

    String username1 = "despot" + timestamp,
            username2 = "a" + username1 + "2",
            username3 = "z" + username2 + "3",
            firstName = "Marko",
            lastName = "Despotovic",
            about = "despot",
            secretQuestion = "marko",
            secretAnswer = "despotovic",
            password = "Password1",
            conirfmPassword = "Password1";

    String hero1Name = "aMarko_" + timestamp,
            hero2Name = "A" + hero1Name,
            hero3Name = "Z" + hero2Name,
            heroClass = "Guardian";
    // All input data follow restriction of the original app
    // Names intentionally start with letters "a" and "z", enforcing search through page lists

    boolean loginSuccessful, hero1Created, hero2Created, hero3Created, user1Created, user2Created = false;

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
    @Test(groups = {Constants.CRITICAL})
    public void testLoginPageIsOpened() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
    }

    @Test(groups = {Constants.CRITICAL})
    public void testSuccessfulLogIn() {
        Log.info(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
        samsaraPage = loginPage.adminLogIn();
        loginSuccessful = true;
    }


    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {Constants.HIGH})
    public void testUnsuccessfulLogin() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
        loginPage = loginPage.logInWithInvalidCredentials("hacker", utils.getProperty("app.url"));
    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {Constants.MEDIUM})
    public void testUnsuccessfulLoginMedium() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.loginPageIsDisplayed();
        loginPage.logInWithInvalidCredentials("Mr Robot", password);

    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {Constants.LOW})
    public void testUnsuccessfulLoginLow() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage = loginPage.logInWithInvalidCredentials("fail", password);

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

        if (hero1Created || hero2Created || hero3Created || user1Created || user2Created) {
            Log.info("=========== Reverting changes");

            if (hero1Created || hero2Created || hero3Created) {
                Log.info("----- Deleting created hero(es)");
                samsaraPage = loginPage.adminLogIn();
                heroesPage = samsaraPage.navigateToHeroesPage();
                if (hero1Created) {
                    Log.debug("Deleting " + hero1Name + "...");
                    heroesPage.deleteHero(hero1Name);
                    Log.debug("Hero " + hero1Name + " has been deleted!");
                }
                if (hero2Created) {
                    Log.debug("Deleting " + hero2Name + "...");
                    heroesPage.deleteHero(hero2Name);
                    Log.debug("Hero " + hero2Name + " has been deleted!");
                }
                if (hero3Created) {
                    Log.debug("Deleting " + hero3Name + "...");
                    heroesPage.deleteHero(hero3Name);
                    Log.debug("Hero " + hero3Name + " has been deleted!");
                }
                loginPage.logOut();
            }

            if (user1Created || user2Created) {
                Log.info("----- Deleting created user(s)");
                samsaraPage = loginPage.adminLogIn();
                usersPage = samsaraPage.navigateToUsersPage();
                if (user1Created) {
                    Log.debug("Deleting " + username1 + "...");
                    usersPage.deleteUser(username1);
                    Log.debug("User " + username1 + " has been deleted!");
                }
                if (user2Created) {
                    Log.debug("Deleting " + username2 + "...");
                    usersPage.deleteUser(username2);
                    Log.debug("User " + username2 + " has been deleted!");
                }
                loginPage.logOut();
                Log.info("========================");
            }
        }

        if (loginPage != null)
            loginPage.quitWebDriver();

        Log.endTest("That's all, folks! ");
    }
}