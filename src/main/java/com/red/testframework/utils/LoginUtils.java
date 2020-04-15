package com.red.testframework.utils;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class LoginUtils {

    private JavascriptExecutor js;

    public LoginUtils(WebDriver driver){
        this.js = (JavascriptExecutor)driver;
    }
    public String getWindowLocationHostname(){
        return js.executeScript("return window.location.hostname;").toString();
    }
}
