package com.red.testframework.api;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.red.testframework.testconfiguration.TestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;


public class BaseApi {

    private TestConfiguration testConfiguration;
    protected String baseUri = "";
    private static Logger logger = LoggerFactory.getLogger(BaseApi.class);
    private final String defaultConfigPropertiesFile = TestConfiguration.getConfigurationFilename();

    public BaseApi() throws IOException {
        loadProperties(defaultConfigPropertiesFile);
        baseUri = testConfiguration.getUrl();
    }

    private void loadProperties(String path) throws IOException {
        File configProperties = new File(defaultConfigPropertiesFile);
        if (!configProperties.exists() || !configProperties.isFile())
            logger.error("config-file does not exist or is not a file!");
        new FileInputStream(defaultConfigPropertiesFile);
    }

    /**
     * GET root / should return Status 200 OK
     * <br>Prerequisite for all other tests.
     */
    @BeforeTest(description = "Testing GET status code 200 for base URL as a prerequisite for all tests",
            alwaysRun = true)
    public void baseUrlTest_StatusCode200() {
        RestAssured
                .when().get(baseUri)
                .then().statusCode(200);
    }
}