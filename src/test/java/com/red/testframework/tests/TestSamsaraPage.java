package com.red.testframework.tests;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestSamsaraPage extends BaseTest {

    /**  TC naming: test_state(page)_actions_expectedResult()  **/

    @Test(description = "Verify Samsara page is opened after login.", groups = {Constants.SANITY})
    public void test_samsaraPage_loginWithAdminCredentials_adminLogIn() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        loginPage.openSamsaraTrainingSite();
        assert loginPage.isLoginPageTitleDisplayed() : "Login Page title is not displayed";
        samsaraPage = loginPage.adminLogin();
        assert samsaraPage.isSamsaraPageTitleDisplayed() : "Samsara Page title is not displayed";
    }

    @Test(description = "Verify \"Start Testing!\" button is displayed.",groups = {Constants.CRITICAL},  dependsOnMethods = "test_samsaraPage_loginWithAdminCredentials_adminLogIn")
    public void test_samsaraPage_verifyStartTestingButtonIsDisplayed_startTestingButtonIsDisplayed() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        assert samsaraPage.isStartTestingButtonDisplayed() : "\"Start Testing!\" button is not displayed!";
    }

    @Test(description = "Verify \"Start Testing!\" button is displayed.",groups = {Constants.HIGH},  dependsOnMethods = "test_samsaraPage_loginWithAdminCredentials_adminLogIn")
    public void test_samsaraPage_verifyShareWithFriendsButtonIsDisplayed_shareWithFriendsButtonIsDisplayed() {
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName());
        assert samsaraPage.isShareWithFriendsButtonDisplayed() : "\"Share with friends!\" button is not displayed!";
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        Log.endTest(new Object(){}.getClass().getEnclosingMethod().getName() + " in @AfterMethod");
    }
}