package com.red.testframework.utils;

public class Constants {

    private Utils utils;

    // Login messages
    public static final String SUCCESSFUL_LOGOUT_MESSAGE = "You have been logged out.";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username and password.";
    // Pages' identifiers
    public static final String LOGIN_PAGE_PANEL_TITLE = "Samsara";
    public static final String SAMSARA_PAGE_PANEL_TITLE = "Hello, and welcome to our gamers page!";
    public static final String HOME_PAGE_PANEL_TITLE = "Samsara Homepage";
    public static final String USERS_PAGE_PANEL_TITLE = "Users";
    public static final String HEROES_PAGE_PANEL_TITLE = "Heroes";
    public static final String GALLERY_PAGE_PANEL_TITLE = "Gallery";
    public static final String API_PAGE_PANEL_TITLE = "API Documentation";
    public static final String BROKEN_LINK_PAGE_PANEL_TITLE = "Alice in Wonderland";
    public static final String ADMIN_PAGE_PANEL_TITLE = "Hey there admin!";
    public static final String PANEL_TITLE_XPATH = "//div[@class='panel-title text-center']";
    public static final String PANEL_XPATH = "//div[@class='my-jumbotron']//h1";
    // Test groups
    public static final String SANITY = "SANITY";
    public static final String CRITICAL = "CRITICAL";
    public static final String HIGH = "HIGH";
    public static final String MEDIUM = "MEDIUM";
    public static final String LOW = "LOW";
    public static final String DB_SANITY = "DB_SANITY";
    public static final String DB_CRITICAL = "DB_CRITICAL";
    public static final String DB_HIGH = "DB_HIGH";
    public static final String DB_MEDIUM = "DB_MEDIUM";
    public static final String DB_LOW = "DB_LOW";
    // Time utils
    public static final int ELEMENT_VISIBLE_TIME = 15;
    // Database
    public static final String url = "jdbc:mysql://"; //<-- Testing purpose "jdbc:sql://"
    public static final String databaseIP = "database.ip";
    public static final String databasePort = "database.port";
    public static final String databaseName = "database.name"; //<-- "Production" DB
    public static final String testDatabaseName = "test.database.name"; //<-- Test DB

}