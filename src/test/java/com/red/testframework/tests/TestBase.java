package com.red.testframework.tests;

import com.red.testframework.pages.LoginPage;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.Utils;

import org.testng.annotations.*;

import java.sql.SQLException;

public class TestBase {

    private LoginPage loginPage;
    private Utils utils;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("CHROME") String browser) {
        loginPage = Utils.setUpWebBrowser(browser); // Running this class only will default to chrome. When called via testng.xml, CHROME will be ignored and
        // all tests will be treated respecting xml's config.
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName()); // Reading enclosing method name
        utils = new Utils();
    }

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

        if(loginPage!=null)
            loginPage.quitWebDriver();
    }
//
//    // This should be called before each test method
//    protected void prepareBaseState(String username, String password) {
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.logIn(username, password);
//        Assert.assertTrue(loginPage.isLoggedIn(3), username + " is logged in");
//        driver.get(baseURL);
//    }
}
