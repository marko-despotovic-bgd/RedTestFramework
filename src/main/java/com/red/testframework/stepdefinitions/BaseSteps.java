package com.red.testframework.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.red.testframework.utils.TestConfiguration;
import com.red.testframework.utils.Log;

public class BaseSteps {
	 protected WebDriver driver;
	 TestConfiguration testConfiguration;
	 protected static Logger logger = Log.getLog(BaseSteps.class);
	 
	 public BaseSteps() {
		 testConfiguration = new TestConfiguration();
	 }
	 

}
