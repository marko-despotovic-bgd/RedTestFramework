package com.red.testframework.pages;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HeroesPage extends BasePage {

    // Page locators
    @FindBy(xpath = "//span[contains(@class,'-plus')]")
    //in case some additional -plus is added -> //a[@href="#"][contains(@class,"btn")]//span[contains(@class,'-plus')]
    private WebElement addNewHeroButton;
    @FindBy(id = "search")
    private WebElement searchInput;
    @FindBy(xpath = "//li[8]//a[1]") // Unpopular strategy, but due to font formatting, using this relative path as a locator
    private WebElement nextPageSearchArrow;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    //Popup locators
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

    private static Logger log = LoggerFactory.getLogger(HeroesPage.class);

    public HeroesPage(WebDriver driver) {
        super(driver);
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }


    private void clickOnNewHeroButton() {
        clickOnElement(addNewHeroButton);
    }

    public void addHero(String heroName, String level, String heroClass) {

        Log.info("Creating hero:\nName: " + heroName + "\nLevel: " + level + "\nClass: " + heroClass);
        clickOnNewHeroButton();
        fillInInputField(nameInput, heroName);
        fillInInputField(levelInput, level);
        selectHeroClass(heroClass);
        clickOnElement(addHeroSaveButton);
        Log.info("Created hero:\nName: " + heroName + "\nLevel: " + level + "\nClass: " + heroClass);
    }

    private void selectHeroClass(String heroClass) {
        clickOnElement(classDropdownMenu);
        clickOnElement(driver.findElement(By.xpath("//option[@value='" + heroClass + "']")));
    }

    public HeroesPage editHero(String heroName, String level, String heroClass) {

        Log.info("Editing hero:\nName: " + heroName);
        if (isHeroDisplayed(heroName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + heroName + "']/following-sibling::td//span[contains(@class,'pencil')]")));
            fillInInputField(levelInput, level);
            selectHeroClass(heroClass);
            clickOnElement(editHeroSaveButton);
            Log.info("Edited hero:\nName: " + heroName + "\nLevel: " + level + "\nClass: " + heroClass);
        } else
            Log.info("Hero not found!");
        return new HeroesPage(driver);

    }

    public HeroesPage deleteHero(String heroName) {
        Log.info("Deleting hero " + heroName + "...");
        //fillInInputFieldAndPressEnter(searchInput, heroName); Commented out due to issue with search
        if (isHeroDisplayed(heroName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + heroName + "']/following-sibling::td//span[contains(@class,'trash')]")));
            clickOnElement(deleteButton);
            Log.info("Hero " + heroName + " has been deleted!");
        } else
            Log.info("Hero not found!");
        return new HeroesPage(driver);
    }

    public boolean isHeroDisplayed(String heroName) {
        Log.info("Checking if hero " + heroName + " is displayed...");
        boolean isHeroDisplayed = false;
        List<WebElement> heroesList = driver.findElements(By.xpath("//td[@title='" + heroName + "']"));

        do {

            if (!heroesList.isEmpty()){
                isHeroDisplayed = true;
                break;
            } else {
                nextPageSearchArrow.click();
            }
            heroesList = driver.findElements(By.xpath("//td[@title='" + heroName + "']"));

        } while (verifyNextPageButtonIsClickable());
        return isHeroDisplayed;
    }

    private boolean verifyNextPageButtonIsClickable() {
        String visibleArrowCssSelector = "div.container:nth-child(2) div.mainbox.col-md-8.col-md-offset-2.col-sm-8.col-sm-offset-2 div.panel.panel-default div.panel-body:nth-child(2) div.row.text-right:nth-child(3) div.form-group.col-sm-10.pagination-center:nth-child(2) ul.pagination li:nth-child(8) > a.pageLink";
        String notVisibleArrowCssSelector = "div.container:nth-child(2) div.mainbox.col-md-8.col-md-offset-2.col-sm-8.col-sm-offset-2 div.panel.panel-default div.panel-body:nth-child(2) div.row.text-right:nth-child(3) div.form-group.col-sm-10.pagination-center:nth-child(2) ul.pagination li.disabled:nth-child(8) > a.pageLink";
        List<WebElement> elements = driver.findElements(By.cssSelector(visibleArrowCssSelector));
        List<WebElement> elements1 = driver.findElements(By.cssSelector(notVisibleArrowCssSelector));
        boolean isNextPageButtonClickable = false;
        if (elements.size() != 0 && elements1.size()==0) {
                isNextPageButtonClickable = true;
        } else if (elements1.size() != 0)
            return false;
        return isNextPageButtonClickable;
    }

    public boolean verifyHeroesPageIsDisplayed() {
        return verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.HEROES_PAGE_PANEL_TITLE);
    }
}
