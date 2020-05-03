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

import com.red.testframework.pages.HeroesPage;
import com.red.testframework.pages.SamsaraPage;
import com.red.testframework.pages.UsersPage;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.ScreenshotUtil;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class SamsaraUITests extends BaseTest {

    private SamsaraPage samsaraPage;
    private UsersPage usersPage;
    private HeroesPage heroesPage;

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
    public void userCreateNewHeroTest() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
        loginSuccessful = true;
        heroesPage = samsaraPage.navigateToHeroesPage();
        heroesPage.addHero(hero1Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), heroClass); // Creating hero
        hero1Created = heroesPage.isHeroDisplayed(hero1Name); // If hero is found in the list(s), this will set flag to true
        Assert.assertTrue(hero1Created); // This is the goal of the test
    }

    @Test(groups = {Constants.MEDIUM})
    public void editExistingOwnHeroTest() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
        loginSuccessful = true;
        heroesPage = samsaraPage.navigateToHeroesPage();
        Assert.assertTrue(heroesPage.verifyHeroesPageIsDisplayed());
        heroesPage.addHero(hero2Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), heroClass); // Creating hero
        heroesPage.editHero(hero2Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), "Revenant");
        Assert.assertTrue(heroesPage.isHeroDisplayed(hero2Name)); // This is the goal of the test
        hero2Created = heroesPage.isHeroDisplayed(hero2Name); // If hero is found in the list(s), this will set flag to true
    }

    @Test(groups = {Constants.MEDIUM})
    public void deleteOwnHeroTest() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
        loginSuccessful = true;
        heroesPage = samsaraPage.navigateToHeroesPage();
        heroesPage.addHero(hero3Name, Integer.toString(ThreadLocalRandom.current().nextInt(1, 80)), "Ranger"); // Creating hero
        Assert.assertTrue(heroesPage.isHeroDisplayed(hero3Name)); // Verifying created hero is in the list
        heroesPage.deleteHero(hero3Name); // Deleting hero
        Assert.assertFalse(heroesPage.isHeroDisplayed(hero3Name)); // This is the goal of the test,
        // verifying that previously created, then deleted hero is no longer in the list(s)
        hero3Created = heroesPage.isHeroDisplayed(hero3Name); // If hero is found in the list(s), this will set flag to false
        loginSuccessful = true;
    }

