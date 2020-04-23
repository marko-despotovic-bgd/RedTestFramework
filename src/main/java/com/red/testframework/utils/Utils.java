package com.red.testframework.utils;

import com.red.testframework.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class);

    /**
     * @param browser
     * @return LoginPage
     */
    public static LoginPage setUpWebBrowser(String browser) {
        LoginPage loginPage;
        log.info("Chosen browser is " + browser);
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            loginPage = new LoginPage(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            loginPage = new LoginPage(new FirefoxDriver());
        } else
            throw new RuntimeException();
        return loginPage;
    }


    /**
     * @param driver
     * @param locator
     */
    public static void webDriverWait(WebDriver driver, By locator) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}