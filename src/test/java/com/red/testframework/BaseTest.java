package com.red.testframework;

import com.red.testframework.db.DBConnection;
import com.red.testframework.db.DBQueries;
import com.red.testframework.enums.BrowserType;
import com.red.testframework.pages.LoginPage;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.OSUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;


public class BaseTest {

    private static Properties properties = new Properties();
    private final String defaultConfigPropertiesFile = "/resources/config/config.properties";

    protected static WebDriver driver;
    //   protected static DBConnection dbConn;
    protected static DBConnection dbConn;
    protected static String baseURL;
    protected static String adminUsername, adminPassword;
    private LoginPage loginPage;


    private void loadProperties(String path) throws FileNotFoundException, IOException {
        File configProperties = new File(defaultConfigPropertiesFile);
        if(!configProperties.exists() || !configProperties.isFile()) {
            Log.error("config-file does not exist or is not a file!");
            System.exit(-1);
        }
        // load properties
        properties.load(new FileInputStream(defaultConfigPropertiesFile));
    }

    /**
     * @param browser
     * @return LoginPage
     */
    public static LoginPage setUpWebBrowser(@org.jetbrains.annotations.NotNull String browser) {
        LoginPage loginPage;
        Log.debug("Initializing " + browser);
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
            options.setCapability(InternetExplorerDriver.NATIVE_EVENTS,false);
            loginPage = new LoginPage(new InternetExplorerDriver(options));
        } else if (browser.equalsIgnoreCase(BrowserType.CHROME_HEADLESS.toString())) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            loginPage = new LoginPage(new ChromeDriver(chromeOptions));
        }else
            throw new RuntimeException();

        return loginPage;
    }

    protected void closeWebDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

//    /*
//     * TestNG test prepare and tear-down
//     */
//    @BeforeMethod(alwaysRun = true)
//    @Parameters({"browser"})
//    public void beforeBaseSuite(@Optional("firefox") String browser)  throws IOException, SQLException, ClassNotFoundException {
//        loginPage = setUpWebBrowser(browser);
//
//        loadProperties(defaultConfigPropertiesFile);
//        baseURL = properties.getProperty("app.url");
//        adminUsername = properties.getProperty("admin.username");
//        adminPassword = properties.getProperty("password");

//        // create db connection
//        String pwd = (properties.getProperty("database.password") != null ? properties.getProperty("database.password") : "");
//
//        dbConn = new DBConnection("jdbc:mysql://"
//                + properties.getProperty("database.ip")
//                + ":" + properties.getProperty("database.port")
//                + "/" + properties.getProperty("database.name"),
//                properties.getProperty("database.user"), pwd);
//        // Verify connection is working
//        dbConn.executeQuery("SELECT 1");
//        DBQueries.setDBConnection(dbConn);
//
//        driver.manage().window().maximize();
//        driver.get(baseURL);
//
//    }

    @AfterSuite(alwaysRun = true)
    public void afterBaseSuite() throws SQLException {
        this.closeWebDriver();
        //this.dbConn.close();
    }

    @AfterMethod(alwaysRun = true)
    public void baseAfterMethod() {
        driver.get(baseURL);
    }


    private String getDriverBinaryLocation(String browser) {
        String currentDir = System.getProperty("user.dir");
        String driverPath = Paths.get(currentDir, "resources/driver_binaries").toString();
        String fileExtension = "";

        switch (OSUtils.getOsType()) {
            case MAC: {
                driverPath = Paths.get(driverPath, "/osx").toString();
                fileExtension = ".exe";
                break;
            }
            case WINDOWS: {
                driverPath = Paths.get(driverPath, "/windows").toString();
                break;
            }
            case LINUX: {
                driverPath = Paths.get(driverPath, "/linux").toString();
                break;
            }
        }

        switch (browser.toLowerCase()) {
            case "firefox": {

            }
            default: { // chrome is same as default
                String filename = "chromedriver";
                driverPath = Paths.get(
                        driverPath,
                        "googlechrome",
                        "64bit",
                        filename + fileExtension).toString();
            }
        }
        return driverPath;
    }

    private String getChromeBinaryLocation() {
        return getDriverBinaryLocation("chrome");
    }

//    // This should be called before each test method
//    protected void prepareBaseState(String username, String password) {
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.logIn(username, password);
//        Assert.assertTrue(loginPage.isLoggedIn(3), username + " is logged in");
//        driver.get(baseURL);
//    }
}
