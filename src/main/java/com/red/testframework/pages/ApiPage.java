package com.red.testframework.pages;

import com.red.testframework.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ApiPage extends BasePage {

    // Constructor
    public ApiPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isApiPageTitleDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.API_PAGE_PANEL_TITLE);
        log.info("Api page title is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.API_PAGE_PANEL_TITLE);
    }
}