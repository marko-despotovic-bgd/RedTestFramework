package com.red.testframework.utils.db;

import com.red.testframework.pages.LoginPage;
import com.red.testframework.utils.Log;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBQueries {

    private static DBConnection dbConnection;
    private static Logger log = LoggerFactory.getLogger(LoginPage.class);

    private DBQueries() {
        log = Log.getLog(DBConnection.class);
        dbConnection = DBConnection.createDBConnection();
    }

    @Contract(" -> new")
    public static @NotNull DBQueries createDBQueries() {
        return new DBQueries();
    }

    public void connectionInfoDBQuery() {
        try {
            Log.info("Connection established.\n Using " + dbConnection.getMetaData().getDriverName() + " driver for DB: " + dbConnection.getMetaData().getURL().substring(0, 31)); // Hardcoded, boundary to update based on database name
            System.out.println(dbConnection.getMetaData().getDriverName());
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
    }

    public static ResultSet showDatabaseDBQuery() {
        String showDatabaseDBQuery = "SHOW DATABASES;";
        return DBConnection.executeQuery(showDatabaseDBQuery);
    }

    public static ResultSet showTablesDBQuery() {
        String showTables = "SHOW TABLES;";
        return DBConnection.executeQuery(showTables);
    }

    public static void truncateTablesDBQuery() {
        DBConnection.executeUpdate("USE red");
        DBConnection.execute("SET FOREIGN_KEY_CHECKS=0");
        DBConnection.execute("TRUNCATE heroes");
        DBConnection.execute("TRUNCATE users");
        DBConnection.execute("SET FOREIGN_KEY_CHECKS=1");

    }

    public static boolean insertIntoUsersTableDBQuery(int user_id, String first_name, String last_name, String username) {
        String insertUsersTable = "INSERT INTO users VALUES ('" + user_id + "', '" + first_name + "', '" + last_name + "', '" + username + "', '$2a$10$c4k24Pk4lNy/v9wEZRsuT.LrTsYRLK7Jj7.mLahhCZwCgoWwAY7IW')";
        return DBConnection.execute(insertUsersTable);
    }

    public static boolean insertIntoHeroesTableDBQuery(int hero_id, String name, String type, int level, int fk_user_id) {
        String insertUsersTable = "INSERT INTO heroes VALUES ('" + hero_id + "', '" + name + "', '" + type + "', '" + level + "', '" + fk_user_id + "')";
        return DBConnection.execute(insertUsersTable);
    }

    public static void showTableData(String table) {
        String showTableData = "SELECT * FROM " + table + ";";
        DBConnection.execute(showTableData);
    }

    public static ResultSet selectDBQuery(String select, String from, String where) {
        String showTables = "SELECT " + select + " FROM " + from + " WHERE " + where + ";";
        return DBConnection.executeQuery(showTables);
    }

    public static boolean isResultSetEmpty(@NotNull ResultSet resultSet) {
        boolean isResultSetEmpty = false;
        try {
            isResultSetEmpty = resultSet.next();
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
        Log.info("Result Set is empty: " + isResultSetEmpty);
        return !isResultSetEmpty;
    }

//        public void dropAndRecreateDatabase() {
//        Utils utils = new Utils();
//        assert execute("DROP DATABASE IF EXISTS " + utils.getProperty(Constants.testDatabaseName) + ";\n" +
//                "CREATE DATABASE " + utils.getProperty(Constants.testDatabaseName) + ";\n" +
//                "USE " + utils.getProperty(Constants.testDatabaseName) + ";") : "Database not (re)created";
//    }

    // Attempt to keep consistency in relations, but it is failing at reading @tables after nullifying.
//        databaseUtil.execute("SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;");//-- save current foreign key settings and disable foreign key checks
//        databaseUtil.execute("SET GROUP_CONCAT_MAX_LEN=32768;"); //-- you never know how people name their tables
//        databaseUtil.execute("SET @tables = NULL;");
//        databaseUtil.execute("SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables FROM information_schema.tables WHERE table_schema = (SELECT DATABASE());"); //-- operates on the current DB
//        databaseUtil.execute("SELECT IFNULL(@tables,'dummy') INTO @tables;");  //-- avoid error if there are no tables
//        //"-- At this point you might want to double check the list of tables to be cleared.  Run SELECT @tables; to do so.
//        databaseUtil.execute("SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);");
//        databaseUtil.execute("PREPARE stmt FROM @tables;");
//        databaseUtil.execute("EXECUTE stmt;");
//        databaseUtil.execute("DEALLOCATE PREPARE stmt;");
//        databaseUtil.execute("SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;");
}