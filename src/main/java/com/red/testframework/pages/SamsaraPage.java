package com.red.testframework.pages;

import com.red.testframework.testconfiguration.TestConfiguration;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.XPathUtil;
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
    @FindBy(xpath = "//span[contains(@class,'log-out')]")
    private WebElement logoutButton;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    private static Logger log = LoggerFactory.getLogger(SamsaraPage.class);

    public SamsaraPage(WebDriver driver) {
        super(driver);
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public boolean verifySamsaraPageIsDisplayed() {
        return verifyPageIsDisplayed(driver.findElement(By.xpath(XPathUtil.PANEL_TITLE_XPATH)), XPathUtil.SAMSARA_PAGE_PANEL_TITLE);
    }

    // Since this is landing page, where not really much of stuff related to app functionality is placed,
    // placing pages'navigation in this class
    public HomePage navigateToHomePage() {
        clickOnElement(homePageNavigator);
        Assert.assertTrue(new HomePage(driver).verifyHomePageIsDisplayed(), "Home page is not displayed!");
        Log.info("Home page is displayed");
        return new HomePage(driver);
    }

    public UsersPage navigateToUsersPage() {
        clickOnElement(usersPageNavigator);
        Assert.assertTrue(new UsersPage(driver).verifyUsersPageIsDisplayed(), "Users page is not displayed!");
        Log.info("Users page is displayed");
        return new UsersPage(driver);
    }

    public HeroesPage navigateToHeroesPage() {
        clickOnElement(heroesPageNavigator);
        Assert.assertTrue(new HeroesPage(driver).verifyHeroesPageIsDisplayed(), "Heroes page is not displayed!");
        Log.info("Heroes page is displayed");
        return new HeroesPage(driver);
    }


    public GalleryPage navigateToGalleryPage() {
            clickOnElement(galleryPageNavigator);
            Assert.assertTrue(new GalleryPage(driver).verifyGalleryPageIsDisplayed(), "Gallery page is not displayed!");
            Log.info("Gallery page is displayed");
            return new GalleryPage(driver);
        }

    public ApiPage navigateToApiPage() {
        clickOnElement(apiPageNavigator);
        Assert.assertTrue(new ApiPage(driver).verifyApiPageIsDisplayed(), "Api page is not displayed!");
        Log.info("Api page is displayed");
        return new ApiPage(driver);
    }

    public BrokenLinkPage navigateToBrokenLinkPage() {
        clickOnElement(brokenPageNavigator);
        Assert.assertTrue(new BrokenLinkPage(driver).verifyBrokenLinkPageIsDisplayed(), "Broken Link page is not displayed!");
        Log.info("Broken Link page is displayed");
        return new BrokenLinkPage(driver);
    }

    public AdminPage navigateToAdminPage() {
        clickOnElement(adminPageNavigator);
        Assert.assertTrue(new AdminPage(driver).verifyAdminPageIsDisplayed(), "Users page is not displayed!");
        Log.info("Users page is displayed");
        return new AdminPage(driver);
    }
}