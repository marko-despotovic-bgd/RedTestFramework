package com.red.testframework.utils;


import java.util.Properties;

public class DatabaseUtil  {

    private Properties properties = new Properties();
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
