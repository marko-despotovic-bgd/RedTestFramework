package com.red.testframework.pages;

import com.red.testframework.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BrokenLinkPage extends BasePage {

    // Constructor
    public BrokenLinkPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isBrokenLinkPageTitleDisplayed() {
        log.info("Executing..... " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean isDisplayed = verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.BROKEN_LINK_PAGE_PANEL_TITLE);
        log.info("Broken link page title is displayed: " + isDisplayed);
        log.info("Successfully executed " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        return /*! <- Testing purpose*/verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.BROKEN_LINK_PAGE_PANEL_TITLE);
    }
}