package com.red.testframework.utils;

import com.red.testframework.pages.BasePage;
import org.openqa.selenium.WebDriver;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ScreenshotUtil extends BasePage {

    private static Properties properties = new Properties();
    private static WebDriver driver;
    private String screenshotFileLocation;
    private String saveScreenshots;

    public ScreenshotUtil(WebDriver driver) {
        super(driver);
    }

    private static void makeScreenshot(WebDriver driver, String imageNamePrefix, String folderName) {
        try {
            String screenshotFileLocation = properties.getProperty("screenshotFileLocation");


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + screenshotFileLocation;
            File destFile = null;
            if (StringUtils.isBlank(imageNamePrefix)) {
                destFile = new File((String) reportDirectory + folderName + "/Screenshot_" + formater.format(calendar.getTime()) + ".png");
            } else {
                destFile = new File((String) reportDirectory + folderName + "/" + imageNamePrefix + "_" + formater.format(calendar.getTime()) + ".png");
            }

            Log.info("Screenshot is saved as " + destFile.getAbsolutePath());

            FileUtils.copyFile(scrFile, destFile);
            Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
        } catch (IOException e) {
            Log.error("Something went wrong while taking screenshot!");
            e.printStackTrace();
        }
    }

    public static void makeScreenshot(WebDriver driver, ITestResult result, String imageNamePrefix) {

        imageNamePrefix = verifyImageNamePrefix(imageNamePrefix);

        boolean saveScreenshot = getSaveScreenshots();
        if (saveScreenshot) {
            if (result != null && !result.isSuccess()) {
                Log.info("Creating snapshot for failed method.");
                String failedLocation = "/failure_screenshots";
                makeScreenshot(driver, imageNamePrefix, failedLocation);
            } else {
                Log.info("Creating snapshot.");
                String screensthotLocation = "/screenshots";
                makeScreenshot(driver, imageNamePrefix, screensthotLocation);
            }
        }
    }

    public static boolean getSaveScreenshots() {
        if (StringUtils.isBlank(properties.getProperty("saveScreenshots"))) {
            return false;
        } else return properties.getProperty("saveScreenshots").equalsIgnoreCase("true");
    }

    public static void makeScreenshot(ITestResult result) {
        ScreenshotUtil.makeScreenshot(result, "");
    }

    public static void makeScreenshot(String imageNamePrefix) {
        ITestResult result = null;
        ScreenshotUtil.makeScreenshot(result, imageNamePrefix);
    }

    public static void makeScreenshot(ITestResult result, String imageNamePrefix) {
        ScreenshotUtil.makeScreenshot(driver, result, imageNamePrefix);
    }

    private static String verifyImageNamePrefix(String imageNamePrefix) {
        if (StringUtils.isNotBlank(imageNamePrefix) && !imageNamePrefix.matches("[a-zA-Z0-9-_]+")) {
            Log.error("Image name must contain only aplhanumers, - or _");
            Log.error("Using default name instead");
            imageNamePrefix = "";
        }
        return imageNamePrefix;
    }

}

