package com.sandor.database;

import java.sql.*;

public class DBConnection {

    public static Connection connection;

    public static Connection getConnection(){
        if(connection == null) {
            try {
                //Get connection to database
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root", "root");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static ResultSet performQuerry(String sql){
        try {
            Statement stmt = getConnection().createStatement();

            return stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int performUpdate(String sql){
        try {
            Statement stmt = getConnection().createStatement();

            return stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
