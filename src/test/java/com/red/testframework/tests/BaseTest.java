package com.red.testframework.tests;

import com.red.testframework.pages.LoginPage;
import com.red.testframework.pages.SamsaraPage;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    public LoginPage loginPage;
    public SamsaraPage samsaraPage;
    boolean loginSuccessful = false;

    private static void setContext(@NotNull ITestContext iTestContext, WebDriver driver) {
        iTestContext.setAttribute("driver", driver);
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("CHROME") String browser, ITestContext iTestContext) {
        loginPage = Utils.setUpWebBrowser(browser); // Running this class only will default to chrome. When called via testng.xml,
        // CHROME will be ignored and all tests will be treated respecting xml's config.
        Log.startTest(new Object(){}.getClass().getEnclosingMethod().getName()); // Reading enclosing method name
        setContext(iTestContext, loginPage.driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Cleaning after all test have been executed, regardless of outcome
        Log.endTest(new Object() {
        }.getClass().getEnclosingMethod().getName() + " in @AfterClass");
        if(loginPage!=null)
            loginPage.quitWebDriver();
        Log.endTest("=== That's all, folks! ===");
    }
}
