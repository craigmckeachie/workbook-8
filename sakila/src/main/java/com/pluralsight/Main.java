package com.pluralsight;

// Import statements - bringing in classes we need from other packages
import com.pluralsight.data.FilmDAO;              // Our custom FilmDAO class
import com.pluralsight.model.Film;                // Our custom Film model class
import org.apache.commons.dbcp2.BasicDataSource;  // Database connection pool manager
import java.util.List;                            // Interface for ordered collections
import java.util.Scanner;                         // Class for reading user input from console

/**
 * Main class - the entry point of our application
 * This class demonstrates how to use the FilmDAO to perform database operations
 * It serves as the "presentation layer" that interacts with users and calls the DAO
 */
public class Main {

    /**
     * main method - the starting point of any Java application
     * The JVM (Java Virtual Machine) looks for this exact method signature to run the program
     * @param args - command line arguments passed when running the program
     */
    public static void main(String[] args) {
        // Extract database credentials from command line arguments
        // args[0] is the first argument, args[1] is the second
        // Program expects: java Main <username> <password>
        String username = args[0];  // Database username
        String password = args[1];  // Database password

        // Create and configure a database connection pool
        // BasicDataSource manages multiple database connections efficiently
        BasicDataSource dataSource = new BasicDataSource();

        // Set database connection properties
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");  // MySQL database URL
        // jdbc:mysql://  - tells Java this is a MySQL database
        // localhost:3306 - database server location (local machine, port 3306)
        // sakila         - name of the database to connect to
        dataSource.setUsername(username);  // Set the username for database authentication
        dataSource.setPassword(password);  // Set the password for database authentication

        // Call each demonstration method to show different CRUD operations
        // Each method demonstrates a different aspect of database interaction
        displayAllFilmsScreen(dataSource);    // READ - show all films
        displayFilmSearchScreen(dataSource);  // READ - search for specific films
        displayCreateFilmScreen(dataSource);  // CREATE - add a new film
        displayUpdateFilmScreen(dataSource);  // UPDATE - modify an existing film
        displayDeleteFilmScreen(dataSource);  // DELETE - remove a film
    }

    /**
     * Demonstrates reading ALL films from the database
     * This method shows how to retrieve and display all records
     * @param dataSource - the database connection pool to use
     */
    private static void displayAllFilmsScreen(BasicDataSource dataSource) {
        // Create a FilmDAO instance to handle database operations
        FilmDAO filmDAO = new FilmDAO(dataSource);

        // Call the DAO's getAll() method to retrieve all films
        // This returns a List<Film> containing all films in the database
        List<Film> films = filmDAO.getAll();

        // Print a formatted header row for our output table
        // %-4s means left-aligned string with minimum width of 4 characters
        // %-40s means left-aligned string with minimum width of 40 characters
        // %10s means right-aligned string with minimum width of 10 characters
        // %n is a platform-independent newline character
        System.out.printf("%-4s %-40s %10s%n", "Id", "Title", "Release Year");

        // Print a separator line to make the table look cleaner
        System.out.println("_________________________________________________________________________________");

        // Enhanced for loop (for-each loop) to iterate through all films
        // This is cleaner than a traditional for loop when you need to process every element
        for (Film film : films) {
            // Print each film's data in the same format as the header
            // %-4d means left-aligned integer with minimum width of 4 characters
            // %-40s means left-aligned string with minimum width of 40 characters
            // %10d means right-aligned integer with minimum width of 10 characters
            System.out.printf("%-4d %-40s %10d%n",
                    film.getFilmId(),      // Get the film's ID
                    film.getTitle(),       // Get the film's title
                    film.getReleaseYear()  // Get the film's release year
            );
        }
    }

    /**
     * Demonstrates searching for films based on user input
     * This method shows how to get user input and perform database searches
     * @param dataSource - the database connection pool to use
     */
    private static void displayFilmSearchScreen(BasicDataSource dataSource) {
        // Create a Scanner object to read input from the console (System.in)
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Search for films that start with: ");

        // Read a line of input from the user and add SQL wildcard character
        // nextLine() reads the entire line including spaces
        // Adding "%" makes it work with SQL LIKE operator for "starts with" searches
        String searchTerm = scanner.nextLine() + "%";

        // Create FilmDAO instance and perform the search
        FilmDAO filmDAO = new FilmDAO(dataSource);
        List<Film> films = filmDAO.search(searchTerm);

        // Print results in the same table format as displayAllFilmsScreen
        System.out.printf("%-4s %-40s %10s%n", "Id", "Title", "Release Year");
        System.out.println("_________________________________________________________________________________");

        // Display each film that matches the search criteria
        for (Film film : films) {
            System.out.printf("%-4d %-40s %10d%n",
                    film.getFilmId(),
                    film.getTitle(),
                    film.getReleaseYear()
            );
        }
    }

    /**
     * Demonstrates creating a new film record in the database
     * This method shows how to INSERT new data
     * @param dataSource - the database connection pool to use
     */
    private static void displayCreateFilmScreen(BasicDataSource dataSource) {
        // Create a new Film object with sample data
        // Constructor parameters: (id, title, releaseYear)
        // ID is set to 0 because the database will auto-generate the actual ID
        Film film = new Film(0, "SPIDER-MAN: NO WAY HOME", 2021);

        // Create FilmDAO instance and insert the new film
        FilmDAO filmDAO = new FilmDAO(dataSource);
        // The create() method returns the same Film object but with the database-generated ID
        film = filmDAO.create(film);

        // Display the newly created film with its database-assigned ID
        System.out.printf("%-4d %-40s %10d%n",
                film.getFilmId(),      // This will now have the database-generated ID
                film.getTitle(),
                film.getReleaseYear()
        );
    }

    /**
     * Demonstrates updating an existing film record in the database
     * This method shows how to UPDATE existing data
     * @param dataSource - the database connection pool to use
     */
    private static void displayUpdateFilmScreen(BasicDataSource dataSource) {
        // Create a Film object with the data we want to update
        // This assumes film with ID 1004 already exists in the database
        Film film = new Film(1004, "SUPER/MAN", 2024);

        // Create FilmDAO instance and update the existing film
        FilmDAO filmDAO = new FilmDAO(dataSource);
        // update() method takes the film ID and the Film object with new data
        // It returns the updated Film object
        film = filmDAO.update(film.getFilmId(), film);

        // Display the updated film information
        System.out.printf("%-4d %-40s %10d%n",
                film.getFilmId(),
                film.getTitle(),
                film.getReleaseYear()
        );
    }

    /**
     * Demonstrates deleting a film record from the database
     * This method shows how to DELETE data
     * @param dataSource - the database connection pool to use
     */
    private static void displayDeleteFilmScreen(BasicDataSource dataSource) {
        // Create FilmDAO instance
        FilmDAO filmDAO = new FilmDAO(dataSource);

        // Delete the film with ID 1005
        // This assumes a film with ID 1005 exists in the database
        filmDAO.delete(1005);

        // Print confirmation message
        // Note: In a real application, you'd want to check if the deletion was successful
        System.out.print("Successfully deleted film.");
    }
}