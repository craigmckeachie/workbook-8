package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String username = args[0];
        String password = args[1];


        //create a connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila",username, password);
        System.out.println(connection);

        //create a SQL statement/query
        String sql = """
                     select * 
                     from film;
                    """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //execute the statement/query
        ResultSet resultSet =  preparedStatement.executeQuery();

        //print header row
        System.out.printf("%-4s %-40s %10s%n", "Id", "Title", "Release Year");
        System.out.println("_________________________________________________________________________________");


        //loop through the results and display them
        while (resultSet.next()){
            int id = resultSet.getInt("film_id");
            String title = resultSet.getString("title");
            int releaseYear = resultSet.getInt("release_year");

            //print row
            System.out.printf("%-4d %-40s %10d%n", id, title, releaseYear);
        }

    }
}