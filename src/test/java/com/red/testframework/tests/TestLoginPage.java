package com.red.testframework.tests;

import com.red.testframework.utils.TestConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import com.red.testframework.utils.Utils;
import com.red.testframework.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestLoginPage {

    private LoginPage loginPage;
    private TestConfiguration testConfiguration;
    private static Logger log = LoggerFactory.getLogger(TestLoginPage.class);

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("firefox") String browser) {
        loginPage = Utils.setUpWebBrowser(browser);
        testConfiguration = new TestConfiguration();
    }

    /*
     * Test validates that login page is opened by checking if log in button is
     * visible on the page
     */
    @Test(groups = {"P0"})
    public void testLoginPageIsOpened() {

        loginPage.logIn(testConfiguration.getUserUsername(), testConfiguration.getPassword());

    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {"P1"})
    public void testUnsuccessfulLogin() {

        loginPage = loginPage.logInWithInvalidCredentials(testConfiguration.getAdminName() + "fail", testConfiguration.getPassword());

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (loginPage != null)
            loginPage.quitWebDriver();
    }
}
