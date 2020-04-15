package com.red.testframework.pageobjects;

import com.red.testframework.testconfiguration.TestConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.red.testframework.utils.Log;
import com.red.testframework.utils.LoginUtils;
import com.red.testframework.utils.Verification;
import com.red.testframework.verificationobjects.SoftAssertMap;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;


public class LoginPage extends BasePage {

    private static Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Locators
    @FindBy(id = "username")
    private WebElement usernameInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(xpath = "//input[@type='submit']")
    private WebElement loginButton;
    @FindBy(xpath = "//div[@class='alert alert-success']")
    private WebElement alertSuccessMessage;
    @FindBy(xpath = "//div[@class='alert alert-danger']")
    private WebElement alertFailMessage;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public LoginPage typeUsername(String username) {
        Log.debug("Typing Username: " + username);
        fillInInputField(usernameInput, username);
        return this;
    }

    public LoginPage typePassword(String password) {
        Log.debug("Typing Password: " + password);
        fillInInputField(passwordInput, password);
        return this;
    }

    public HomePage clickOnLogin() {
        Log.debug("clickOnLogin");
        clickOnElement(loginButton);
        return new HomePage(driver);
    }

    public LoginPage clickOnLoginFail() {
        Log.debug("clickOnLogin");
        clickOnElement(loginButton);
        return this;
    }

    public SamsaraPage login(String username, String password) {
        driver.navigate().to(testConfiguration.getUrl());
        Log.debug("Login with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertTrue(new SamsaraPage(driver).verifySamsaraPageDisplayed());
        Log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public SamsaraPage loginWithInvalidCredentials(String username, String password) {
        driver.navigate().to(testConfiguration.getUrl());
        Log.debug("Login with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertTrue(verifyFailLoginMessageIsDisplayed());
        Log.info("Login not successful message is displayed");
        return new SamsaraPage(driver);
    }


    public SamsaraPage login() {
        driver.manage().window().maximize();
        try {
            String url = testConfiguration.getUrl();

            if (!url.contains("localhost")) {
                LoginUtils loginUtils = new LoginUtils(driver);
                // TODO: depending on tests modify this
                if (loginUtils.getWindowLocationHostname().isEmpty()) {
                    driver.navigate().to(url);
                    // TODO: add check for already logged in if needed later
                    login(testConfiguration.getUsername(), testConfiguration.getPassword());
                }
            } else {
                driver.navigate().to(url);
            }
        } catch (AssertionError e) {
            throw new AssertionError("Login was not successful! ");
        }
        Assert.assertTrue(new SamsaraPage(driver).verifySamsaraPageDisplayed());
        Log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public boolean verifySuccessfulLogoutMessageIsDisplayed() {
        return getElementText(alertSuccessMessage).equals("You have been logged out.");
    }
    public boolean verifyFailLoginMessageIsDisplayed() {
        return getElementText(alertFailMessage).equals("Invalid username and password.");
    }



    // unchecked suppression is needed for Soft Assert
    @SuppressWarnings("unchecked")
    public void verificationExample() {
        // do some tests
        int x = 2;
        Verification.verify(x == 2, true, "2 + 2 = 4");

        String piken = "Ticks for Piken";
        Verification.verify(piken, "Ticks for Piken", "For Piken!");

        boolean thisIsTrue = true;
        Verification.verifyTrue(thisIsTrue, "This will pass");

        boolean thisIsFalse = false;
        boolean thisWillFail = false;
        boolean butAllTestsWillRun = true;
        boolean powerOfSoftAssert = true;

        // Soft Assert verifies all, doesn't stop, won't stop, even if it fails
        // Just like Piken Blob!
        // class in parameter is needed for logs
        // messages must be different for this to work
        @SuppressWarnings("rawtypes")
        SoftAssertMap sam = new SoftAssertMap(this.getClass());
        sam.add(thisIsTrue, true, "This will pass 1");
        sam.add(thisIsFalse, false, "This will pass 2");
        sam.add(thisWillFail, false, "This will fail 1");
        sam.add(butAllTestsWillRun, true, "This will pass 3");
        sam.add(powerOfSoftAssert, true, "This will pass 4");
        sam.verify();
    }

    public void wait(int timeout) {
        super.wait(timeout);
    }
}
