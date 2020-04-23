package com.red.testframework.pages;

import com.red.testframework.testconfiguration.TestConfiguration;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.PageConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GalleryPage extends BasePage {

    private static Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Constructor
    public GalleryPage(WebDriver driver) {
        super(driver);
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public boolean verifyGalleryPageIsDisplayed() {
        return verifyPageIsDisplayed(driver.findElement(By.xpath(PageConstants.PANEL_TITLE_XPATH)), PageConstants.GALLERY_PAGE_PANEL_TITLE);
    }
}
