package com.red.testframework.pages;

import com.red.testframework.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class HeroesPage extends BasePage {

    // Page locators
    @FindBy(xpath = "//span[contains(@class,'-plus')]")
    // In case some additional -plus is added -> //a[@href="#"][contains(@class,"btn")]//span[contains(@class,'-plus')]
    private WebElement addNewHeroButton;
    @FindBy(id = "search")
    private WebElement searchInput;
    @FindBy(xpath = "//li[8]//a[1]")
    // Unpopular strategy, but due to font formatting, using this relative path as a locator
    private WebElement nextPageSearchArrow;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    // Popup locators
    @FindBy(id = "name")
    private WebElement nameInput;
    @FindBy(id = "level")
    private WebElement levelInput;
    @FindBy(id = "type")
    private WebElement classDropdownMenu;
    @FindBy(id = "submitButton")
    private WebElement addHeroSaveButton;
    @FindBy(xpath = "//i[contains(@class,'search')]")
    private WebElement searchTextField;
    @FindBy(xpath = "//div[@class='modal-footer']//button[@type='submit']")
    private WebElement editHeroSaveButton, deleteButton;
    @FindBy(xpath = "//div[@class='modal-header bg-primary']")
    private WebElement modalHeader;


    public HeroesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isHeroesPageTitleDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.HEROES_PAGE_PANEL_TITLE);
        log.info("Heroes page title is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.HEROES_PAGE_PANEL_TITLE);
    }

    private boolean isNewHeroButtonDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = isDisplayed("//span[contains(@class,'-plus')]");
        log.info("New hero button is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/isDisplayed("//span[contains(@class,'-plus')]");
    }

    private void clickOnNewHeroButton() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        assert isNewHeroButtonDisplayed(): "New Hero button is not displayed!";
        clickOnElement(addNewHeroButton);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    public void addNewHero(String heroName, String level, String heroClass) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Creating hero:\nName: " + heroName + "\nLevel: " + level + "\nClass: " + heroClass);
        clickOnNewHeroButton();
        fillInInputField(nameInput, heroName);
        fillInInputField(levelInput, level);
        selectHeroClass(heroClass);
        clickOnElement(modalHeader); // Due firefox issues with querySelector()
        clickOnElement(addHeroSaveButton);
        log.info("Created hero:\nName: " + heroName + "\nLevel: " + level + "\nClass: " + heroClass);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    private void selectHeroClass(String heroClass) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        selectFromDropDownMenu(classDropdownMenu, heroClass);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    public HeroesPage editHero(String heroName, String level, String heroClass) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Editing hero:\nName: " + heroName);
        if (isHeroDisplayed(heroName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + heroName + "']/following-sibling::td//span[contains(@class,'pencil')]")));
            fillInInputField(levelInput, level);
            selectHeroClass(heroClass);
            clickOnElement(modalHeader); // Due firefox issues with querySelector()
            clickOnElement(editHeroSaveButton);
            log.info("Edited hero:\nName: " + heroName + "\nLevel: " + level + "\nClass: " + heroClass);
        } else
            log.info("Hero not found!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new HeroesPage(driver);

    }

    public HeroesPage deleteHero(String heroName) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Deleting hero " + heroName + "...");
        //fillInInputFieldAndPressEnter(searchInput, heroName); Commented out due to issue with search
        if (isHeroDisplayed(heroName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + heroName + "']/following-sibling::td//span[contains(@class,'trash')]")));
            clickOnElement(deleteButton);
            log.info("Hero " + heroName + " has been deleted!");
        } else
            log.info("Hero not found!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new HeroesPage(driver);
    }

    public boolean isHeroDisplayed(String heroName) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Checking if hero " + heroName + " is displayed...");
        boolean isHeroDisplayed = false;
        List<WebElement> heroesList = driver.findElements(By.xpath("//td[@title='" + heroName + "']"));

        do {

            if (!heroesList.isEmpty()) {
                isHeroDisplayed = true;
                break;
            } else {
                nextPageSearchArrow.click();
                heroesList = driver.findElements(By.xpath("//td[@title='" + heroName + "']"));
            }
        } while (isNextPageButtonClickable());
        log.info("Is hero displayed: " + isHeroDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return isHeroDisplayed;
    }

    private boolean isNextPageButtonClickable() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        String visibleArrowCssSelector = "div.container:nth-child(2) div.mainbox.col-md-8.col-md-offset-2.col-sm-8.col-sm-offset-2 div.panel.panel-default div.panel-body:nth-child(2) div.row.text-right:nth-child(3) div.form-group.col-sm-10.pagination-center:nth-child(2) ul.pagination li:nth-child(8) > a.pageLink";
        String notVisibleArrowCssSelector = "div.container:nth-child(2) div.mainbox.col-md-8.col-md-offset-2.col-sm-8.col-sm-offset-2 div.panel.panel-default div.panel-body:nth-child(2) div.row.text-right:nth-child(3) div.form-group.col-sm-10.pagination-center:nth-child(2) ul.pagination li.disabled:nth-child(8) > a.pageLink";
        List<WebElement> elements = driver.findElements(By.cssSelector(visibleArrowCssSelector));
        List<WebElement> elements1 = driver.findElements(By.cssSelector(notVisibleArrowCssSelector));
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("New page button is clickable: " + (elements.size() != 0  && elements1.size()==0));
        return elements.size() != 0  && elements1.size()==0;
    }

}
