/********************************************************************************************
 Test suite:                                                                                *
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

//        Verify search by hero name displays correct table
//        Verify search by user name displays correct table
//        Verify user actions - rename hero is saved in audit table
//        Verify admin actions - rename hero is saved in audit table

package com.red.testframework.tests;

import com.red.testframework.pages.*;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class SamsaraUITests extends BaseTest {

    private SamsaraPage samsaraPage;
    private HomePage homePage;
    private UsersPage usersPage;
    private HeroesPage heroesPage;
    private GalleryPage galleryPage;
    private ApiPage apiPage;
    private BrokenLinkPage brokenLinkPage;
    private AdminPage adminPage;

    String timestamp = new SimpleDateFormat("HHmmss").format(new Timestamp(new Date().getTime())); // To secure non-redundancy in user/hero creating

    String username1 = "despot" + timestamp,
            username2 = "a" + username1 + "1",
            username3 = "z" + username2 + "2",
            username4 = "az" + username3 + "3",
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
            hero4Name = "ZA" + hero3Name,
            hero5Name = "AZ" + hero4Name,

    heroClass = "Guardian";
    // All input data follow restriction of the original app
    // Names intentionally start with letters "a" and "z", enforcing search through page lists

    boolean loginSuccessful, hero1Created, hero2Created, hero3Created, hero4Created, hero5Created,
            user1Created, user2Created, user3Created, user4Created = false;

    @Test(groups = {Constants.HIGH})
    public void testCreateNewHeroAsUser() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login page is not displayed!";
        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
        assert samsaraPage.isSamsaraPageTitleDisplayed() : "Samsara page is not displayed";
        loginSuccessful = samsaraPage.isSamsaraPageTitleDisplayed();
        heroesPage = samsaraPage.navigateToHeroesPage();
        assert heroesPage.isHeroesPageTitleDisplayed() : "Hero is not created";
        heroesPage.addHero(hero1Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), heroClass); // Creating hero
        hero1Created = heroesPage.isHeroDisplayed(hero1Name); // If hero is found in the list(s), this will set flag to true
        assert hero1Created : "User was not able to create a hero!"; // This is the goal of the test
        loginPage.logOut();
    }

    @Test(groups = {Constants.HIGH})
    public void testEditExistingOwnHeroAsUser() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed();
        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
        assert samsaraPage.isSamsaraPageTitleDisplayed();
        loginSuccessful = samsaraPage.isSamsaraPageTitleDisplayed();
        heroesPage = samsaraPage.navigateToHeroesPage();
        assert heroesPage.isHeroesPageTitleDisplayed() : "Hero page is not displayed!";
        heroesPage.addHero(hero2Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), heroClass); // Creating hero
        hero2Created = heroesPage.isHeroDisplayed(hero2Name);
        assert hero2Created : "User was not able to create hero!";
        ; // If hero is found in the list(s), this will set flag to true
        heroesPage.editHero(hero2Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), "Revenant");
        hero2Created = heroesPage.isHeroDisplayed(hero2Name); // This is the goal of the test
        assert hero2Created : "User was not able to edit own hero!";
        ; // If hero is found in the list(s), this will set flag to true
        loginPage.logOut();
    }

    @Test(groups = {Constants.MEDIUM})
    public void testDeleteOwnHeroAsUser() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed();
        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
        assert samsaraPage.isSamsaraPageTitleDisplayed();
        loginSuccessful = samsaraPage.isSamsaraPageTitleDisplayed();
        heroesPage = samsaraPage.navigateToHeroesPage();
        assert heroesPage.isHeroesPageTitleDisplayed() : "Hero page is not displayed!";
        heroesPage.addHero(hero3Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), heroClass); // Creating hero
        hero3Created = heroesPage.isHeroDisplayed(hero3Name);
        assert hero3Created : "User was not able to create hero!";
        ; // If hero is found in the list(s), this will set flag to true
        heroesPage.deleteHero(hero3Name); // Deleting hero
        hero3Created = heroesPage.isHeroDisplayed(hero3Name); // If hero is found in the list(s), this will set flag to false
        assert !hero3Created; // This is the goal of the test,
        // verifying that previously created, then deleted hero is no longer in the list(s)
        loginPage.logOut();
    }

//    @Test(dependsOnMethods = "userCreateNewHeroTest")
//    public void testUserCannotDeleteNotOwnHero() {
//        Log.startTest(new Object() {
//        }.getClass().getEnclosingMethod().getName());
//        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
//        loginSuccessful = true;
//        heroesPage.deleteHero(hero1Name); // Deleting hero
//        hero1Created = true;
//    }

//   @Test(groups = {Constants.MEDIUM})
//   public void testShowMyHeroesOnlyButton() {
//}
//   @Test(groups = {Constants.MEDIUM})
//   public void testLastAddedHeroesButton() {
//}

    @Test(groups = {Constants.CRITICAL})
    public void testCreateUserAsAdmin() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login page is not displayed!";
        samsaraPage = loginPage.adminLogIn();
        assert samsaraPage.isSamsaraPageTitleDisplayed() : "Samsara page is not displayed";
        loginSuccessful = samsaraPage.isSamsaraPageTitleDisplayed();
        usersPage = samsaraPage.navigateToUsersPage();
        assert usersPage.isUsersPageTitleDisplayed() : "Users page is not displayed!";
        usersPage.addUser(username1, firstName, lastName, about, secretQuestion, secretAnswer, password, conirfmPassword); // Creating user
        user1Created = usersPage.isUserDisplayed(username1);
        assert user1Created : "Admin was not able to create user!"; // This is the goal of the test
        loginPage.logOut();
    }

    @Test(groups = {Constants.HIGH})
    public void testEditUserAsAdmin() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login page is not displayed!";
        samsaraPage = loginPage.adminLogIn();
        assert samsaraPage.isSamsaraPageTitleDisplayed() : "Samsara page is not displayed";
        loginSuccessful = samsaraPage.isSamsaraPageTitleDisplayed();
        usersPage = samsaraPage.navigateToUsersPage();
        assert usersPage.isUsersPageTitleDisplayed() : "Users page is not displayed!";
        usersPage.addUser(username2, firstName, lastName, about, secretQuestion, secretAnswer, password, conirfmPassword); // Creating user
        user2Created = usersPage.isUserDisplayed(username2);
        assert user2Created : "Admin was not able to create user!"; // This is the goal of the test
        usersPage.editUser(username2, firstName + firstName, lastName, about + firstName); // Editing user created in previous step
        user2Created = usersPage.isUserDisplayed(username2);
        assert user2Created : "Admin was not able to edit user!"; // This is the goal of the test
        loginPage.logOut();
    }

    @Test(groups = {Constants.HIGH})
    public void testDeleteUserAsAdmin() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login page is not displayed!";
        samsaraPage = loginPage.adminLogIn();
        assert samsaraPage.isSamsaraPageTitleDisplayed() : "Samsara page is not displayed";
        loginSuccessful = samsaraPage.isSamsaraPageTitleDisplayed();
        usersPage = samsaraPage.navigateToUsersPage();
        assert usersPage.isUsersPageTitleDisplayed() : "Users page is not displayed!";
        usersPage.addUser(username3, firstName, lastName, about, secretQuestion, secretAnswer, password, conirfmPassword); // Creating user
        user3Created = usersPage.isUserDisplayed(username3);
        assert user3Created : "Admin was not able to create user!"; // This is the goal of the test
        usersPage.deleteUser(username3); // Deleting user
        user3Created = usersPage.isUserDisplayed(username3);
        assert !user3Created;
        loginPage.logOut();
    }

    //   @Test(groups = {Constants.MEDIUM})
//   public void testLastAddedUsersButton() {
//}

    //   @Test(groups = {Constants.CRITICAL})
//   public void testUserCannotAccessAdminSection() {
//}

    //   @Test(groups = {Constants.CRITICAL})
//   public void testAdminCanAccessAdminSection() {
//}

    //   @Test(groups = {Constants.CRITICAL})
//   public void testAdminCanRecreateRegistrationKey() {
//}


    @Test(groups = {Constants.SANITY})
    public void samsaraPagesCheck() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.adminLogIn();
        loginSuccessful = samsaraPage.isSamsaraPageTitleDisplayed();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(samsaraPage.isSamsaraPageTitleDisplayed(), "Samsara page is not displayed!");
        homePage = samsaraPage.navigateToHomePage();
        softAssert.assertTrue(homePage.isHomePageTitleDisplayed(), "Home page is not displayed!");
        usersPage = samsaraPage.navigateToUsersPage();
        softAssert.assertTrue(usersPage.isUsersPageTitleDisplayed(), "Users page is not displayed!");
        heroesPage = samsaraPage.navigateToHeroesPage();
        softAssert.assertTrue(/*! <- Soft assert testing purpose*/heroesPage.isHeroesPageTitleDisplayed(), "Heroes page is not displayed!");
        galleryPage = samsaraPage.navigateToGalleryPage();
        softAssert.assertTrue(galleryPage.isGalleryPageTitleDisplayed(), "Gallery page is not displayed!");
        apiPage = samsaraPage.navigateToApiPage();
        softAssert.assertTrue(apiPage.isApiPageTitleDisplayed(), "Api page is not displayed!");
        brokenLinkPage = samsaraPage.navigateToBrokenLinkPage();
        softAssert.assertTrue(brokenLinkPage.isBrokenLinkPageTitleDisplayed(), "Broken link page is not displayed!");
        samsaraPage.clickSamsaraBrandLink();
        adminPage = samsaraPage.navigateToAdminPage();
        softAssert.assertTrue(adminPage.isAdminPageTitleDisplayed(), "Admin page is not displayed!");
        softAssert.assertAll();
        loginPage.logOut();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(@NotNull ITestResult result) {
        // Cleaning after all test have been executed, regardless of outcome
        Log.endTest(new Object() {
        }.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.makeScreenshot(result);
        }
        if (!loginPage.isLoginPageTitleDisplayed())
            loginPage.logOut();

        if (hero1Created || hero2Created || hero3Created || hero4Created || hero5Created || user1Created || user2Created || user3Created || user4Created) {
            Log.info("=========== Reverting changes");
            samsaraPage = loginPage.adminLogIn();

            if (hero1Created || hero2Created || hero3Created || hero4Created || hero5Created) {
                Log.info("----- Deleting created hero(es) -----");
                heroesPage = samsaraPage.navigateToHeroesPage();
                if (hero1Created) {
                    Log.info("Deleting " + hero1Name + "...");
                    heroesPage.deleteHero(hero1Name);
                    assert !heroesPage.isHeroDisplayed(hero1Name);
                    Log.info("Hero " + hero1Name + " has been deleted!");
                }
                if (hero2Created) {
                    Log.info("Deleting " + hero2Name + "...");
                    heroesPage.deleteHero(hero2Name);
                    assert !heroesPage.isHeroDisplayed(hero2Name);
                    Log.info("Hero " + hero2Name + " has been deleted!");
                }
                if (hero3Created) {
                    Log.info("Deleting " + hero3Name + "...");
                    heroesPage.deleteHero(hero3Name);
                    assert !heroesPage.isHeroDisplayed(hero3Name);
                    Log.info("Hero " + hero3Name + " has been deleted!");
                }
                if (hero4Created) {
                    Log.info("Deleting " + hero4Name + "...");
                    heroesPage.deleteHero(hero4Name);
                    assert !heroesPage.isHeroDisplayed(hero4Name);
                    Log.info("Hero " + hero4Name + " has been deleted!");
                }
                if (hero5Created) {
                    Log.info("Deleting " + hero5Name + "...");
                    heroesPage.deleteHero(hero5Name);
                    assert !heroesPage.isHeroDisplayed(hero5Name);
                    Log.info("Hero " + hero5Name + " has been deleted!");
                }
            }

            if (user1Created || user2Created || user3Created || user4Created) {
                Log.info("----- Deleting created user(s) -----");
                usersPage = samsaraPage.navigateToUsersPage();
                if (user1Created) {
                    Log.info("Deleting " + username1 + "...");
                    usersPage.deleteUser(username1);
                    assert !usersPage.isUserDisplayed(username1);
                    Log.info("User " + username1 + " has been deleted!");
                }
                if (user2Created) {
                    Log.info("Deleting " + username2 + "...");
                    usersPage.deleteUser(username2);
                    assert !usersPage.isUserDisplayed(username2);
                    Log.info("User " + username2 + " has been deleted!");
                }
                if (user3Created) {
                    Log.info("Deleting " + username3 + "...");
                    usersPage.deleteUser(username3);
                    assert !usersPage.isUserDisplayed(username3);
                    Log.info("User " + username3 + " has been deleted!");
                }
                if (user4Created) {
                    Log.info("Deleting " + username4 + "...");
                    usersPage.deleteUser(username4);
                    assert !usersPage.isUserDisplayed(username4);
                    Log.info("User " + username4 + " has been deleted!");
                }
                Log.info("========================");
            }
        }
        if (!loginPage.isLoginPageTitleDisplayed())
            loginPage.logOut();

        if (loginSuccessful)
            loginSuccessful = false;
    }
}