package ru.iamaswiftie.network;

import java.sql.*;

public class ManagementSystem {

    private static Connection con;
    private static ManagementSystem instance;
    private static String DatabaseURL = "jdbc:mysql://localhost:3306/users?useUnicode=true&use" +
            "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //

    private ManagementSystem() throws Exception {
        try {
            con = DriverManager.getConnection(DatabaseURL, "root", "12212298aa");
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized ManagementSystem getInstance() throws Exception{
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }

    /*
    Authorization
     */
    public synchronized boolean authorization(String user, String password) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM users WHERE user = \"" + user + "\" and password = \"" + password + "\"";

            rs = stmt.executeQuery(query);
            if(!rs.next()) { //return false if in database no user with this username and password
                rs.close();
                return false;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return true;
    }

    /*
    Function for checking if user exists
    Made to avoid the multiple registrations the same-named persons
     */
    public synchronized boolean doesThisUserExist(String user) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM users WHERE user = \"" + user + "\"";

            rs = stmt.executeQuery(query);
            if(!rs.next()) { //return false if in database no user with this username and password
                rs.close();
                return false;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return true;
    }

    /*
    Registration
     */
    public synchronized boolean registration(String user, String password) throws SQLException {
        String query = "INSERT INTO users (user, password) \n" +
                " VALUES (?, ?);";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();

        return true;
    }
}

