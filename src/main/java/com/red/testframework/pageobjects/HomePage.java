package com.red.testframework.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//span[contains(@class,'log-out')]")
    private WebElement logoutButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage logOut() {
        clickOnElement(logoutButton);
        return new LoginPage(driver);
    }
}