/********************************************************************************************
 Test suite:                                                                                *
 L1. Login module - Login to the web app using valid credentials                            *
 L2. Login module - Login to the web app using invalid credentials                          *
 H1. Hero module - Verify user can create new hero                                          *
 H2. Hero module - Verify user can edit existing own hero                                   *
 H3. Hero module - Verify user can delete own hero                                          *
 H4. Hero module - Verify user can not delete heroes he does not own                        *
 H5. Hero module - Verify "Show my heroes only" button is working as per design             *
 H6. Hero module - Verify newly added hero is displayed on "Last added heroes" list         *
 U1. User module - Verify Admin can create user                                             *
 U2. User module - Verify Admin can edit user                                               *
 U3. User module - Verify Admin can delete user                                             *
 U4. User module - Verify newly added user is displayed on "Last added users" list          *
 A1. Admin module - Verify user does not have access to admin section                       *
 A2. Admin module - Verify admin has access to admin section                                *
 A3. Admin module - Verify admin can recreate registration key                              *
 ********************************************************************************************/
package com.red.testframework.tests;

import com.red.testframework.pages.*;
import com.red.testframework.utils.TestConfiguration;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import com.red.testframework.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SamsaraUITests {

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
    private TestConfiguration testConfiguration;
    private static Logger log = LoggerFactory.getLogger(SamsaraUITests.class);


    private static final SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
    String timestamp = sdf.format(new Timestamp(new Date().getTime())); // To secure non-redundancy in user/hero creating
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


    @BeforeClass()
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        log.info("setUp() in @BeforeMethod");
        loginPage = Utils.setUpWebBrowser(browser);
    }

    @Test//(description = "L1. Login module - Login to the web app using valid credentials", groups = {"P1"})
    public void userLoginWithCorrectCredentialsTest() {
        /* Test steps:
         1. Navigate to Login page (in browsers address bar enter http://samsara.com or http://localhost:8080/)
         2. On Login Page enter valid credentials in username and password text fields
         3. On Login page, click Log in button
         Expected result: Samsara greeting page (http://samsara.com/ or http://localhost:8080/) is displayed  */

        log.info("Entered userLoginWithCorrectCredentials test!");
        loginSuccessful = false;
        samsaraPage = loginPage.logIn(testConfiguration.getUserUsername(), testConfiguration.getPassword());
        // Validation on Samsara page implemented within method login() itself
        loginSuccessful = true;
    }

//    @Test(description = "L2. Login module - Login to the web app using invalid credentials", groups = {"P2"})
//    public void userLoginWithIncorrectCredentialsTest() {
//        /* Test steps:
//         1. Navigate to Login page (in browsers address bar enter http://www.samsara.com or http://localhost:8080/)
//         2. On Login Page enter invalid credentials in username field
//         3. On Login page, click Log in button
//         Expected: Login denied. Error message "Invalid username and password." displayed   */
//
//        Log.info("Entered userLoginWithCorrectCredentials test!");
//        loginSuccessful = false;
//        // Validation on error message implemented in method loginWithInvalidCredentials() itself
//    }
//
//    @Test(description = "H1. Hero module - Verify user can create new hero", groups = {"P1"})
//    public void userCreateNewHeroTest() {
//        Log.info("Entered userCreateNewHero test!");
//        loginSuccessful = false;
//        samsaraPage = loginPage.login(testConfiguration.getUsername2(), testConfiguration.getPassword());
//        loginSuccessful = true;
//        heroesPage = samsaraPage.navigateToHeroesPage();
//        heroesPage.addHero(hero1Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), heroClass); // Creating hero
//        hero1Created = heroesPage.isHeroDisplayed(hero1Name); // If hero is found in the list(s), this will set flag to true
//        Assert.assertTrue(hero1Created); // This is the goal of the test
//    }
//
//    @Test(description = "2. Verify user can edit existing own hero")
//    public void editExistingOwnHeroTest() {
//        Log.info("Entered editExistingOwnHero test!");
//        loginSuccessful = false;
//        samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
//        loginSuccessful = true;
//        heroesPage = samsaraPage.navigateToHeroesPage();
//        Assert.assertTrue(heroesPage.verifyHeroesPageIsDisplayed());
//        heroesPage.addHero(hero2Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), heroClass); // Creating hero
//        heroesPage.editHero(hero2Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), "Revenant");
//        Assert.assertTrue(heroesPage.isHeroDisplayed(hero2Name)); // This is the goal of the test
//        hero2Created = heroesPage.isHeroDisplayed(hero2Name); // If hero is found in the list(s), this will set flag to true
//    }

