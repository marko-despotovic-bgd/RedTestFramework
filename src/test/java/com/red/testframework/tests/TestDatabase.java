package com.red.testframework.tests;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.DatabaseUtil;
import com.red.testframework.pages.LoginPage;

import org.testng.annotations.*;

import java.sql.*;

public class TestDatabase {

    private LoginPage loginPage;
    private DatabaseUtil databaseUtil;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUpDBConnection(@Optional("CHROME") String browser) {
        databaseUtil = new DatabaseUtil();
    }

    /*
     * Test validates that proper error message is displayed upon unsuccessful login
     */
    @Test(groups = {Constants.CRITICAL})
    public void testDBConnection() throws SQLException, ClassNotFoundException, SQLException {
//        Log.startTest(new Object() {
//        }.getClass().getEnclosingMethod().getName());
         ResultSet rs = databaseUtil.executeQuery("SELECT count(*) FROM heroes WHERE type='Guardian'");
        databaseUtil.extract(rs);
    }

    @AfterMethod(alwaysRun = true)
    public void afterBaseSuite() throws SQLException {

        if (databaseUtil != null)
            databaseUtil.close();
    }
}
