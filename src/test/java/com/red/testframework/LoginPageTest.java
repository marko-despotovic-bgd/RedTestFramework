package com.red.testframework;

import com.red.testframework.pages.BasePage;
import com.red.testframework.pages.LoginPage;
import com.red.testframework.utils.TestGroups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    BasePage basePage;
    LoginPage loginPage;

//
//    @BeforeMethod(alwaysRun = true)
//    public void loguotIfNeeded() {
//        basePage = new BasePage(driver);
//        loginPage = new LoginPage(driver);
//
//        if(basePage.isLoggedIn(0))
//            loginPage.logOut();
//
//        Assert.assertFalse(loginPage.isLoggedIn(3), "User is not logged in");
//
//    }
//
//    @Test(groups = {TestGroups.CRITICAL}, description = "Login as admin")
//    public void loginAsSCBAdmin() {
//        loginPage = new LoginPage(driver);
//        loginPage.logIn(adminUsername,adminPassword);
//
//        Assert.assertTrue(loginPage.isLoggedIn(3), "SCB Admin is logged in");
//    }
}