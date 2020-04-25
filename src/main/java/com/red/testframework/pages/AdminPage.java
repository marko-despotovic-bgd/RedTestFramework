package com.red.testframework.pages;

import com.red.testframework.utils.TestConfiguration;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminPage extends BasePage {

    private static Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Constructor
    public AdminPage(WebDriver driver) {
        super(driver);
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public boolean verifyAdminPageIsDisplayed() {
        return verifyPageIsDisplayed(driver.findElement(By.xpath(Constants.PANEL_TITLE_XPATH)), Constants.ADMIN_PAGE_PANEL_TITLE);
    }

}