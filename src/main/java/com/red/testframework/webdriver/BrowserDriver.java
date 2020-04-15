package com.red.testframework.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red.testframework.testconfiguration.TestConfiguration;

public class BrowserDriver {

    private static WebDriver driver;
    private static Logger log = LoggerFactory.getLogger(BrowserDriver.class);

    public static WebDriver getCurrentDriver(){
        if(driver == null){
            try {
            	
                driver = WebDriverFactory.initDriver(new TestConfiguration());
            } catch (Exception e) {
                log.error("Driver failed to initialize" , e);
            }
            finally {
                Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
            }
        }
        return driver;
    }

    public static WebDriver getCurrentDriver(TestConfiguration testConfiguration){
        if (driver!=null){
            close();
        }
        try {
            driver = WebDriverFactory.initDriver(testConfiguration);
        } catch (Exception e) {
            log.error("Driver failed to initialize", e);
        }finally {
            Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
        }

        return driver;
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            close();
        }
    }

    public static void close(){
        try {
            getCurrentDriver().quit();
            driver = null;
        }
        catch (UnreachableBrowserException e){
            log.error("Unreachable Browser Exception", e);
        }
    }
}
