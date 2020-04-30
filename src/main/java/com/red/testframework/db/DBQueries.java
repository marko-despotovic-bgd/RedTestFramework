package com.red.testframework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBQueries {

    private static DBConnection dbConnection;

    public static void setDBConnection(DBConnection connection) {
        dbConnection = connection;
    }


    private static String EMPLOYEE_PROFILES_JOIN_QUERY = "FROM profiles p JOIN employees e ON p.person_id = e.id";

    public static ResultSet getEmployeeProfiles(String email) throws SQLException {
        String query = "SELECT * "
                + EMPLOYEE_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";
        return dbConnection.executeQuery(query);
    }

    public static long getEmployeeProfilesCount(String email) throws SQLException {
        String query = "SELECT count(*) "
                + EMPLOYEE_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";

        ResultSet rs = dbConnection.executeQuery(query);
        rs.next();
        return rs.getLong(1);
    }

    private static String USER_PROFILES_JOIN_QUERY = "FROM profiles p JOIN users u ON p.person_id = u.employee_id";

    public static ResultSet getUserProfiles(String email) throws SQLException {
        String query = "SELECT * "
                + USER_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";
        return dbConnection.executeQuery(query);
    }

    public static long getUserProfilesCount(String email) throws SQLException {
        String query = "SELECT count(*) "
                + USER_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";
        ResultSet rs = dbConnection.executeQuery(query);

        rs.next();
        return rs.getLong(1);
    }
    public static ArrayList<String> getColumnData(String query, String column) throws SQLException {
        ArrayList<String> arr = new ArrayList<>();
        dbConnection = new DBConnection();
        PreparedStatement ps = dbConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            arr.add(resultSet.getString(column));
            System.out.println("-----\n"+resultSet.getString(column)+"\n");
        }
        return arr;
    }

    public static ArrayList<ArrayList<String>> extract(ResultSet resultSet)
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
