package com.red.testframework.utils;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import com.red.testframework.verificationobjects.SoftAssertMap;


public class Verification {

	public static void verify(boolean actual, boolean expected, String message) {
		Log.info("------------------------- " + message + " --------------------------");
		Log.info("ACTUAL:     " + ((actual) ? "TRUE" : "FALSE"));
		Log.info("EXPECTED:   " + ((expected) ? "TRUE" : "FALSE"));
		Log.info("----------------");
		Assert.assertEquals(actual, expected);
	}
	
	public static void verifyTrue(boolean actual, String message) {
		verify(actual, true, message);
	}

    public static void verifyFalse(boolean actual, String message) {

		verify(actual, false, message);
    }

	public static void verify(int actual, int expected, String message) {
		Log.info("------------------------- " + message + " --------------------------");
		Log.info("ACTUAL:     " + actual);
		Log.info("EXPECTED:   " + expected);
		Log.info("----------------");
		Assert.assertEquals(actual, expected);
	}

	public static void verify(long actual, long expected, String message) {
		Log.info("------------------------- " + message + " --------------------------");
		Log.info("ACTUAL:     " + actual);
		Log.info("EXPECTED:   " + expected);
		Log.info("----------------");
		Assert.assertEquals(actual, expected);
	}
	
	public static void verify(String actual, String expected, String message) {
		Log.info("------------------------- " + message + " --------------------------");
		Log.info("ACTUAL:     " + actual);
		Log.info("EXPECTED:   " + expected);
		Log.info("----------------");
		Assert.assertEquals(actual, expected);
	}

	@SuppressWarnings("rawtypes")
	public static void verify(SoftAssertMap map) {
		map.verify();
	}
	
	public static void failTest(String message) {
		Log.info("!!!!!!!!!!! " + message + " !!!!!!!!!!!");
		Assert.fail();
	}
	
	public static void verifyPageIsDisplayed(boolean isDisplayedUniqueElement, String pageName, String message) {
		Log.info("########################################################################################");
        Log.info("------------------------- Verify Page is Displayed: " + pageName + " ----------------");
        if(StringUtils.isNotBlank(message)) {
        	Log.info("MESSAGE:    " + message );
            Log.info("");
        }
		Log.info("ACTUAL:     " + ((isDisplayedUniqueElement) ? "DISPLAYED" : "NOT DISPLAYED"));
		Log.info("EXPECTED:   DISPLAYED");
		Log.info("-------------------------");
		Assert.assertEquals(isDisplayedUniqueElement, true);
	}
	
	public static void verifyPageIsDisplayed(boolean isDisplayedUniqueElement, String pageName) {
		Verification.verifyPageIsDisplayed(isDisplayedUniqueElement, pageName, "");
	}
	
	public static void verifyPageIsNotDisplayed(boolean isDisplayedUniqueElement, String pageName, String message) {		
		Log.info("########################################################################################");
        Log.info("---------------- Verify Page is NOT Displayed: " + pageName + " ---------------------------");
        if(StringUtils.isNotBlank(message)) {
        	Log.info("MESSAGE:    " + message );
            Log.info("");
        }
		Log.info("ACTUAL:     " + ((isDisplayedUniqueElement) ? "DISPLAYED" : "NOT DISPLAYED"));
		Log.info("EXPECTED:   NOT DISPLAYED");
		Log.info("-------------------------");
		Assert.assertEquals(isDisplayedUniqueElement, false);
	}
	
	public static void verifyPageIsNotDisplayed(boolean isDisplayedUniqueElement, String pageName) {
		Verification.verifyPageIsNotDisplayed(isDisplayedUniqueElement, pageName, "");
	}


}
