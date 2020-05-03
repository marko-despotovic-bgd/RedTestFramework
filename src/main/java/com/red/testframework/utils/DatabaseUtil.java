package com.red.testframework.utils;

import com.red.testframework.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseUtil  {

    private static Logger log = LoggerFactory.getLogger(LoginPage.class);
    private static Connection connection;

    public DatabaseUtil(){
        log = Log.getLog(DatabaseUtil.class);
    }

    public Connection getConnection() {
        Log.info(new Object() {
    }.getClass().getEnclosingMethod().getName());
        Utils utils = new Utils();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"
                            + utils.getProperty("database.ip")
                            + ":" + utils.getProperty("database.port")
                            + "/" + utils.getProperty("database.name")
                            + "?useSSL=false&"   // Do not use ssl
                            + "useUnicode=true&"
                            + "useJDBCCompliantTimezoneShift=true&"
                            + "useLegacyDatetimeCode=false&"
                            + "serverTimezone=UTC", // Due to java.lang.RuntimeException: java.sql.SQLException: The server time zone value 'Central Europe Summer Time' is unrecognized
                    utils.getProperty("database.user"), utils.getProperty("database.password"));

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Log.info("Executing query: '" + query + "'");
        return getConnection().createStatement().executeQuery(query);
    }

    public void closeConnection() throws SQLException {
        Log.info("Executing " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        if (!connection.isClosed()) connection.close();
    }


    public ArrayList<String> getColumnData(String query, String column) throws SQLException {
        ArrayList<String> arr = new ArrayList<>();
        PreparedStatement ps = getConnection().prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            arr.add(resultSet.getString(column));
            System.out.println("-----\n"+resultSet.getString(column)+"\n");
        }
        return arr;
    }

    public ArrayList<ArrayList<String>> extract(ResultSet resultSet)
            throws SQLException {
        ArrayList<ArrayList<String>> table;
        int columnCount = resultSet.getMetaData().getColumnCount();

        if (resultSet.getType() == ResultSet.TYPE_FORWARD_ONLY)
            table = new ArrayList<>();
        else {
            resultSet.last();
            table = new ArrayList<>(resultSet.getRow());
            resultSet.beforeFirst();
        }

        for (ArrayList<String> row; resultSet.next(); table.add(row)) {
            row = new ArrayList<>(columnCount);

            for (int c = 1; c <= columnCount; ++c)
                row.add(resultSet.getString(c).intern());
        }
        System.out.println(table);
        return table;
    }

}