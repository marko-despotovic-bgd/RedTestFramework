package com.red.testframework.pages;

import com.red.testframework.utils.Log;
import com.red.testframework.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiPage extends BasePage {

    private static Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Constructor
    public ApiPage(WebDriver driver) {
        super(driver);
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public boolean verifyApiPageIsDisplayed() {
        return verifyPageIsDisplayed(By.xpath(Constants.PANEL_TITLE_XPATH), Constants.API_PAGE_PANEL_TITLE);
    }
}