package ru.iamaswiftie.network;

import javax.swing.*;
import java.sql.*;

public class ManagementSystem {

    private static Connection con;
    private static ManagementSystem instance;

    private ManagementSystem() throws Exception {
        try {
            //String url = "jdbc:mysql://localhost/medicines?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String url = "jdbc:mysql://localhost:3306/users?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "12212298aa");
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

    public boolean authorization(String user, String password) throws SQLException {
        if (user.equals("") || password.equals("")) {
            //JOptionPane.showMessageDialog(this, "Enter something!");
            System.out.println("kapec");
            return false;
        }
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM users.users WHERE user = \"" + user + "\" and password = \"" + password + "\"";

            rs = stmt.executeQuery(query);
            if(!rs.next()) {
                rs.close();
                System.out.println("pizdec");
                return false;
            }
            while (rs.next()) {
                System.out.println("эта запись никогда не отобразится");
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
        //Случай, когда введено что-то левое
        /*else if (!user_input.getText().equals("glad_valakas") && !new String(password_input.getPassword()).equals("1234")) {
            //JOptionPane.showMessageDialog(AuthorizationWindow.this, "The password/username is wrong");
            return false;
        }*/
    }
}

