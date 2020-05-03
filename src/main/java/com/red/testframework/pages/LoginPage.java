package com.red.testframework.pages;

import com.red.testframework.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.red.testframework.utils.Constants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

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

    private final Utils utils;

    public LoginPage(WebDriver driver) {
        super(driver);
        utils = new Utils();
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
    }

    public void openSamsaraTrainingSite() { // Use loginPageIsDisplayed for opening Samsara page
        log.info("Executing ..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        driver.get(utils.getProperty("app.url"));
//        driver.get("http://google.com"); //<-- Testing purpose
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    public SamsaraPage logIn(String username, String password) {
        log.info("Executing ..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        verifyLoginPageTitleIsDisplayed();
        verifyLogInButtonIsDisplayed();
        log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        log.info("Samsara page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new SamsaraPage(driver);
    }

    public LoginPage logInWithInvalidCredentials(String username, String password) {
        log.info("Executing ..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        verifyLoginPageTitleIsDisplayed();
        verifyLogInButtonIsDisplayed();
        log.info("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        verifyLoginPageTitleIsDisplayed();
        Assert.assertEquals(getElementText(alertFailMessage), Constants.INVALID_CREDENTIALS_MESSAGE, "Unsuccessful Login message is not displayed!");
        log.info("Unsuccessful Login message is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new LoginPage(driver);
    }

    public SamsaraPage adminLogIn() {
        log.info("Executing ..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        verifyLoginPageTitleIsDisplayed();
        verifyLogInButtonIsDisplayed();
        log.debug("\nLogin with credentials:\nUsername: " + properties.getProperty("admin") + "\nPassword: " + properties.getProperty("admin"));
        fillInInputField(usernameInput, utils.getProperty("admin.username"));
        fillInInputField(passwordInput, utils.getProperty("password"));
        clickOnElement(loginButton);
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        log.info("Welcome message on Samsara page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new SamsaraPage(driver);
    }

    public void logOut() {
        log.info("Executing ..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(logoutButton), "Log Out button is not displayed");
        clickOnElement(logoutButton);
        verifyLoginPageTitleIsDisplayed();
        Assert.assertEquals(getElementText(alertSuccessMessage), Constants.SUCCESSFUL_LOGOUT_MESSAGE, "Successful Logout message is not displayed!");
        log.info("Successful Logout message is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    public boolean verifyLoginPageTitleIsDisplayed() {
        log.info("Executing ..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE), "Login page is not displayed!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE);
    }

    public boolean verifyLogInButtonIsDisplayed() {
        log.info("Executing ..." + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed("//input[@value='Log In']"), "Log In button is not displayed!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/isDisplayed("//input[@value='Log In']");
    }
}