//    @Test(dependsOnMethods = "userCreateNewHeroTest")
//    public void cantDeleteNotOwnHeroTest() {
//        Log.startTest(new Object() {
//        }.getClass().getEnclosingMethod().getName());
//        samsaraPage = loginPage.logIn(utils.getProperty("user.username"), utils.getProperty("password"));
//        loginSuccessful = true;
//        heroesPage.deleteHero(hero1Name); // Deleting hero
//        hero1Created = true;
//    }

    @Test(groups = {Constants.HIGH})
    public void createUserAsAdminTest() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.adminLogIn();
        loginSuccessful = true;
        usersPage = samsaraPage.navigateToUsersPage();
        usersPage.addUser(username1, firstName, lastName, about, secretQuestion, secretAnswer, password, conirfmPassword); // Creating user
        Assert.assertTrue(usersPage.isUserDisplayed(username1)); // This is the goal of the test
        user1Created = usersPage.isUserDisplayed(username1); // If user is found in the list(s), this will set flag to true
    }

    @Test(groups = {Constants.HIGH})
    public void editUserAsAdminTest() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.adminLogIn();
        loginSuccessful = true;
        usersPage = samsaraPage.navigateToUsersPage();
        usersPage.addUser(username2, firstName, lastName, about, secretQuestion, secretAnswer, password, conirfmPassword); // Creating user
        Assert.assertTrue(usersPage.isUserDisplayed(username2)); // This is the goal of the test
        user2Created = usersPage.isUserDisplayed(username2); // If user is found in the list(s), this will set flag to true
        usersPage.editUser(username2, firstName + firstName, lastName, about + firstName); // Editing user created in previous step
        Assert.assertTrue(usersPage.isUserDisplayed(username2)); // This is the goal of the test,
        // verifying that edited user is in the list
        user2Created = usersPage.isUserDisplayed(username2); // If user is found in the list(s), this will set flag to true
    }

    @Test(groups = {Constants.HIGH})
    public void deleteUserAsAdmin() {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.adminLogIn();
        loginSuccessful = true;
        usersPage = samsaraPage.navigateToUsersPage();
        usersPage.addUser(username3, "markko", "desspot", "aboout", "secretquestion", "secretanswer", password, conirfmPassword);
        Assert.assertTrue(usersPage.isUserDisplayed(username3)); // Verifying user is created and in the list(s)
        user3Created = usersPage.isUserDisplayed(username3); // If user is found in the list(s), this will set flag to true
        usersPage.deleteUser(username3); // Deleting user
        Assert.assertFalse(usersPage.isUserDisplayed(username3)); // This is the goal of the test,
        // verifying that deleted user is no longer in the list(s)
        user3Created = usersPage.isUserDisplayed(username3); // If hero is found in the list(s), this will set flag to false
        loginSuccessful = true;
    }

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
        // Cleaning after all test have been executed, regardless of outcome
        Log.endTest(new Object() {
        }.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.makeScreenshot(result);
        }
        assert loginPage != null;
        if (loginSuccessful) {
            loginPage.logOut();
            loginSuccessful = false;
        }
        loginPage.openSamsaraTrainingSite();
        if (hero1Created || hero2Created || hero3Created || hero4Created || hero5Created || user1Created || user2Created || user3Created || user4Created) {
            Log.info("=========== Reverting changes");
            samsaraPage = loginPage.adminLogIn();

            if (hero1Created || hero2Created || hero3Created || hero4Created || hero5Created) {
                Log.info("----- Deleting created hero(es)");
                heroesPage = samsaraPage.navigateToHeroesPage();
                if (hero1Created) {
                    Log.info("Deleting " + hero1Name + "...");
                    heroesPage.deleteHero(hero1Name);
                    Log.info("Hero " + hero1Name + " has been deleted!");
                }
                if (hero2Created) {
                    Log.info("Deleting " + hero2Name + "...");
                    heroesPage.deleteHero(hero2Name);
                    Log.info("Hero " + hero2Name + " has been deleted!");
                }
                if (hero3Created) {
                    Log.info("Deleting " + hero3Name + "...");
                    heroesPage.deleteHero(hero3Name);
                    Log.info("Hero " + hero3Name + " has been deleted!");
                }
                if (hero4Created) {
                    Log.info("Deleting " + hero4Name + "...");
                    heroesPage.deleteHero(hero4Name);
                    Log.info("Hero " + hero4Name + " has been deleted!");
                }
                if (hero5Created) {
                    Log.info("Deleting " + hero5Name + "...");
                    heroesPage.deleteHero(hero5Name);
                    Log.info("Hero " + hero5Name + " has been deleted!");
                }
                loginPage.logOut();
            }

            if (user1Created || user2Created || user3Created || user4Created) {
                Log.info("----- Deleting created user(s)");
                usersPage = samsaraPage.navigateToUsersPage();
                if (user1Created) {
                    Log.info("Deleting " + username1 + "...");
                    usersPage.deleteUser(username1);
                    Log.info("User " + username1 + " has been deleted!");
                }
                if (user2Created) {
                    Log.info("Deleting " + username2 + "...");
                    usersPage.deleteUser(username2);
                    Log.info("User " + username2 + " has been deleted!");
                }
                if (user3Created) {
                    Log.info("Deleting " + username3 + "...");
                    usersPage.deleteUser(username3);
                    Log.info("User " + username3 + " has been deleted!");
                }
                loginPage.logOut();
                Log.info("========================");
            }
        }

        if (loginSuccessful)
            loginSuccessful = false;
    }
}