package com.red.testframework.tests;

import com.red.testframework.db.DBConnection;
import com.red.testframework.db.DBQueries;
import com.red.testframework.pages.LoginPage;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.TestGroups;
import com.red.testframework.utils.Utils;

import org.testng.annotations.*;

import java.sql.*;

public class DatabaseTest {

    private LoginPage loginPage;
    private DBConnection dbConnection;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("CHROME") String browser) {
        loginPage = Utils.setUpWebBrowser(browser); // Running this class only will default to chrome. When called via testng.xml, CHROME will be ignored and
        // all tests will be treated respecting xml's config.
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName()); // Reading enclosing method name
    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {TestGroups.HIGH})
    public void testDBConnection() throws SQLException, ClassNotFoundException, SQLException {
        Log.startTest(new Object() {
        }.getClass().getEnclosingMethod().getName());
        DBQueries.getColumnData("SELECT * FROM samsara.heroes","name");
        dbConnection = new DBConnection();
        ResultSet rs = dbConnection.executeQuery("SELECT count(*) FROM heroes WHERE type='Guardian'");
        DBQueries.extract(rs);
    }

    @AfterMethod(alwaysRun = true)
    public void afterBaseSuite() throws SQLException {

        if (dbConnection != null)
            dbConnection.close();
        if (loginPage != null)
            loginPage.quitWebDriver();
    }
}
