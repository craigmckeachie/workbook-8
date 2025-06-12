package com.pluralsight.data;

import com.pluralsight.model.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    BasicDataSource dataSource;

    public FilmDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Film> getAll() {
        String sql = """
                 select * 
                 from film;
                """;
        List<Film> films = new ArrayList<>();

        try (
                //create a connection
                Connection connection = this.dataSource.getConnection();
                //create a SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql)

        ) {

            //execute the statement/query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                //loop through the results and put them into model objects
                while (resultSet.next()) {
                    int id = resultSet.getInt("film_id");
                    String title = resultSet.getString("title");
                    int releaseYear = resultSet.getInt("release_year");
                    Film film = new Film(id, title, releaseYear);
                    films.add(film);
                }
            }

        } catch (SQLException e) {
            //display user friendly error message
            System.out.println("There was an error retrieving the films. Please try again or contact support.");
            //display error message for the developer
            e.printStackTrace();
        }
        return films;
    }

    public List<Film> search(String searchTerm) {
        String sql = """
                 select * 
                 from film
                 where title like ?;
                """;
        List<Film> films = new ArrayList<>();

        try (
                //create a connection
                Connection connection = this.dataSource.getConnection();
                //create a SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql)

        ) {

            //execute the statement/query
            preparedStatement.setString(1, searchTerm);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                //loop through the results and put them into model objects
                while (resultSet.next()) {
                    int id = resultSet.getInt("film_id");
                    String title = resultSet.getString("title");
                    int releaseYear = resultSet.getInt("release_year");
                    Film film = new Film(id, title, releaseYear);
                    films.add(film);
                }
            }

        } catch (SQLException e) {
            //display user friendly error message
            System.out.println("There was an error retrieving the films. Please try again or contact support.");
            //display error message for the developer
            e.printStackTrace();
        }
        return films;
    }
}
