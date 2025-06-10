package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String username = args[0];
        String password = args[1];

        //create a connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila",username, password);
        System.out.println(connection);

    }
}