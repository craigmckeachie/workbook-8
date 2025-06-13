package com.pluralsight.data;

// Import statements - these bring in external classes we need to use
import com.pluralsight.model.Film;           // Our custom Film model class
import org.apache.commons.dbcp2.BasicDataSource; // Database connection pool manager
import java.sql.*;                           // All SQL-related classes (Connection, PreparedStatement, etc.)
import java.util.ArrayList;                  // Dynamic array implementation
import java.util.List;                       // Interface for ordered collections

/**
 * FilmDAO (Data Access Object) - This class handles all database operations for Film objects.
 * DAO pattern separates business logic from database access logic, making code more organized.
 */
public class FilmDAO {
    // Instance variable to hold our database connection pool
    // BasicDataSource manages multiple database connections efficiently
    BasicDataSource dataSource;

    /**
     * Constructor - called when creating a new FilmDAO object
     * @param dataSource - the database connection pool to use for all operations
     */
    public FilmDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource; // Store the dataSource for use in all methods
    }

    /**
     * Retrieves ALL films from the database
     * @return List<Film> - a collection of all Film objects in the database
     */
    public List<Film> getAll() {
        // SQL query using text blocks (""" """) - a modern Java feature for multi-line strings
        // This query selects all columns (*) from the film table
        String sql = """
                 select * 
                 from film;
                """;

        // Create an empty ArrayList to store Film objects we retrieve from database
        List<Film> films = new ArrayList<>();

        // Try-with-resources statement - automatically closes database connections when done
        // This prevents memory leaks and ensures proper cleanup
        try (
                // Get a connection from our connection pool
                Connection connection = this.dataSource.getConnection();
                // Create a PreparedStatement - safer than regular Statement, prevents SQL injection
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // Execute the query and get results
            // Another try-with-resources to ensure ResultSet is properly closed
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Loop through each row in the result set
                while (resultSet.next()) {
                    // Extract data from current row using column names
                    int id = resultSet.getInt("film_id");           // Get film_id as integer
                    String title = resultSet.getString("title");    // Get title as string
                    int releaseYear = resultSet.getInt("release_year"); // Get release_year as integer

                    // Create a new Film object using the constructor with the retrieved data
                    Film film = new Film(id, title, releaseYear);

                    // Add this Film object to our list
                    films.add(film);
                }
            }
        } catch (SQLException e) {
            // Handle any database errors that might occur
            // Show user-friendly message (what end users see)
            System.out.println("There was an error retrieving the films. Please try again or contact support.");
            // Show technical details for developers to debug
            e.printStackTrace();
        }

        // Return the list of films (could be empty if no films found)
        return films;
    }

    /**
     * Searches for films with titles that match a search term
     * @param searchTerm - the text to search for in film titles
     * @return List<Film> - films whose titles contain the search term
     */
    public List<Film> search(String searchTerm) {
        // SQL query with a placeholder (?) for the search term
        // LIKE operator allows partial matching, % wildcards would be added to searchTerm
        String sql = """
                 select * 
                 from film
                 where title like ?;
                """;

        List<Film> films = new ArrayList<>();

        try (
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // Set the first (and only) parameter in our SQL query
            // The ? in the SQL gets replaced with this value safely
            preparedStatement.setString(1, searchTerm);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Same logic as getAll() - loop through results and create Film objects
                while (resultSet.next()) {
                    int id = resultSet.getInt("film_id");
                    String title = resultSet.getString("title");
                    int releaseYear = resultSet.getInt("release_year");
                    Film film = new Film(id, title, releaseYear);
                    films.add(film);
                }
            }
        } catch (SQLException e) {
            System.out.println("There was an error retrieving the films. Please try again or contact support.");
            e.printStackTrace();
        }
        return films;
    }

    /**
     * Creates a new film record in the database
     * @param film - the Film object containing data to insert
     * @return Film - the same Film object, but now with the database-generated ID
     */
    public Film create(Film film) {
        // INSERT SQL statement - adds a new row to the film table
        // Note: film_id is not included because it's auto-generated by the database
        String sql = """
                 INSERT INTO film
                 (
                 `title`,
                 `release_year`,
                 `language_id`
                 )
                 VALUES
                 (
                  ?,
                  ?,
                  ?
                 );
                """;

        // Try-with-resources for database connection and prepared statement
        // Statement.RETURN_GENERATED_KEYS tells database to return the auto-generated ID
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the three parameters in our INSERT statement
            preparedStatement.setString(1, film.getTitle());      // First ? gets title
            preparedStatement.setInt(2, film.getReleaseYear());   // Second ? gets release year
            preparedStatement.setInt(3, film.getLanguageId());    // Third ? gets language ID

            // Execute the INSERT statement
            // executeUpdate() returns number of rows affected (should be 1 for successful insert)
            int rows = preparedStatement.executeUpdate();
            System.out.printf("Rows updated %d\n", rows);

            // Get the auto-generated primary key (film_id) that database created
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                // Loop through generated keys (usually just one)
                while (keys.next()) {
                    int id = keys.getInt(1); // Get the first (and typically only) generated key
                    System.out.printf("%d key was added\n", id);
                    film.setFilmId(id); // Update our Film object with the new ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error details for debugging
        }
        return film; // Return the Film object, now with its database ID
    }

    /**
     * Updates an existing film record in the database
     * @param filmId - the ID of the film to update
     * @param film - Film object containing the new data
     * @return Film - the updated Film object
     */
    public Film update(int filmId, Film film) {
        film.setFilmId(filmId); // Ensure the Film object has the correct ID

        // UPDATE SQL statement - modifies existing row where film_id matches
        String sql = """
                update film
                set
                	title = ?,
                    release_year = ?,
                    language_id = ?
                where film_id = ?;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the four parameters in our UPDATE statement
            preparedStatement.setString(1, film.getTitle());        // First ? gets new title
            preparedStatement.setLong(2, film.getReleaseYear());    // Second ? gets new release year
            preparedStatement.setLong(3, film.getLanguageId());     // Third ? gets new language ID
            preparedStatement.setLong(4, filmId);                   // Fourth ? gets film ID for WHERE clause

            // Execute the UPDATE statement
            int rows = preparedStatement.executeUpdate();
            System.out.printf("Rows updated %d\n", rows); // Should be 1 if film was found and updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film;
    }

    /**
     * Deletes a film record from the database
     * @param filmId - the ID of the film to delete
     */
    public void delete(int filmId) {
        // DELETE SQL statement - removes row where film_id matches
        String sql = """
                delete from film
                where film_id = ?;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the parameter for which film to delete
            preparedStatement.setInt(1, filmId); // The ? gets replaced with the filmId

            // Execute the DELETE statement
            int rows = preparedStatement.executeUpdate();
            System.out.printf("Rows deleted %d\n", rows); // Should be 1 if film was found and deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}