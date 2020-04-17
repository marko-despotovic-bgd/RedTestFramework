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

import java.util.List;

public class HeroesPage extends BasePage {

    public final String PANEL_TITLE ="Heroes";

    // Page locators
    @FindBy(xpath = "//span[contains(@class,'-plus')]")
    //in case some additional -plus is added -> //a[@href="#"][contains(@class,"btn")]//span[contains(@class,'-plus')]
    private WebElement addNewHeroButton;
    @FindBy(id = "search")
    private WebElement searchInput;
    @FindBy(xpath = "//a[contains(text(),'→')]")
    private WebElement nextPageSearchArrow;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    //Popup locators
    @FindBy(id = "name")
    private WebElement nameInput;
    @FindBy(name = "level")
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
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }


    private void clickOnNewHeroButton() {
        clickOnElement(addNewHeroButton);
    }

    public void addHero(String heroName, String level, String heroClass) {

        Log.debug("Creating hero:\nName: " + heroName + "\nLevel: " + levelInput + "\nClass: " + heroClass);
        clickOnNewHeroButton();
        fillInInputField(nameInput, heroName);
        fillInInputField(levelInput, level);
        selectHeroClass(heroClass);
        clickOnElement(addHeroSaveButton);
        Log.debug("Created hero:\nName: " + heroName + "\nLevel: " + levelInput + "\nClass: " + heroClass);
    }

    private void selectHeroClass(String heroClass) {
        clickOnElement(classDropdownMenu);
        clickOnElement(driver.findElement(By.xpath("//option[@value='" + heroClass + "']")));
    }

    public HeroesPage editHero(String heroName, String level, String heroClass) {

        Log.debug("Editing hero:\nName: " + heroName);
        if (isHeroDisplayed(heroName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + heroName + "']/following-sibling::td//span[contains(@class,'pencil')]")));
            fillInInputField(levelInput, level);
            selectHeroClass(heroClass);
            clickOnElement(editHeroSaveButton);
            Log.debug("Edited hero:\nName: " + heroName + "\nLevel: " + levelInput + "\nClass: " + heroClass);
        } else
            Log.info("Hero not found!");
        return new HeroesPage(driver);

    }

    public HeroesPage deleteHero(String heroName) {
        Log.debug("Deleting hero " + heroName + "...");
        //fillInInputFieldAndPressEnter(searchInput, heroName); Commented out due to issue with search
        if (isHeroDisplayed(heroName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + heroName + "']/following-sibling::td//span[contains(@class,'trash')]")));
            clickOnElement(deleteButton);
            Log.debug("Hero " + heroName + " has been deleted!");
        } else
            Log.info("Hero not found!");
        return new HeroesPage(driver);
    }

    public boolean isHeroDisplayed(String heroName) {
        Log.debug("Checking if hero " + heroName + " is displayed...");
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
        List<WebElement> elements = driver.findElements(By.xpath("//a[contains(text(),'→')]"));
        List<WebElement> elements1 = driver.findElements(By.xpath("//li[@class='disabled']//a[contains(text(),'→')]"));

        boolean isNextPageButtonClickable = false;
        if (elements.size() != 0 && elements1.size()==0) {
                isNextPageButtonClickable = true;
        } else if (elements1.size() != 0)
            return false;
        return isNextPageButtonClickable;
    }


    public boolean verifyHeroesPageDisplayed() {
        return getElementText(panelTitle).equals(PANEL_TITLE);
    }
}
