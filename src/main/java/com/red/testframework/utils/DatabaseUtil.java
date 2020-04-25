package com.red.testframework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseUtil extends TestConfiguration {

    private Properties properties = super.getProperties();

    private String dbIP;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    public DatabaseUtil() {
        setDBFields();
    }

    private void setDBFields() {

        this.dbIP = properties.getProperty("database.ip");
        this.dbPort = properties.getProperty("database.port");
        this.dbName = properties.getProperty("database.name");
        this.dbUser = properties.getProperty("database.user");
        this.dbPassword = properties.getProperty("database.password");
    }
}
