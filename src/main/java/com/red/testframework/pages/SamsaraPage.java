package com.red.testframework.pages;

import com.red.testframework.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
    @FindBy(xpath = "//a[@class='navbar-brand']")
    private WebElement samsaraBrandLink;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-lg'][text()='Start Testing!']")
    private WebElement startTestingButton;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-lg'][text()='Share with friends!']")
    private WebElement shareWithFriendsButton;

    public SamsaraPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Since this is landing page, where not really much of stuff related to app functionality is placed,
    // placing pages'navigation in this class

    public boolean isSamsaraPageTitleDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE);
        log.info("Samsara page title is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE);
    }

    public HomePage navigateToHomePage() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(homePageNavigator), "Home Page navigation link is not displayed");
        clickOnElement(homePageNavigator);
        Assert.assertTrue(new HomePage(driver).isHomePageTitleDisplayed(), "Home page is not displayed!");
        log.info("Home page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new HomePage(driver);
    }

    public UsersPage navigateToUsersPage() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(usersPageNavigator), "Users Page navigation link is not displayed");
        clickOnElement(usersPageNavigator);
        Assert.assertTrue(new UsersPage(driver).isUsersPageTitleDisplayed(), "Users page is not displayed!");
        log.info("Users page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new UsersPage(driver);
    }

    public HeroesPage navigateToHeroesPage() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(heroesPageNavigator), "Heroes Page navigation link is not displayed");
        clickOnElement(heroesPageNavigator);
        Assert.assertTrue(new HeroesPage(driver).isHeroesPageTitleDisplayed(), "Heroes page title is not displayed!");
        log.info("Heroes page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new HeroesPage(driver);
    }


    public GalleryPage navigateToGalleryPage() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(galleryPageNavigator), "Gallery Page navigation link is not displayed");
        clickOnElement(galleryPageNavigator);
        Assert.assertTrue(new GalleryPage(driver).isGalleryPageTitleDisplayed(), "Gallery page is not displayed!");
        log.info("Gallery page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new GalleryPage(driver);
    }

    public ApiPage navigateToApiPage() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(apiPageNavigator), "Api Page navigation link is not displayed");
        clickOnElement(apiPageNavigator);
        Assert.assertTrue(new ApiPage(driver).isApiPageTitleDisplayed(), "Api page is not displayed!");
        log.info("Api page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new ApiPage(driver);
    }

    public BrokenLinkPage navigateToBrokenLinkPage() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(brokenPageNavigator), "Broken Page navigation link is not displayed");
        clickOnElement(brokenPageNavigator);
        Assert.assertTrue(new BrokenLinkPage(driver).isBrokenLinkPageTitleDisplayed(), "Broken Link page is not displayed!");
        log.info("Broken Link page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new BrokenLinkPage(driver);
    }

    public AdminPage navigateToAdminPage() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed(adminPageNavigator), "Admin Page navigation link is not displayed");
        clickOnElement(adminPageNavigator);
        Assert.assertTrue(new AdminPage(driver).isAdminPageTitleDisplayed(), "Admin page is not displayed!");
        log.info("Users page is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new AdminPage(driver);
    }

    public boolean isStartTestingButtonDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = isDisplayed("//a[@class='btn btn-primary btn-lg'][text()='Start Testing!']");
        log.info("Start Testing button is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/isDisplayed("//a[@class='btn btn-primary btn-lg'][text()='Start Testing!']");
    }

    public boolean isShareWithFriendsButtonDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed("//a[@class='btn btn-primary btn-lg'][text()='Share with friends!']"), "Log In button is not displayed!");
        log.info("Share with friends is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/isDisplayed("//a[@class='btn btn-primary btn-lg'][text()='Share with friends!']");
    }

    private boolean isSamsaraBrandLinkDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        Assert.assertTrue(isDisplayed("//a[@class='navbar-brand']"), "Brand link is not displayed!");
        log.info("Samsara brand link is displayed");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/isDisplayed("//a[@class='navbar-brand']");
    }

    public void clickSamsaraBrandLink() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        assert isSamsaraBrandLinkDisplayed();
        clickOnElement(samsaraBrandLink);
        Assert.assertTrue(verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE), "Samsara page is not displayed!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }
}