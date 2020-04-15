package com.red.testframework.pageobjects;

import com.red.testframework.testconfiguration.TestConfiguration;
import com.red.testframework.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

public class SamsaraPage extends BasePage {

    private static Logger log = LoggerFactory.getLogger(SamsaraPage.class);

    @FindBy(xpath = "//a[@href='/heroes']")
    private WebElement heroesPageNavigator;

    @FindBy(xpath = "//a[@href='/users']")
    private WebElement usersPageNavigator;

    @FindBy(xpath = "//span[contains(@class,'log-out')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    public SamsaraPage(WebDriver driver) {
        super(driver);
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);    }

    public HeroesPage navigateToHeroesPage() {
        clickOnElement(heroesPageNavigator);
        Assert.assertTrue(new HeroesPage(driver).verifyHeroesPageDisplayed());
        Log.info("Heroes page is displayed");
        return new HeroesPage(driver);
    }

    public UsersPage navigateToUsersPage() {
        clickOnElement(usersPageNavigator);
        Assert.assertTrue(new UsersPage(driver).verifyUsersPageDisplayed());
        Log.info("Users page is displayed");
        return new UsersPage(driver);
    }

    public LoginPage logOut() {
        clickOnElement(logoutButton);
        Assert.assertTrue(new LoginPage(driver).verifySuccessfulLogoutMessageIsDisplayed());
        Log.debug("Successful logout message is displayed");
        return new LoginPage(driver);
    }

    public boolean verifySamsaraPageDisplayed() {
        return getElementText(panelTitle).equals("Hello, and welcome to our gamers page!");
    }
    private boolean verifyLoginPageDisplayed() {
        return getElementText(panelTitle).equals("Samsara");
    }
}
