package com.red.testframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.red.testframework.utils.Constants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UsersPage extends BasePage {

    // Page locators
    @FindBy(xpath = "//span[contains(@class,'-plus')]")
    //in case some additional -plus is added -> //a[@href="#"][contains(@class,"btn")]//span[contains(@class,'-plus')]
    private WebElement addNewUserButton;
    @FindBy(xpath = "//li[8]//a[1]")
    // Unpopular strategy, but due to font formatting, using this relative path as a locator
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


    public UsersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public boolean isUsersPageTitleDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.USERS_PAGE_PANEL_TITLE);
        log.info("User page title is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.USERS_PAGE_PANEL_TITLE);
    }

    private boolean isNewUserButtonDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = isDisplayed("//span[contains(@class,'-plus')]");
        log.info("New hero button is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/isDisplayed("//span[contains(@class,'-plus')]");
    }

    private void clickOnNewUserButton() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        assert isNewUserButtonDisplayed() : "New User button is not displayed!";
        clickOnElement(addNewUserButton);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    public UsersPage addNewUser(String username, String firstName, String lastName, String about,
                                String secretQuestion, String secretAnswer, String password, String confirmPassword) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Creating User:\nUsername: " + username + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nAbout: " + about
                + "\nSecret Question: " + secretQuestion + "\nSecret Answer: " + secretAnswer + "\nPassword: " + password + "\nConfirm Password: " + confirmPassword);
        // Printing out, so it can be easily detected if some field rules/restrictions change
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
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new UsersPage(driver);
    }

    public UsersPage editUser(String username, String firstName, String lastName, String about) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Editing user:\nName: " + username);
        if (isUserDisplayed(username)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + username + "']/following-sibling::td//span[contains(@class,'pencil')]")));
            fillInInputField(firstNameInput, firstName);
            fillInInputField(lastNameInput, lastName);
            fillInInputField(aboutInput, about);
            clickOnElement(deleteButton); // The same path as delete button, so using this locator
            log.debug("Edited user " + username + ":\nNew first name: " + firstName + "\nNew last name: " + lastName + "\nNew about: " + about);
        } else
            log.debug("User not found!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return new UsersPage(driver);
    }

    public boolean isUserDisplayed(String username) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        log.info("Checking if user " + username + " is displayed...");
        boolean isUserDisplayed = false;
        List<WebElement> usersList = driver.findElements(By.xpath("//td[@title='" + username + "']"));

        do {
            if (!usersList.isEmpty()) {
                isUserDisplayed = true;
                break;
            } else {
                nextPageSearchArrow.click();
                usersList = driver.findElements(By.xpath("//td[@title='" + username + "']"));
            }
        } while (isNextPageButtonClickable());
        log.info("User is displayed: " + isUserDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return isUserDisplayed;
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

    public void deleteUser(String userName) {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        //fillInInputFieldAndPressEnter(searchInput, userName); //Commented out due to bug on the app
        if (isUserDisplayed(userName)) {
            clickOnElement(driver.findElement(By.xpath("//td[@title='" + userName + "']/following-sibling::td//span[contains(@class,'trash')]")));
            clickOnElement(deleteButton);
            log.info("User " + userName + " has been deleted!");
        } else
            log.info("User not found!");
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }
}