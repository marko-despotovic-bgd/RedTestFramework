package com.red.testframework.db;

import com.red.testframework.utils.Log;
import com.red.testframework.utils.Utils;
import java.sql.*;


public class DBConnection {


    private static Connection connection;

    public Connection getConnection() {
        Utils utils = new Utils();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            connection = DriverManager.getConnection("jdbc:mysql://"
                            + utils.getProperty("database.ip")
                            + ":" + utils.getProperty("database.port")
                            + "/" + utils.getProperty("database.name")
                            + "?useSSL=false&",  // Do not use ssl
                    utils.getProperty("database.user"), utils.getProperty("database.password"));

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Log.debug("executing query: '" + query + "'");
        return connection.createStatement().executeQuery(query);
    }
    public void close() throws SQLException {
        if (!connection.isClosed()) connection.close();
    }

}
