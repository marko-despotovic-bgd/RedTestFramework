package com.red.testframework;

import com.google.common.base.Strings;
import com.red.testframework.db.DBConnection;
import com.red.testframework.db.DBQueries;
import com.red.testframework.pages.LoginPage;
import com.red.testframework.utils.OSUtils;
import org.slf4j.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);

    private static Properties properties = new Properties();

    protected static WebDriver driver;
    //   protected static DBConnection dbConn;
    private boolean visibleBrowser;
    private String browserToUse;
    protected static DBConnection dbConn;
    protected static String baseURL;
    protected static String adminUsername, adminPassword;


    private void loadProperties() throws FileNotFoundException, IOException {
        String defaultConfigPropertiesFile = "resources/config/config.properties";
        File configProperties = new File(defaultConfigPropertiesFile);
        if (!configProperties.exists() || !configProperties.isFile()) {
            logger.error("Config file does not exist or is not a file!");
            System.exit(-1);
        }
        // load properties
        properties.load(new FileInputStream(defaultConfigPropertiesFile));
    }

    public WebDriver initWebDriver() {
        if (driver == null) {

            switch (this.browserToUse.toLowerCase()) {
                case "firefox":
                    ; //TODO: add support for firefox driver
                case "ie":
                    ; //TODO: add support for ie driver
                case "chrome":
                    ; // same as default
                default: {

                    // set system property if not set
                    if (Strings.isNullOrEmpty(System.getProperty("webdriver.chrome.driver"))) {
                        System.setProperty("webdriver.chrome.driver", getChromeBinaryLocation());
                    }
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (!this.visibleBrowser) {
                        chromeOptions.addArguments("--headless"); // not visible == headless
                    }
                    driver = new ChromeDriver(chromeOptions);
                    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                }
                break;
            }
        }
        return driver;
    }

    protected void closeWebDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    /*
     * TestNG test prepare and tear-down
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"browser"})
    public void beforeBaseSuite() throws IOException, SQLException, ClassNotFoundException {

        loadProperties();

        baseURL = properties.getProperty("app.url");
        adminUsername = properties.getProperty("admin.username");
        adminPassword = properties.getProperty("password");

        browserToUse = properties.getProperty("browser");
        visibleBrowser = Boolean.parseBoolean(properties.getProperty("visible.browser"));

        // create db connection
        String pwd = (properties.getProperty("database.password") != null ? properties.getProperty("database.password") : "");

        dbConn = new DBConnection("jdbc:mysql://"
                + properties.getProperty("database.ip")
                + ":" + properties.getProperty("database.port")
                + "/" + properties.getProperty("database.name"),
                properties.getProperty("database.user"), pwd);
        // Verify connection is working
        dbConn.executeQuery("SELECT 1");
        DBQueries.setDBConnection(dbConn);

        driver = initWebDriver();
        driver.manage().window().maximize();
        driver.get(baseURL);

        // cleanup mails from mailcatcher
    }

    @AfterSuite(alwaysRun = true)
    public void afterBaseSuite() throws SQLException {
        this.closeWebDriver();
        this.dbConn.close();
    }

    @AfterMethod(alwaysRun = true)
    public void baseAfterMethod() {
        // this will logout session
        JavascriptExecutor jsexec = (JavascriptExecutor) driver;
        jsexec.executeScript("$.ajax({" +
                "    type: \"DELETE\"," +
                "    url: \"/users/sign_out\"," +
                "    success: function(msg){" +
                "        location.reload();" +
                "    }" +
                "});");

        // give max 2sec for ajax to finish
        for (int i = 0; i < 10; i++) {
            if (0 != (Long) jsexec.executeScript("return jQuery.active")) {
                logger.debug("Ajax is active #" + i);
                try {
                    Thread.sleep(200);
                } catch (Exception ignored) {
                }
                continue;
            } else {
                break;
            }
        }

        // TODO: add some verification that user is logged out

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

    // This should be called before each test method
    protected void prepareBaseState(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logIn(username, password);
        Assert.assertTrue(loginPage.isLoggedIn(3), username + " is logged in");
        driver.get(baseURL);
    }
}
