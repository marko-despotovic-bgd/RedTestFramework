package com.red.testframework.pages;

import com.red.testframework.utils.PageConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyHomePageIsDisplayed() {
        return verifyPageIsDisplayed(driver.findElement(By.xpath(PageConstants.PANEL_XPATH)), PageConstants.HOME_PAGE_PANEL_TITLE);
    }
}