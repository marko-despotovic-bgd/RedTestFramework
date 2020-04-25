package com.red.testframework.pages;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.TestConfiguration;
import com.red.testframework.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UsersPage extends BasePage {

    private static Logger log = LoggerFactory.getLogger(UsersPage.class);

    public UsersPage(WebDriver driver) {
        super(driver);
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    // Page locators
    @FindBy(xpath = "//span[contains(@class,'-plus')]")
    //in case some additional -plus is added -> //a[@href="#"][contains(@class,"btn")]//span[contains(@class,'-plus')]
    private WebElement addNewUserButton;
    @FindBy(id = "search")
    private WebElement searchInput;
    @FindBy(xpath = "//a[contains(text(),'→')]")
    private WebElement nextPageSearchArrow;
    @FindBy(xpath = "//div[@class='panel-title text-center']")
    private WebElement panelTitle;

    //Popup locators
    @FindBy(id = "username")
    private WebElement usernameInput;
    @FindBy(id = "firstName")
    private WebElement firstNameInput;
    @FindBy(id = "lastName")
    private WebElement lastNameInput;
    @FindBy(id = "about")
    private WebElement aboutInput;
    @FindBy(id = "secretQuestion")
    private WebElement secretQuestionInput;
    @FindBy(id = "secretAnswer")
    private WebElement secretAnswerInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(id = "repassword")
    private WebElement confirmPasswordInput;
    @FindBy(id = "submitButton")
    private WebElement saveButton;
    @FindBy(xpath = "//div[@class='modal-footer']//button[@type='submit']")
    private WebElement deleteButton;
    @FindBy(xpath = "//div[@class='modal-footer']//button[@type='button']")
    private WebElement closeButton;


    private UsersPage clickOnNewUserButton() {
        clickOnElement(addNewUserButton);
        return new UsersPage(driver);
    }

    public UsersPage addUser(String username, String firstName, String lastName, String about,
                             String secretQuestion, String secretAnswer, String password, String confirmPassword) {

        Log.debug("Creating User:\nUsername: " + username + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nAbout: " + about
                + "\nSecret Question: " + secretQuestion + "\nSecret Answer: " + secretAnswer + "\nPassword: " + password + "\nConfirm Password: " + confirmPassword);
        clickOnNewUserButton();
        fillInInputField(usernameInput, username);
        fillInInputField(firstNameInput, firstName);
        fillInInputField(lastNameInput, lastName);
        fillInInputField(aboutInput, about);
        fillInInputField(secretQuestionInput, secretQuestion);
        fillInInputField(secretAnswerInput, secretAnswer);
        fillInInputField(passwordInput, password);
        fillInInputField(confirmPasswordInput, confirmPassword);
        clickOnElement(saveButton);

        return new UsersPage(driver);
    }

    public UsersPage editUser(String username, String firstName, String lastName, String about) {

        Log.debug("Editing user:\nName: " + username);
        if (isUserDisplayed(username)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + username + "']/following-sibling::td//span[contains(@class,'pencil')]")));
            fillInInputField(firstNameInput, firstName);
            fillInInputField(lastNameInput, lastName);
            fillInInputField(aboutInput, about);
            clickOnElement(deleteButton); // The same path as delete button, so using this locator
            Log.debug("Edited user " + username + ":\nNew first name: " + firstName + "\nNew last name: " + lastName + "\nNew about: " + about);
        } else
            Log.debug("User not found!");
        return new UsersPage(driver);
    }

    public boolean isUserDisplayed(String username) {
        Log.debug("Checking if user " + username + " is displayed...");
        boolean isUserDisplayed = false;

        List<WebElement> usersList = driver.findElements(By.xpath("//td[@title='" + username + "']"));
        do {
            if (usersList.size() > 0) {
                isUserDisplayed = true;
                break;
            } else
                nextPageSearchArrow.click();
            usersList = driver.findElements(By.xpath("//td[@title='" + username + "']"));
        } while (verifyNextPageButtonIsClickable());
        return isUserDisplayed;
    }

    private boolean verifyNextPageButtonIsClickable() {
        List<WebElement> elements = driver.findElements(By.xpath("//a[contains(text(),'→')]"));
        List<WebElement> elements1 = driver.findElements(By.xpath("//li[@class='disabled']//a[contains(text(),'→')]")); // elements.isEnabled(); does not work here,
        boolean isNextPageButtonClickable = false;
        if (elements.size() != 0 && elements1.size() == 0) {
            isNextPageButtonClickable = true;
        } else if (elements1.size() != 0)
            return false;
        return isNextPageButtonClickable;
    }

    public void deleteUser(String userName) {
        //fillInInputFieldAndPressEnter(searchInput, userName); //Commented out due to bug on the app
        if (isUserDisplayed(userName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + userName + "']/following-sibling::td//span[contains(@class,'trash')]")));
            clickOnElement(deleteButton);
            Log.debug("User " + userName + " has been deleted!");
        } else
            Log.debug("User not found!");
    }

    public boolean verifyUsersPageIsDisplayed() {
        return verifyPageIsDisplayed(driver.findElement(By.xpath(Constants.PANEL_XPATH)), Constants.USERS_PAGE_PANEL_TITLE);
    }

    public void wait(int timeout) {
        new BasePage(driver).wait(2);
    }
}