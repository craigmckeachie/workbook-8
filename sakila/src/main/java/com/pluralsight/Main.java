package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);


        Scanner scanner = new Scanner(System.in);
        System.out.print("Search for films that start with: ");
        String searchTerm = scanner.nextLine() + "%";

        String sql = """
                 select * 
                 from film
                 where title like ?;
                """;

        try (
                //create a connection
                Connection connection = dataSource.getConnection();
                //create a SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql)

        ) {

            //execute the statement/query
            preparedStatement.setString(1, searchTerm);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                //print header row
                System.out.printf("%-4s %-40s %10s%n", "Id", "Title", "Release Year");
                System.out.println("_________________________________________________________________________________");

                //loop through the results and display them
                while (resultSet.next()) {
                    int id = resultSet.getInt("film_id");
                    String title = resultSet.getString("title");
                    int releaseYear = resultSet.getInt("release_year");

                    //print row
                    System.out.printf("%-4d %-40s %10d%n", id, title, releaseYear);
                }
            }

        } catch (SQLException e) {
            //display user friendly error message
            System.out.println("There was an error retrieving the films. Please try again or contact support.");
            //display error message for the developer
            e.printStackTrace();
        }

    }
}