package com.red.testframework.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQueries {

    private static DBConnection dbConn;

    public static void setDBConnection(DBConnection conn) {
        dbConn = conn;
    }


    private static String EMPLOYEE_PROFILES_JOIN_QUERY = "FROM profiles p JOIN employees e ON p.person_id = e.id";

    public static ResultSet getEmployeeProfiles(String email) throws SQLException {
        String query = "SELECT * "
                + EMPLOYEE_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";
        return dbConn.executeQuery(query);
    }

    public static long getEmployeeProfilesCount(String email) throws SQLException {
        String query = "SELECT count(*) "
                + EMPLOYEE_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";

        ResultSet rs = dbConn.executeQuery(query);
        rs.next();
        return rs.getLong(1);
    }

    private static String USER_PROFILES_JOIN_QUERY = "FROM profiles p JOIN users u ON p.person_id = u.employee_id";

    public static ResultSet getUserProfiles(String email) throws SQLException {
        String query = "SELECT * "
                + USER_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";
        return dbConn.executeQuery(query);
    }

    public static long getUserProfilesCount(String email) throws SQLException {
        String query = "SELECT count(*) "
                + USER_PROFILES_JOIN_QUERY
                + " WHERE p.email = '" + email + "';";
        ResultSet rs = dbConn.executeQuery(query);

        rs.next();
        return rs.getLong(1);
    }
}
