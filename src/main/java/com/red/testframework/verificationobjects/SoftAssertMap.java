package com.red.testframework.verificationobjects;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import com.red.testframework.pages.BasePage;
import com.red.testframework.utils.Log;

/**
 * Container class for storing and verifying {@link SoftAssertCouple} objects
 *
 */
@SuppressWarnings("rawtypes")
public class SoftAssertMap<T> {

    
	private Map<String, SoftAssertCouple> map;
	private static Logger log = LoggerFactory.getLogger(BasePage.class);
	
    public SoftAssertMap(Class clazz) {
        map = new HashMap<>();
        log = Log.getLog(clazz);
    }

    /**
     * Adds new pair of values (actual, expected) for the given field
     * @param element - name of the field
     * @param actual - actual value for assertion
     * @param expected - expected value for assertion
     */
    public void add(T actual, T expected, String element) {
        map.put(element, new SoftAssertCouple(actual, expected));
    }

    /**
     * Adds new pair of values (actual, expected) for the given field
     * @param element - name of the field
     * @param values - pair of values for assert
     */
    private void add(String element, SoftAssertCouple values) {
        map.put(element, values);
    }

    public Map<String, SoftAssertCouple> getMap() {
        return map;
    }

    /**
     * Verify all elements in the map using {@link SoftAssert}
     */
    public void verify() {
        Log.info("-------------------------  Soft Assert Check  --------------------------");
        SoftAssert sa = new SoftAssert();
        map.forEach((element, values) -> {
            Log.info("----- Checking element: " + element);
            Log.info("ACTUAL:     " + values.getActual().toString());
            Log.info("EXPECTED:   " +  values.getExpected().toString());
            sa.assertEquals(values.getActual(), values.getExpected());
        });
        Log.info("----------------");
        sa.assertAll();
    }
}
