package com.red.testframework.utils;

import java.util.*;
import java.io.*;

import com.red.testframework.enums.BrowserType;
import com.red.testframework.pages.LoginPage;
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

    private static final Properties properties = new Properties();

    /**
     * @param browser type is read from testng.xml
     * @return LoginPage(driver)
     */
    public static LoginPage setUpWebBrowser(@org.jetbrains.annotations.NotNull String browser) {
        LoginPage loginPage;
        if (browser.equalsIgnoreCase(BrowserType.CHROME.toString())) {
            WebDriverManager.chromedriver().setup();
            loginPage = new LoginPage(new ChromeDriver());
            Log.info("Initialized " + BrowserType.CHROME.toString().toLowerCase() + " session");
        } else if (browser.equalsIgnoreCase(BrowserType.FIREFOX.toString())) {
            WebDriverManager.firefoxdriver().setup();
            loginPage = new LoginPage(new FirefoxDriver());
            Log.info("Initialized " + BrowserType.FIREFOX.toString().toLowerCase() + " session");
        } else if (browser.equalsIgnoreCase(BrowserType.EDGE.toString())) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.setCapability("InPrivate", true);
            loginPage = new LoginPage(new EdgeDriver(options));
            Log.info("Initialized " + BrowserType.EDGE.toString().toLowerCase() + " session");
        } // Extremely slow execution in 64-bit mode, so calling 32-bit driver instead
        else if (browser.equalsIgnoreCase(BrowserType.IE.toString())) {
            WebDriverManager.iedriver().arch32().setup();
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.ignoreZoomSettings();
            options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
            loginPage = new LoginPage(new InternetExplorerDriver(options));
            Log.info("Initialized " + BrowserType.IE.toString().toLowerCase() + " session");
        } else if (browser.equalsIgnoreCase(BrowserType.CHROME_HEADLESS.toString())) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            loginPage = new LoginPage(new ChromeDriver(chromeOptions));
            Log.info("Initialized " + BrowserType.CHROME_HEADLESS.toString().toLowerCase() + " session");
        }
//        else if (browser.equalsIgnoreCase(BrowserType.SAFARI.toString())) {
//            WebDriverManager.safaridriver().setup();
//            loginPage = new LoginPage(new SafariDriver());
//            Log.info("Initialized " + BrowserType.SAFARI.toString().toLowerCase() + " session");
//        }
        else
            throw new RuntimeException();

        return loginPage;
    }

    public static void loadProperties() {
        String defaultConfigPropertiesFile = "resources/config/config.properties";
        File configProperties = new File(defaultConfigPropertiesFile);
        if (!configProperties.exists() || !configProperties.isFile()) {
            Log.error("Config file does not exist or is not a file!");
            System.exit(-1);
        }
        // load properties
        try {
            properties.load(new FileInputStream(defaultConfigPropertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        loadProperties();
        return properties.getProperty(key);
    }
}