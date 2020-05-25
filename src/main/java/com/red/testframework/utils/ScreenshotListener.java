package com.red.testframework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ScreenshotListener extends TestListenerAdapter {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public void onTestFailure(ITestResult result) {
        String driverName = null;
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
        try {

            if (driver instanceof ChromeDriver)
                driverName = "chrome";
            else if (driver instanceof FirefoxDriver)
                driverName = "firefox";
            else if (driver instanceof InternetExplorerDriver)
                driverName = "IE";
            else if (driver instanceof EdgeDriver)
                driverName = "edge";

            String screenshotDirectory = Utils.getProperty("screenshotFileLocation") + File.separator + sdf.format(timestamp);
            boolean isDirectoryCreated = (new File(screenshotDirectory)).mkdir();
            if (isDirectoryCreated)
                Log.info("Directory: " + screenshotDirectory + " created");
            String screenshotAbsolutePath = screenshotDirectory + File.separator + driverName + "_" + System.currentTimeMillis() + "_" + result.getName() + ".png";
            File screenshot = new File(screenshotAbsolutePath);
            if (createFile(screenshot)) {
                try {
                    writeScreenshotToFile(driver, screenshot);
                } catch (ClassCastException weNeedToAugmentOurDriverObject) {
                    writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
                }
                Log.info("Written screenshot to " + screenshotAbsolutePath);
            } else {
                Log.error("Unable to create " + screenshotAbsolutePath);
            }
        } catch (Exception ex) {
            Log.error("Unable to capture screenshot...");
            ex.printStackTrace();
        }
    }

    private boolean createFile(File screenshot) {
        boolean fileCreated = false;

        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                try {
                    fileCreated = screenshot.createNewFile();
                } catch (IOException errorCreatingScreenshot) {
                    errorCreatingScreenshot.printStackTrace();
                }
            }
        }
        return fileCreated;
    }

    private void writeScreenshotToFile(WebDriver driver, File screenshot) {
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            screenshotStream.close();
        } catch (IOException unableToWriteScreenshot) {
            Log.error("Unable to write " + screenshot.getAbsolutePath());
            unableToWriteScreenshot.printStackTrace();
        }
    }
}