//    @Test(description = "3. Verify user can delete own hero")
//    public void deleteOwnHeroTest() {
//        Log.info("Entered deleteOwnHero test!");
//        loginSuccessful = false;
//
//        samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
//        Assert.assertTrue(samsaraPage.verifySamsaraPageDisplayed());
//        loginPage.wait(3);
//        loginSuccessful = true;
//        heroesPage = samsaraPage.navigateToHeroesPage();
//        heroesPage.addHero(hero3Name, level, "Ranger"); // Creating hero
//        Assert.assertTrue(heroesPage.isHeroDisplayed(hero3Name)); // Verifying created hero is in the list
//        heroesPage.deleteHero(hero3Name); // Deleting hero
//        Assert.assertFalse(heroesPage.isHeroDisplayed(hero3Name)); // This is the goal of the test,
//        // verifying that previously created, then deleted hero is no longer in the list(s)
//        hero3Created = heroesPage.isHeroDisplayed(hero3Name); // If hero is found in the list(s), this will set flag to false
//        loginSuccessful = true;
//    }
//
////    @Test(description = "4. Verify user can not delete heroes he does not own")
////    public void cantDeleteNotOwnHeroTest() {
////        Log.debug("Entered cantDeleteNotOwnHero test!");
////
////        loginSuccessful = true;
////        loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
////        loginSuccessful = true;
//////        hero1Created = true;
////    }
//
//    @Test(description = "5. Verify Admin can create user")
//    public void createUserAsAdminTest() {
//        Log.info("Entered createUserAsAdmin test!");
//        loginSuccessful = false;
//        samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
//        loginPage.wait(3);
//        loginSuccessful = true;
//        usersPage = samsaraPage.navigateToUsersPage();
//        usersPage.addUser(username1, firstName, lastName, about, secretQuestion, secretAnswer, password, conirfmPassword); // Creating user
//        Assert.assertTrue(usersPage.isUserDisplayed(username1)); // This is the goal of the test
//        user1Created = usersPage.isUserDisplayed(username1); // If user is found in the list(s), this will set flag to true
//    }
//
//    @Test(description = "6. Verify Admin can edit user")
//    public void editUserAsAdminTest() {
//        Log.info("Entered editUserAsAdmin test!");
//        loginSuccessful = false;
//        samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
//        Assert.assertTrue(samsaraPage.verifySamsaraPageDisplayed());
//        loginPage.wait(3);
//        loginSuccessful = true;
//        usersPage = samsaraPage.navigateToUsersPage();
//        Assert.assertTrue(usersPage.verifyUsersPageDisplayed());
//        usersPage.addUser(username2, "markko", "desspot", "aboout", "secretquestion", "secretanswer", password, conirfmPassword);
//        usersPage.editUser(username2, firstName, lastName, about); // Editing user created in previous step
//        Assert.assertTrue(usersPage.isUserDisplayed(username2)); // This is the goal of the test,
//        // verifying that edited user is in the list
//        user2Created = usersPage.isUserDisplayed(username2); // If user is found in the list(s), this will set flag to true
//    }
//
//    @Test(description = "7. Verify Admin can delete user")
//    public void deleteUserAsAdmin() {
//        Log.info("Entered deleteUserAsAdmin test!");
//        loginSuccessful = false;
//        samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
//        Assert.assertTrue(samsaraPage.verifySamsaraPageDisplayed());
//        loginPage.wait(3);
//        loginSuccessful = true;
//        usersPage = samsaraPage.navigateToUsersPage();
//        Assert.assertTrue(usersPage.verifyUsersPageDisplayed());
//        usersPage.addUser(username3, "markko", "desspot", "aboout", "secretquestion", "secretanswer", password, conirfmPassword);
//        Assert.assertTrue(usersPage.isUserDisplayed(username3)); // Verifying user is created and in the list(s)
//        usersPage.deleteUser(username3); // Deleting user
//        Assert.assertFalse(usersPage.isUserDisplayed(username3)); // This is the goal of the test,
//        // verifying that deleted user is no longer in the list(s)
//        user2Created = usersPage.isUserDisplayed(username3); // If hero is found in the list(s), this will set flag to false
//        loginSuccessful = true;
//    }
//
////    @Test(description = "8. Verify \"Show my heroes only\" button is working as per design")
////    public void showMyHeroesOnly() {
////        loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
////        loginSuccessful = true;
////        hero1Created = true;
////
////    }
//
////    @Test(description = "9. Verify newly added user is displayed on \"Last added users\" list")
////    public void lastAddedUsers() {
////        loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
////        loginSuccessful = true;
////        user1Created = true;
////
////    }
//
////    @Test(description = "10. Verify newly added hero is displayed on \"Last added heroes\" list")
////    public void lastAddedHeroes() {
////        loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
////        loginSuccessful = true;
////        hero1Created = true;
////
////    }
//
////    @Test(description = "11. Verify user does not have access to admin section")
////    public void restrictedUserAccess() {
////        loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
////        loginSuccessful = true;
////        user1Created = true;
////
////    }
//
////    @Test(description = "12. Verify admin has access to admin section")
////    public void permittedAdminAccess() {
////        loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
////
////        loginSuccessful = true;
////        user1Created = true;
////
////    }
//
////    @Test(description = "13. Verify admin can recreate registration key")
////    public void recreateRegistrationKey() {
////        loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
////
////        loginSuccessful = true;
////        user1Created = true;
////    }

    /**
     * @Test(groups = {"P0"})
     * public void samsaraPageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * Assert.assertTrue(samsaraPage.verifySamsaraPageIsDisplayed(),"Samsara page is not displayed!");
     * }
     * @Test(groups = {"P0"})
     * public void homePageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * homePage = samsaraPage.navigateToHomePage();
     * Assert.assertTrue(homePage.verifyHomePageIsDisplayed(),"Home page is not displayed!");
     * }
     * @Test(groups = {"P0"})
     * public void UsersPageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * usersPage = samsaraPage.navigateToUsersPage();
     * Assert.assertTrue(usersPage.verifyUsersPageIsDisplayed(),"Users page is not displayed!");
     * }
     * @Test(groups = {"P0"})
     * public void HeroesPageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * heroesPage = samsaraPage.navigateToHeroesPage();
     * Assert.assertTrue(heroesPage.verifyHeroesPageIsDisplayed(),"Heroes page is not displayed!");
     * }
     * @Test(groups = {"P0"})
     * public void GalleryPageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * galleryPage = samsaraPage.navigateToGalleryPage();
     * Assert.assertTrue(galleryPage.verifyGalleryPageIsDisplayed(),"Gallery page is not displayed!");
     * }
     * @Test(groups = {"P0"})
     * public void ApiPageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * apiPage = samsaraPage.navigateToApiPage();
     * Assert.assertTrue(apiPage.verifyApiPageIsDisplayed(),"Api page is not displayed!");
     * }
     * @Test(groups = {"P0"})
     * public void BrokenLinkPageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * brokenLinkPage = samsaraPage.navigateToBrokenLinkPage();
     * Assert.assertTrue(brokenLinkPage.verifyBrokenLinkPageIsDisplayed(),"Broken Link page is not displayed!");
     * }
     * @Test(groups = {"P0"})
     * public void AdminPageCheck() {
     * loginSuccessful = false;
     * samsaraPage = loginPage.login(testConfiguration.getUsername(), testConfiguration.getPassword());
     * loginSuccessful = true;
     * adminPage = samsaraPage.navigateToAdminPage();
     * Assert.assertTrue(adminPage.verifyAdminPageIsDisplayed(),"Admin page is not displayed!");
     * }
     **/
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        Log.debug("tearDown() in @AfterMethod");
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.makeScreenshot(result);
        }

        if (loginSuccessful)
            loginPage.logOut();

    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        // Cleaning after all test have been executed, regardless of outcome
        Log.debug("Reverting changes... (afterTest() in @AfterClass)");
        if (hero1Created || hero2Created || hero3Created) {
            samsaraPage = loginPage.logIn(testConfiguration.getAdminName(), testConfiguration.getPassword());
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
            samsaraPage = loginPage.logIn(testConfiguration.getAdminName(), testConfiguration.getPassword());

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