package com.red.testframework.pages;

import com.red.testframework.testconfiguration.TestConfiguration;
import com.red.testframework.utils.PageConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.red.testframework.utils.Log;
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
        driver.get(testConfiguration.getUrl());
        log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertTrue(verifyPageIsDisplayed(driver.findElement(By.xpath(PageConstants.PANEL_TITLE_XPATH)), PageConstants.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public LoginPage loginWithInvalidCredentials(String username, String password) {
        driver.navigate().to(testConfiguration.getUrl());
        verifyPageIsDisplayed(driver.findElement(By.xpath(PageConstants.PANEL_TITLE_XPATH)), PageConstants.LOGIN_PAGE_PANEL_TITLE);
        log.debug("\nLogin with credentials:\nUsername: " + username + "\nPassword: " + password);
        fillInInputField(usernameInput, username);
        fillInInputField(passwordInput, password);
        clickOnElement(loginButton);
        Assert.assertEquals(getElementText(alertFailMessage), PageConstants.INVALID_CREDENTIALS_MESSAGE, "Unsuccessful login message is not displayed!");
        log.info("Unsuccessful login message is displayed");
        return new LoginPage(driver);
    }


    public SamsaraPage adminLogin() {
        verifyPageIsDisplayed(driver.findElement(By.xpath(PageConstants.PANEL_TITLE_XPATH)), PageConstants.LOGIN_PAGE_PANEL_TITLE);
        driver.manage().window().maximize();
        String url = testConfiguration.getUrl();
        login(testConfiguration.getUsername(), testConfiguration.getPassword());
        driver.navigate().to(url);
        Assert.assertEquals(getElementText(panelTitle), "Hello, and welcome to our gamers page!", "Samsara page is not displayed");
        log.info("Samsara page is displayed");
        return new SamsaraPage(driver);
    }

    public void logOut() {
        clickOnElement(logoutButton);
        verifyPageIsDisplayed(driver.findElement(By.xpath(PageConstants.PANEL_TITLE_XPATH)), PageConstants.LOGIN_PAGE_PANEL_TITLE);
        Assert.assertEquals(getElementText(alertSuccessMessage), PageConstants.SUCCESSFUL_LOGOUT_MESSAGE, "Successful logout message is not displayed!");
        log.debug("Successful logout message is displayed");
    }

    public void wait(int timeout) {
        super.wait(timeout);
    }
}
