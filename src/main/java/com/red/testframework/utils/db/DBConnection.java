package com.red.testframework.utils.db;

import com.red.testframework.utils.Constants;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.Utils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;


public class DBConnection {

    private static Connection connection;

    private DBConnection() {
        Utils utils = new Utils();
        // Step 1 - Load driver
        // Class.forName("com.mysql.cj.jdbc.Driver"); //Class.forName() is not needed since JDBC 4.0
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(Constants.url
                                + utils.getProperty(Constants.databaseIP)
                                + ":" + utils.getProperty(Constants.databasePort)
                                + "/" + utils.getProperty(Constants.testDatabaseName)
                                + "?useSSL=false&"   // Do not use ssl
                                + "useUnicode=true&"
                                + "useJDBCCompliantTimezoneShift=true&"
                                + "useLegacyDatetimeCode=false&"
                                + "serverTimezone=UTC", // Due to java.lang.RuntimeException: java.sql.SQLException: The server time zone value 'Central Europe Summer Time' is unrecognized
                        utils.getProperty("database.user"), utils.getProperty("database.password"));
            } catch (SQLException SQLEx) {
                SQLEx.printStackTrace();
            }
        }
    }


    @Contract(" -> new")
    public static @NotNull DBConnection createDBConnection() {
        return new DBConnection();
    }

    public static ResultSet executeQuery(String query) {
        Log.info("Executing query: '" + query + "'");
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery(query);
            printResultSet(resultSet);

        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
        return resultSet;
    }


    public static boolean execute(String query) {
        // Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement
        // or an SQL statement that returns nothing, such as an SQL DDL statement.
        Log.info("Executing : '" + query + "'");
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            result = statement.execute(query);
            while (result) {
                ResultSet rs = statement.getResultSet();
                printResultSet(rs);
                result = statement.getMoreResults();

            }
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
        Log.info("Query successfully executed: " + !result);
        return result;
    }

    public static void executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
    }


    public void closeConnection() {
        Log.info("Executing " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
    }

    public static void printResultSet(@NotNull ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        // Print column names
        for (int i = 1; i <= columnsNumber; i++) {
            if (i > 1) System.out.print("\t|\t ");
            System.out.print(rsmd.getColumnName(i));
        }
        System.out.println("");

        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print("\t|\t");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }

    public DatabaseMetaData getMetaData() {
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
        return metaData;
    }
}