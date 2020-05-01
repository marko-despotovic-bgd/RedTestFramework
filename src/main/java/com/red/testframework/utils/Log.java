package com.red.testframework.utils;

import java.util.Date;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private static Logger log;

    @SuppressWarnings("rawtypes")
	public static Logger getLog(Class className) {
        return log = LoggerFactory.getLogger(className);
    }

    public static void startTest(String testName) {

        log.info("************************************************************************************");
        log.info("\t-----S-T-A-R-T-----\t\t" +testName);
        log.info("************************************************************************************" + "\n");
    }

    public static void endTest(String testName) {

        log.info("************************************************************************************");
        log.info("\t-----E-N-D--------\t\t" + testName);
        log.info("************************************************************************************" + "\n");
    }

    public static void info(String message) {
        log.info(message + "\n");
    }

    public static void warn(String message) {
        log.warn(message + "\n");
    }

    public static void error(String message) {
        log.error(message + "\n");
    }

    public static void debug(String message) {
        log.debug(message + "\n");
    }

    public static void browserConsoleLog(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
    }

    public static LoggingPreferences getLogPreference(){
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        return loggingPreferences;
    }
}
