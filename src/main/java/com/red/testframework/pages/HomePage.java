package com.red.testframework.pages;

import com.red.testframework.utils.XPathUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyHomePageIsDisplayed() {
        return verifyPageIsDisplayed(driver.findElement(By.xpath(XPathUtil.PANEL_XPATH)), XPathUtil.HOME_PAGE_PANEL_TITLE);
    }
}