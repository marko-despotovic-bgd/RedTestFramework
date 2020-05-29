package com.red.testframework.pages;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SamsaraPage extends BasePage {

    //Page locators
    @FindBy(css = ".navbar-brand") private WebElement samsaraBrandLink;
    @FindBy(css = ".nav a[href=\"/home\"]") private WebElement homePageNavigator;
    @FindBy(css = "a[href=\"/users\"]") private WebElement usersPageNavigator;
    @FindBy(css = "a[href=\"/heroes\"]") private WebElement heroesPageNavigator;
    @FindBy(css = "a[href=\"/gallery\"]") private WebElement galleryPageNavigator;
    @FindBy(css = "a[href=\"/api\"]") private WebElement apiPageNavigator;
    @FindBy(css = "a[href=\"/broken\"]") private WebElement brokenPageNavigator;
    @FindBy(css = "a[href=\"/admin\"]") private WebElement adminPageNavigator;
    @FindBy(css = "//div[@class='panel-title text-center']") private WebElement panelTitle;
    @FindBy(css = ".lead [href=\"/home\"]") private WebElement startTestingButton;
    @FindBy(css = ".lead [href=\"/\"]") private WebElement shareWithFriendsButton;

    public SamsaraPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Since this is landing page, where not really much of stuff related to app functionality is placed,
    // placing pages'navigation in this class

    // Navigation

    public HomePage navigateToHomePage() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(homePageNavigator) : "Home Page navigation link is not displayed";
        homePageNavigator.click();
        assert new HomePage(driver).isHomePageTitleDisplayed() : "Home page is not displayed!";
        log.info("Home page is displayed");
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return new HomePage(driver);
    }

    public UsersPage navigateToUsersPage() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(usersPageNavigator) : "Users Page navigation link is not displayed";
        usersPageNavigator.click();
        assert new UsersPage(driver).isUsersPageTitleDisplayed() : "Users page is not displayed!";
        log.info("Users page is displayed");
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return new UsersPage(driver);
    }

    public HeroesPage navigateToHeroesPage() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(heroesPageNavigator) : "Heroes Page navigation link is not displayed";
        heroesPageNavigator.click();
        assert new HeroesPage(driver).isHeroesPageTitleDisplayed() : "Heroes page title is not displayed!";
        log.info("Heroes page is displayed");
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return new HeroesPage(driver);
    }

    public GalleryPage navigateToGalleryPage() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(galleryPageNavigator) : "Gallery Page navigation link is not displayed";
        galleryPageNavigator.click();
        assert new GalleryPage(driver).isGalleryPageTitleDisplayed() : "Gallery page is not displayed!";
        log.info("Gallery page is displayed");
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");

        return new GalleryPage(driver);
    }

    public ApiPage navigateToApiPage() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(apiPageNavigator) : "Api Page navigation link is not displayed";
        apiPageNavigator.click();
        assert new ApiPage(driver).isApiPageTitleDisplayed() : "Api page is not displayed!";
        log.info("Api page is displayed");
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return new ApiPage(driver);
    }

    public BrokenLinkPage navigateToBrokenLinkPage() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(brokenPageNavigator) : "Broken Page navigation link is not displayed";
        brokenPageNavigator.click();
        assert new BrokenLinkPage(driver).isBrokenLinkPageTitleDisplayed() : "Broken Link page is not displayed!";
        log.info("Broken Link page is displayed");
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return new BrokenLinkPage(driver);
    }

    public AdminPage navigateToAdminPage() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(adminPageNavigator) : "Admin Page navigation link is not displayed";
        adminPageNavigator.click();
        assert new AdminPage(driver).isAdminPageTitleDisplayed() : "Admin page is not displayed!";
        log.info("Users page is displayed");
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return new AdminPage(driver);
    }

    // Checks

    public boolean isSamsaraPageTitleDisplayed() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        boolean isDisplayed = verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE);
        log.info("Samsara page title is displayed: " + isDisplayed);
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return /*! <- Testing purpose*/isDisplayed;
    }

    public boolean isStartTestingButtonDisplayed() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        boolean isDisplayed = isDisplayed(startTestingButton);
        log.info("Start Testing button is displayed: " + isDisplayed);
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return isDisplayed;
    }

    public boolean isShareWithFriendsButtonDisplayed() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        boolean isDisplayed = isDisplayed(shareWithFriendsButton);
        log.info("Share with friends is displayed: " + isDisplayed);
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return isDisplayed;
    }

    private boolean isSamsaraBrandLinkDisplayed() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        boolean isDisplayed = isDisplayed(samsaraBrandLink);
        log.info("Samsara brand link is displayed: " + isDisplayed);
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
        return isDisplayed;
    }

    // Clicks

    public void clickSamsaraBrandLink() {
        log.info("Executing ==> " + new Object(){}.getClass().getEnclosingMethod().getName() + " <== method");
        assert isDisplayed(samsaraBrandLink) : "Broken Page navigation link is not displayed";
        samsaraBrandLink.click();
        assert verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.SAMSARA_PAGE_PANEL_TITLE) : "Samsara page is not displayed!";
        log.info("Successfully executed ==> " + new Object() {}.getClass().getEnclosingMethod().getName() + " <== method");
    }
}