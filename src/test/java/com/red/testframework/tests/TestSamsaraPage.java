package com.red.testframework.tests;

import com.red.testframework.pages.SamsaraPage;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestSamsaraPage extends BaseTest {

    boolean loginSuccessful = false;
    SamsaraPage samsaraPage;

    // Verify Samsara page is opened after login
    @Test(groups = {Constants.CRITICAL})
    public void loginAndSamsaraPageDisplayed() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        samsaraPage = loginPage.adminLogin();
        samsaraPage.isSamsaraPageTitleDisplayed();
    }

    // Verify "Start Testing!" button is displayed
    @Test(groups = {Constants.HIGH},  dependsOnMethods = "loginAndSamsaraPageDisplayed")
    public void verifyStartTestingButtonIsDisplayed() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        samsaraPage.isStartTestingButtonDisplayed();
    }

    // Verify "Share with friends!" button is displayed
    @Test(groups = {Constants.MEDIUM},  dependsOnMethods = "loginAndSamsaraPageDisplayed")
    public void verifyShareWithFriendsButtonIsDisplayed() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        samsaraPage.isShareWithFriendsButtonDisplayed();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        Log.endTest(new Object(){}.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
        if (loginSuccessful)
            loginPage.logOut();
        loginSuccessful = false;
    }
}