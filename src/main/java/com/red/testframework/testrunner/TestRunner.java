//package com.red.testframework.testrunner;
//
//import com.red.testframework.pages.LoginPage;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterMethod;
//
//import com.red.testframework.utils.ScreenshotUtil;
//
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//import io.cucumber.testng.CucumberOptions.SnippetType;
//
///**
// * The application class for running cucumber test scenarios, finding step definitions and creating reports
// */
//
//@CucumberOptions (
//    snippets = SnippetType.CAMELCASE,
//    features = {"features/RED"},
//    tags = {"@RED-FW"},
//    glue = {"com.red.testframework.stepdefinitions"},
//    plugin = {"pretty",
//    		"html:target/cucumber",
//    		"json:target/Json/cucumber.json"
//    		},
//        monochrome = true
//)
//
//public class TestRunner extends AbstractTestNGCucumberTests {
//	private LoginPage loginPage;
//
////	@AfterMethod
////	public void tearDownMethod(ITestResult result) {
////		if (result.getStatus() == ITestResult.FAILURE) {
////			ScreenshotUtil.makeScreenshot(result);
////		}
////	}
//}
