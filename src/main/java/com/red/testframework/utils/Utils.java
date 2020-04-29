package com.red.testframework.utils;

import java.util.*;
import java.io.*;
import com.red.testframework.enums.BrowserType;
import com.red.testframework.pages.LoginPage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Utils {

    private static Properties properties = new Properties();
    private String screenshotFileLocation;
    private String saveScreenshots;

    /**
     * @param browser is read from testng.xml
     * @return LoginPage
     */
    public static LoginPage setUpWebBrowser(@org.jetbrains.annotations.NotNull String browser) {
        LoginPage loginPage;
        if (browser.equalsIgnoreCase(BrowserType.CHROME.toString())) {
            WebDriverManager.chromedriver().setup();
            loginPage = new LoginPage(new ChromeDriver());
        } else if (browser.equalsIgnoreCase(BrowserType.FIREFOX.toString())) {
            WebDriverManager.firefoxdriver().setup();
            loginPage = new LoginPage(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase(BrowserType.EDGE.toString())) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.setCapability("InPrivate", true);
            loginPage = new LoginPage(new EdgeDriver(options));
        } // Extremely slow execution in 64-bit mode, so calling 32-bit driver instead
        else if (browser.equalsIgnoreCase(BrowserType.IE.toString())) {
            WebDriverManager.iedriver().arch32().setup();
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.ignoreZoomSettings();
            options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
            loginPage = new LoginPage(new InternetExplorerDriver(options));
        } else if (browser.equalsIgnoreCase(BrowserType.CHROME_HEADLESS.toString())) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            loginPage = new LoginPage(new ChromeDriver(chromeOptions));
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


    private void loadProperties() {
        String defaultConfigPropertiesFile = "resources/config/config.properties";
        File configProperties = new File(defaultConfigPropertiesFile);
        if (!configProperties.exists() || !configProperties.isFile()) {
            Log.error("config-file does not exist or is not a file!");
            System.exit(-1);
        }
        // load properties
        try {
            properties.load(new FileInputStream(defaultConfigPropertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty (String key){
        loadProperties();
        return properties.getProperty(key);
    }

    public String getScreenshotFileLocation() {
        return this.screenshotFileLocation;
    }

    public boolean getSaveScreenshots() {
        if (StringUtils.isBlank(this.saveScreenshots)) {
            return false;
        } else return this.saveScreenshots.equalsIgnoreCase("true");
    }


}