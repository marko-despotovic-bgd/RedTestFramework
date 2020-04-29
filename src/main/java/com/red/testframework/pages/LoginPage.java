package com.red.testframework.pages;

import com.red.testframework.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.red.testframework.utils.Constants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.red.testframework.utils.Log;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage {

    // Locators
    @FindBy(id = "username")
    private WebElement usernameInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(xpath = "//input[@value='Log In']")
    public WebElement loginButton;
    @FindBy(xpath = "//div[@class='alert alert-success']")
    private WebElement alertSuccessMessage;
    @FindBy(xpath = "//div[@class='alert alert-danger']")
    private WebElement alertFailMessage;
    @FindBy(xpath = "//span[contains(@class,'log-out')]")
    private WebElement logoutButton;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    private Utils utils;
    private static Logger log = LoggerFactory.getLogger(BasePage.class);


    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        utils = new Utils();
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
    }

    public String getLogInButton() {
        // Intentionally added retrieving xpath from already known element, avoiding XPath redefining
        return "//" + loginButton.getTagName() + "[@value='" + loginButton.getAttribute("value") + "']";
    }

    public SamsaraPage logIn(String username, String password) {
        Log.info("Opening Samsara training web site");
        driver.get(utils.getProperty("app.url"));
        verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE);
        Assert.assertTrue(isDisplayed(loginButton), "Log In button is not displayed");
        Log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        Log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public LoginPage logInWithInvalidCredentials(String username, String password) {
        Log.info("Opening Samsara training web site");
        driver.get(utils.getProperty("app.url"));
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE), "Login page is not displayed!");
        Assert.assertTrue(isDisplayed(loginButton), "Log In button is not displayed");
        Log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(Constants.PANEL_TITLE_XPATH))));
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        Assert.assertEquals(getElementText(alertFailMessage), Constants.INVALID_CREDENTIALS_MESSAGE, "Unsuccessful Login message is not displayed!");
        Log.info("Unsuccessful Login message is displayed");
        return new LoginPage(driver);
    }

    public SamsaraPage adminLogIn() {
        Log.info("Opening Samsara training web site");
        driver.get(utils.getProperty("app.url"));
        verifyLoginPageIsDisplayed();
        Assert.assertTrue(isDisplayed(loginButton), "Log In button is not displayed");
        Log.debug("\nLogin with credentials:\nUsername: " + properties.getProperty("admin") + "\nPassword: " + properties.getProperty("admin"));
        fillInInputField(usernameInput, utils.getProperty("admin.username"));
        fillInInputField(passwordInput, utils.getProperty("password"));
        clickOnElement(loginButton);
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        Log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public void logOut() {
        Assert.assertTrue(isDisplayed(logoutButton), "Log Out button is not displayed");
        clickOnElement(logoutButton);
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE), "Login page is not displayed!");
        Assert.assertEquals(getElementText(alertSuccessMessage), Constants.SUCCESSFUL_LOGOUT_MESSAGE, "Successful Logout message is not displayed!");
        Log.debug("Successful Logout message is displayed");
    }

    public void verifyLoginPageIsDisplayed() {
        log.info("Opening Samsara training web site");
        driver.get(utils.getProperty("app.url"));
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE), "Login page is not displayed!");
    }
}
