package ru.iamaswiftie.network;

import java.sql.*;

public class ManagementSystem {

    private static Connection connection;
    private static ManagementSystem instance;
    private static String DatabaseURL = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&use" +
            "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //

    private ManagementSystem() throws Exception {
        try {
            connection = DriverManager.getConnection(DatabaseURL, "root", "root");
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
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE user = \"" + user + "\" and password = \"" + password + "\"";

            resultSet = statement.executeQuery(query);
            if(!resultSet.next()) { //return false if in database no user with this username and password
                resultSet.close();
                return false;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return true;
    }

    /*
    Function for checking if user exists
    Made to avoid the multiple registrations the same-named persons
     */
    public synchronized boolean doesThisUserExist(String user) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE user = \"" + user + "\"";

            resultSet = statement.executeQuery(query);
            if(!resultSet.next()) { //return false if in database no user with this username and password
                resultSet.close();
                return false;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
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

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();

        return true;
    }
}

