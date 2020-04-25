package com.red.testframework.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.red.testframework.utils.TestConfiguration;
import com.red.testframework.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.red.testframework.utils.Log;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;


import com.red.testframework.pages.SamsaraPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
        testConfiguration = new TestConfiguration();
    }

    public String getLoginButton() {
        // Intentionally added retrieving xpath from already known element, avoiding XPath redefining
        return "//" + loginButton.getTagName() + "[@value='" + loginButton.getAttribute("value") + "']";
    }
//
//
//    public String enterWrongCredentials(String username, String password) {
//        driver.findElement(this.userName).sendKeys(username);
//        driver.findElement(this.password).sendKeys(password);
//
//        driver.findElement(this.loginBtn).click();
//        return driver.findElement(invalidCredentialsLabel).getText();
//    }
    //

//public class LoginPage extends BasePage {
//


//    // Constructor
//    public LoginPage(WebDriver driver) {
//        super(driver);
//        log = Log.getLog(this.getClass());
//        PageFactory.initElements(driver, this);
//    }

    public SamsaraPage logIn(String username, String password) {
        log.info("Opening Samsara training web site");
        driver.get(super.testConfiguration.getUrl());
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getLoginButton())));
        log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        loginButton.click();
        Assert.assertTrue(verifyPageIsDisplayed(driver.findElement(By.xpath(Constants.PANEL_TITLE_XPATH)), Constants.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public LoginPage logInWithInvalidCredentials(String username, String password) {
        log.info("Opening Samsara training web site");
        driver.get(super.testConfiguration.getUrl());
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOf(panelTitle));
        verifyPageIsDisplayed(driver.findElement(By.xpath(Constants.PANEL_TITLE_XPATH)), Constants.LOGIN_PAGE_PANEL_TITLE);
        log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertEquals(getElementText(alertFailMessage), Constants.INVALID_CREDENTIALS_MESSAGE, "Unsuccessful login message is not displayed!");
        log.info("Unsuccessful login message is displayed");
        return new LoginPage(driver);
    }


    public SamsaraPage adminLogIn() {
        verifyPageIsDisplayed(driver.findElement(By.xpath(Constants.PANEL_TITLE_XPATH)), Constants.LOGIN_PAGE_PANEL_TITLE);
        driver.manage().window().maximize();
        String url = testConfiguration.getUrl();
        logIn(testConfiguration.getProperties().getProperty("admin"), testConfiguration.getProperties().getProperty("password"));
        driver.navigate().to(url);
        Assert.assertEquals(getElementText(panelTitle), "Hello, and welcome to our gamers page!", "Samsara page is not displayed");
        log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public void logOut() {
        clickOnElement(logoutButton);
        verifyPageIsDisplayed(driver.findElement(By.xpath(Constants.PANEL_TITLE_XPATH)), Constants.LOGIN_PAGE_PANEL_TITLE);
        Assert.assertEquals(getElementText(alertSuccessMessage), Constants.SUCCESSFUL_LOGOUT_MESSAGE, "Successful logout message is not displayed!");
        log.debug("Successful logout message is displayed");
    }

    public boolean isLoggedIn(int timeout) {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            return driver.findElement(By.className("navbar-container")).isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public void wait(int timeout) {
        super.wait(timeout);
    }
}
