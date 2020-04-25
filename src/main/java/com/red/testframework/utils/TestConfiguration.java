package com.red.testframework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.red.testframework.enums.BrowserType;
import com.red.testframework.enums.TestExecutionEnvironment;

public class TestConfiguration {

    private Properties properties = new Properties();

    private BrowserType browserType;
    private String chromeDriverPath;
    private String firefoxDriverPath;
    private String ieDriverPath;
    private String edgeDriverPath;

    private String url;
    private String admin, user;
    private String password;

    private String screenshotFileLocation;
    private String saveScreenshots;

    private TestExecutionEnvironment testExecutionEnvironment;


    private static final String configurationFileName = "resources/config/config.properties";

    public TestConfiguration() {

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(configurationFileName);
            this.properties.load(inputStream);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        setLocalFields();
    }

    private String getFullPath(String relativePath) {

        ClassLoader resource = this.getClass().getClassLoader();
        URL path = this.getClass().getResource(configurationFileName);
        return path.toString();

    }

    public String getSetting(String propertyName) {
        // add try catch
        return properties.getProperty(propertyName);
    }

    private void setLocalFields() {

        this.url = properties.getProperty("app.url");
        this.admin = properties.getProperty("admin.username");
        this.password = properties.getProperty("password");
        this.user = properties.getProperty("user.username");

        this.testExecutionEnvironment = setTestExecutionEnvironment(properties.getProperty("testExecutionEnvironment"));
        this.browserType = setBrowserType(properties.getProperty("browserType"));


        this.screenshotFileLocation = properties.getProperty("screenshotFileLocation");
        this.saveScreenshots = properties.getProperty("saveScreenshots");

        this.chromeDriverPath = properties.getProperty("chromeDriverPath");
        this.firefoxDriverPath = properties.getProperty("firefoxDriverPath");
        this.ieDriverPath = properties.getProperty("ieDriverPath");
        this.edgeDriverPath = properties.getProperty("edgeDriverPath");
    }

    public String getScreenshotFileLocation() {
        return this.screenshotFileLocation;
    }

    public boolean getSaveScreenshots() {
        if (StringUtils.isBlank(this.saveScreenshots)) {
            return false;
        } else
            return this.saveScreenshots.equalsIgnoreCase("true");
    }

    private TestExecutionEnvironment setTestExecutionEnvironment(String executionEnvironmentName) {
        for (TestExecutionEnvironment execution : TestExecutionEnvironment.values()) {
            if (execution.toString().toLowerCase().equals(executionEnvironmentName.toLowerCase())) {
                return execution;
            }
        }
        return null;
    }

    private BrowserType setBrowserType(String browserName) {
        for (BrowserType browser : BrowserType.values()) {
            if (browser.toString().toLowerCase().equals(browserName.toLowerCase())) {
                return browser;
            }
        }
        return null;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdminName() {
        return admin;
    }

    public String getUserUsername() {
        return user;
    }

    public void setUsername(String username) {
        this.user = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TestExecutionEnvironment getTestExecutionEnvironment() {
        return testExecutionEnvironment;
    }

    public void setScreenshotFileLocation(String screenshotFileLocation) {
        this.screenshotFileLocation = screenshotFileLocation;
    }

    public void setSaveScreenshots(String saveScreenshots) {
        this.saveScreenshots = saveScreenshots;
    }

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public String getFirefoxDriverPath() {
        return firefoxDriverPath;
    }

    public String getIeDriverPath() {
        return ieDriverPath;
    }

    public String getEdgeDriverPath() {
        return edgeDriverPath;
    }

    public static String getConfigurationFilename() {
        return configurationFileName;
    }

}
