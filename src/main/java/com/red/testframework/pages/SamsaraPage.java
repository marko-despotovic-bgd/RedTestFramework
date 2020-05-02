package com.red.testframework.pages;

import com.red.testframework.utils.Log;
import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class SamsaraPage extends BasePage {

    //Page locators
    @FindBy(xpath = "//a[@href='/home']")
    private WebElement homePageNavigator;
    @FindBy(xpath = "//a[@href='/users']")
    private WebElement usersPageNavigator;
    @FindBy(xpath = "//a[@href='/heroes']")
    private WebElement heroesPageNavigator;
    @FindBy(xpath = "//a[@href='/gallery']")
    private WebElement galleryPageNavigator;
    @FindBy(xpath = "//a[@href='/api']")
    private WebElement apiPageNavigator;
    @FindBy(xpath = "//a[@href='/broken']")
    private WebElement brokenPageNavigator;
    @FindBy(xpath = "//a[@href='/admin']")
    private WebElement adminPageNavigator;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-lg'][text()='Start Testing!']")
    private WebElement startTestingButton;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-lg'][text()='Share with friends!']")
    private WebElement shareWithFriendsButton;

    public SamsaraPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean verifySamsaraPageIsDisplayed() {
        return verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE);
    }

    // Since this is landing page, where not really much of stuff related to app functionality is placed,
    // placing pages'navigation in this class
    public HomePage navigateToHomePage() {
        clickOnElement(homePageNavigator);
        Assert.assertTrue(new HomePage(driver).verifyHomePageIsDisplayed(), "Home page is not displayed!");
        log.info("Home page is displayed");
        return new HomePage(driver);
    }

    public UsersPage navigateToUsersPage() {
        clickOnElement(usersPageNavigator);
        Assert.assertTrue(new UsersPage(driver).verifyUsersPageIsDisplayed(), "Users page is not displayed!");
        log.info("Users page is displayed");
        return new UsersPage(driver);
    }

    public HeroesPage navigateToHeroesPage() {
        clickOnElement(heroesPageNavigator);
        Assert.assertTrue(new HeroesPage(driver).verifyHeroesPageIsDisplayed(), "Heroes page is not displayed!");
        log.info("Heroes page is displayed");
        return new HeroesPage(driver);
    }


    public GalleryPage navigateToGalleryPage() {
        clickOnElement(galleryPageNavigator);
        Assert.assertTrue(new GalleryPage(driver).verifyGalleryPageIsDisplayed(), "Gallery page is not displayed!");
        log.info("Gallery page is displayed");
        return new GalleryPage(driver);
    }

    public ApiPage navigateToApiPage() {
        clickOnElement(apiPageNavigator);
        Assert.assertTrue(new ApiPage(driver).verifyApiPageIsDisplayed(), "Api page is not displayed!");
        log.info("Api page is displayed");
        return new ApiPage(driver);
    }

    public BrokenLinkPage navigateToBrokenLinkPage() {
        clickOnElement(brokenPageNavigator);
        Assert.assertTrue(new BrokenLinkPage(driver).verifyBrokenLinkPageIsDisplayed(), "Broken Link page is not displayed!");
        log.info("Broken Link page is displayed");
        return new BrokenLinkPage(driver);
    }

    public AdminPage navigateToAdminPage() {
        clickOnElement(adminPageNavigator);
        Assert.assertTrue(new AdminPage(driver).verifyAdminPageIsDisplayed(), "Users page is not displayed!");
        log.info("Users page is displayed");
        return new AdminPage(driver);
    }

    public void verifyStartTestingButtonIsDisplayed() {
        Assert.assertTrue(isDisplayed("//a[@class='btn btn-primary btn-lg'][text()='Start Testing!']"), "Log In button is not displayed!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    public void verifyShareWithFriendsButtonIsDisplayed() {
        Assert.assertTrue(isDisplayed("//a[@class='btn btn-primary btn-lg'][text()='Share with friends!']"), "Log In button is not displayed!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }
}