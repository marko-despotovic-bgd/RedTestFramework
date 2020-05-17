package com.red.testframework.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.red.testframework.pages.BasePage;
import org.testng.Reporter;

public class ScreenshotListener implements ITestListener {
    WebDriver driver;

    @Override
    public void onTestFailure(@NotNull ITestResult result) {
        System.out.println("***** Error " + result.getName() + " test has failed *****");
        String methodName = result.getName().trim();
        ITestContext context = result.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        takeScreenShot(methodName, driver);
    }

    public void takeScreenShot(String methodName, WebDriver driver) {
        try {
            //The below method will save the screen shot in d drive with test method name
            String screenshotFileLocation = Utils.getProperty("screenshotFileLocation");


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + screenshotFileLocation;
            File destinationFile = null;
            destinationFile = new File((String) reportDirectory + "failure_screenshots/Screenshot_" + methodName + formatter.format(calendar.getTime()) + ".png");


            Log.info("Screenshot is saved as " + destinationFile.getAbsolutePath());

            FileUtils.copyFile(scrFile, destinationFile);
            Reporter.log("<a href='" + destinationFile.getAbsolutePath() + "'> <img src='" + destinationFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
        } catch (
                IOException e) {
            Log.error("Something went wrong while taking screenshot!");
            e.printStackTrace();
        }

    }

    public void onFinish(ITestContext context) {
    }

    public void onTestStart(ITestResult result) {
    }

    public void onTestSuccess(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    }
}