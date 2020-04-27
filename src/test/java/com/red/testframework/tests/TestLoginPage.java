package com.red.testframework.tests;

import com.red.testframework.pages.*;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.TestGroups;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import com.red.testframework.utils.Utils;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class TestLoginPage {

    private BasePage basePage;
    private LoginPage loginPage;
    private SamsaraPage samsaraPage;
    private HomePage homePage;
    private UsersPage usersPage;
    private HeroesPage heroesPage;
    private GalleryPage galleryPage;
    private ApiPage apiPage;
    private BrokenLinkPage brokenLinkPage;
    private AdminPage adminPage;
    private Utils utils;

    private static Logger log = LoggerFactory.getLogger(TestLoginPage.class);
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


    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("CHROME_HEADLESS") String browser) {
        loginPage = Utils.setUpWebBrowser(browser);
        utils = new Utils();
    }

    /*
     * Test validates that login page is opened by checking if log in button is
     * visible on the page
     */
    @Test(groups = {TestGroups.CRITICAL})
    public void testLoginPageIsOpened() {

        loginPage.adminLogIn();

    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.HIGH})
    public void testUnsuccessfulLogin() {
        loginPage = loginPage.logInWithInvalidCredentials("hacker", utils.getProperty("app.url"));

    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.MEDIUM})
    public void testUnsuccessfulLoginMedium() {

        loginPage = loginPage.logInWithInvalidCredentials("fail"+log, password);

    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.LOW})
    public void testUnsuccessfulLoginLow() {

        loginPage = loginPage.logInWithInvalidCredentials("fail", password);

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Cleaning after all test have been executed, regardless of outcome
        Log.debug("Reverting changes... (afterTest() in @AfterClass)");
        if (hero1Created || hero2Created || hero3Created) {
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
            if (loginPage != null)
                loginPage.quitWebDriver();

        }
    }
}