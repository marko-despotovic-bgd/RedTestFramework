package com.red.testframework.pages;

import com.red.testframework.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.red.testframework.utils.Constants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    // Locators (id > name > css > xpath)
    @FindBy(id = "username") private WebElement usernameInput;
    @FindBy(id = "password") private WebElement passwordInput;
    @FindBy(css = ".center-block") public WebElement loginButton;
    @FindBy(css = ".alert-success") private WebElement alertSuccessMessage;
    @FindBy(css = ".alert-danger") private WebElement alertFailMessage;
    @FindBy(css = ".glyphicon-log-out") private WebElement logoutButton;
    @FindBy(css = ".panel-title") private WebElement panelTitle;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Open URL

    public void openSamsaraTrainingSite() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        driver.get(Utils.getProperty("app.url"));
//      driver.get("http://google.com"); //<-- Testing purpose
        assert isLoginPageTitleDisplayed() : "Login Page Title is not displayed!";
        assert isLogInButtonDisplayed() : "\"Log In\" button is not displayed!";
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
    }

    // Logins

    public SamsaraPage adminLogin() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        log.debug("\nLogin with credentials:\nUsername: " + Utils.getProperty("admin.username") + "\nPassword: " + Utils.getProperty("admin.password"));
        assert isDisplayed(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(Utils.getProperty("admin.username"));
        assert usernameInput.getAttribute("value").equals(Utils.getProperty("admin.username")) : "Inserted text does not match username!"; // <-- Testing purpose Utils.getProperty("user.username")
        assert isDisplayed(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(Utils.getProperty("admin.password"));
        assert passwordInput.getAttribute("value").equals(Utils.getProperty("admin.password")) : "Inserted text does not match password!";
        assert isLogInButtonDisplayed() : "Login button is not displayed!";
        loginButton.click();
        assert verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE) : "Samsara page is not displayed!";
        log.info("Welcome message on Samsara page is displayed");
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        return new SamsaraPage(driver);
    }

    public SamsaraPage userLogin() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        log.info("\nLogin with credentials:\nUsername: " + Utils.getProperty("user.username") + "\nPassword: " + Utils.getProperty("user.password"));
        assert isDisplayed(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(Utils.getProperty("user.username"));
        assert usernameInput.getAttribute("value").equals(Utils.getProperty("user.username")) : "Inserted text does not match username!";
        assert isDisplayed(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(Utils.getProperty("user.password"));
        assert passwordInput.getAttribute("value").equals(Utils.getProperty("user.password")) : "Inserted text does not match password!";
        assert isLogInButtonDisplayed() : "Login button is not displayed!";
        loginButton.click();
        assert verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE) : "Samsara page is not displayed!";
        log.info("Welcome message on Samsara page is displayed");
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        return new SamsaraPage(driver);
    }

    public SamsaraPage customLogin(String username, String password) {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        log.debug("Login with credentials:\nUsername: " + username + "\nPassword: " + password);
        assert isDisplayed(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(username);
        assert usernameInput.getAttribute("value").equals(username) : "Inserted text does not match username!";
        assert isDisplayed(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        assert passwordInput.getAttribute("value").equals(password) : "Inserted text does not match password!";
        assert isLogInButtonDisplayed() : "Login button is not displayed!";
        loginButton.click();
        assert verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE) : "Samsara page is not displayed!";
        log.info("Welcome message on Samsara page is displayed");
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        return new SamsaraPage(driver);
    }

    public void loginWithInvalidCredentials(String username, String password) {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        assert isDisplayed(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(username);
        assert usernameInput.getAttribute("value").equals(username) : "Inserted text does not match username!";
        assert isDisplayed(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        assert passwordInput.getAttribute("value").equals(password) : "Inserted text does not match password!";
        assert isLogInButtonDisplayed() : "Login button is not displayed!";
        loginButton.click();
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
    }

    // Alerts

    public boolean isInvalidCredentialsErrorMessagePresent() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert getElementText(alertFailMessage).equals(Constants.INVALID_CREDENTIALS_MESSAGE) : "Invalid credentials error message is not displayed!";
        log.info("Invalid credentials error message is displayed");
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        return isDisplayed(alertFailMessage);
    }

    public boolean isSuccessfulLogoutMessageDisplayed()    {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert getElementText(alertSuccessMessage).equals(Constants.SUCCESSFUL_LOGOUT_MESSAGE) : "Successful Logout message is not displayed!";
        log.info("Successful Logout message is displayed");
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        return isDisplayed(alertSuccessMessage);
    }

    // Checks

    public boolean isLoginPageTitleDisplayed() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        boolean isDisplayed = verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.LOGIN_PAGE_PANEL_TITLE);
        log.info("Login page title is displayed: " + isDisplayed);
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        return isDisplayed;
    }

    private boolean isLogInButtonDisplayed() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        boolean isDisplayed = isDisplayed(loginButton);
        log.info("Log In button is displayed: " + isDisplayed);
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        return /*! <- Testing purpose*/isDisplayed;
    }

    // Clicks

    public void logOut() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(logoutButton) : "Log Out button is not displayed";
        logoutButton.click();
        assert isLoginPageTitleDisplayed() : "Login Page title is not displayed!";
        assert isSuccessfulLogoutMessageDisplayed() : "Successful logout message is not displayed!";
        log.info("Successfully executed ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
    }
}