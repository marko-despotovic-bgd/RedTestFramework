package com.red.testframework.pageobjects;

import com.red.testframework.testconfiguration.TestConfiguration;
import com.red.testframework.utils.XPathUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.red.testframework.utils.Log;
import com.red.testframework.utils.LoginUtils;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;


public class LoginPage extends BasePage {

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
    @FindBy(xpath = "//span[contains(@class,'log-out')]")
    private WebElement logoutButton;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    private static Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public SamsaraPage login(String username, String password) {
        driver.navigate().to(testConfiguration.getUrl());
        Log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertTrue(verifyPageIsDisplayed(driver.findElement(By.xpath(XPathUtil.PANEL_TITLE_XPATH)), XPathUtil.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        Log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public LoginPage loginWithInvalidCredentials(String username, String password) {
        driver.navigate().to(testConfiguration.getUrl());
        verifyPageIsDisplayed(driver.findElement(By.xpath(XPathUtil.PANEL_TITLE_XPATH)), XPathUtil.LOGIN_PAGE_PANEL_TITLE);
        Log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertEquals(getElementText(alertFailMessage), XPathUtil.INVALID_CREDENTIALS_MESSAGE, "Unsuccessful login message is not displayed!");
        Log.info("Unsuccessful login message is displayed");
        return new LoginPage(driver);
    }


    public SamsaraPage adminLogin() {
        verifyPageIsDisplayed(driver.findElement(By.xpath(XPathUtil.PANEL_TITLE_XPATH)), XPathUtil.LOGIN_PAGE_PANEL_TITLE);
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
        Assert.assertEquals(getElementText(panelTitle), "Hello, and welcome to our gamers page!", "Samsara page is not displayed");
        Log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public void logOut() {
        clickOnElement(logoutButton);
        verifyPageIsDisplayed(driver.findElement(By.xpath(XPathUtil.PANEL_TITLE_XPATH)), XPathUtil.LOGIN_PAGE_PANEL_TITLE);
        Assert.assertEquals(getElementText(alertSuccessMessage), XPathUtil.SUCCESSFUL_LOGOUT_MESSAGE, "Successful logout message is not displayed!");
        Log.debug("Successful logout message is displayed");
    }

    public void wait(int timeout) {
        super.wait(timeout);
    }
}
