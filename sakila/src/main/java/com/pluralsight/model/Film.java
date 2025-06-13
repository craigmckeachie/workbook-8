// Package declaration - tells Java which folder/namespace this class belongs to
// This follows the convention: com.companyname.projecttype.classtype
package com.pluralsight.model;

/**
 * Film class - This is a MODEL class (also called a POJO - Plain Old Java Object)
 *
 * Purpose: Represents a single film record from the database in our Java application
 * This class follows the "JavaBean" convention with private fields and public getter/setter methods
 *
 * Model classes serve as data containers that:
 * - Hold data retrieved from the database
 * - Transfer data between different layers of the application
 * - Provide a structured way to work with related data
 */
public class Film {

    // PRIVATE INSTANCE VARIABLES (also called "fields" or "attributes")
    // These store the data for each Film object
    // Making them private follows the principle of ENCAPSULATION - 
    // external code cannot directly access or modify these values

    private int filmId;      // Unique identifier for the film (primary key from database)
    private String title;    // The film's title/name
    private int releaseYear; // Year the film was released
    private int languageId;  // Foreign key reference to the language table in database

    // CONSTRUCTOR - special method called when creating a new Film object
    // This is a PARAMETERIZED CONSTRUCTOR (takes parameters)
    // Java automatically provides a no-argument constructor if you don't write one,
    // but once you create a parameterized constructor, you must explicitly create
    // a no-argument constructor if you need one
    /**
     * Constructor for creating a Film object
     * @param filmId - the unique ID of the film
     * @param title - the title of the film
     * @param releaseYear - the year the film was released
     */
    public Film(int filmId, String title, int releaseYear) {
        // 'this' keyword refers to the current object instance
        // It's needed here because parameter names match field names
        // this.filmId refers to the instance variable
        // filmId (without this) refers to the parameter
        this.filmId = filmId;           // Set the film's ID
        this.title = title;             // Set the film's title
        this.releaseYear = releaseYear; // Set the film's release year

        // Set a default value for languageId
        // This assumes language ID 1 exists in the database (probably English)
        // Default values help ensure objects are in a valid state
        this.languageId = 1;
    }

    // GETTER METHODS (also called "accessors")
    // These methods provide READ access to private fields
    // They follow the naming convention: get + FieldName (with first letter capitalized)
    // Getters allow external code to retrieve values without direct field access

    /**
     * Get the film's unique identifier
     * @return int - the film's ID number
     */
    public int getFilmId() {
        return filmId; // Return the value of the private filmId field
    }

    /**
     * Get the film's title
     * @return String - the film's title
     */
    public String getTitle() {
        return title; // Return the value of the private title field
    }

    /**
     * Get the film's release year
     * @return int - the year the film was released
     */
    public int getReleaseYear() {
        return releaseYear; // Return the value of the private releaseYear field
    }

    /**
     * Get the film's language ID
     * @return int - the language ID (foreign key reference)
     */
    public int getLanguageId() {
        return languageId; // Return the value of the private languageId field
    }

    // SETTER METHODS (also called "mutators")
    // These methods provide WRITE access to private fields
    // They follow the naming convention: set + FieldName (with first letter capitalized)
    // Setters allow external code to modify values in a controlled way
    // You could add validation logic here if needed

    /**
     * Set the film's unique identifier
     * @param filmId - the new film ID to assign
     */
    public void setFilmId(int filmId) {
        this.filmId = filmId; // Update the private filmId field with the new value
    }

    /**
     * Set the film's release year
     * @param releaseYear - the new release year to assign
     */
    public void setReleaseYear(int releaseYear) {
        // In a real application, you might add validation here:
        // if (releaseYear < 1888) throw new IllegalArgumentException("Movies weren't invented yet!");
        this.releaseYear = releaseYear; // Update the private releaseYear field
    }

    /**
     * Set the film's title
     * @param title - the new title to assign
     */
    public void setTitle(String title) {
        // In a real application, you might add validation here:
        // if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Title cannot be empty!");
        this.title = title; // Update the private title field
    }

    /**
     * Set the film's language ID
     * @param languageId - the new language ID to assign
     */
    public void setLanguageId(int languageId) {
        this.languageId = languageId; // Update the private languageId field
    }

    // OPTIONAL METHODS (not present in this class but commonly added):

    // toString() method - provides a string representation of the object
    // Useful for debugging and logging
    // Example:
    // @Override
    // public String toString() {
    //     return String.format("Film{id=%d, title='%s', year=%d, languageId=%d}", 
    //                         filmId, title, releaseYear, languageId);
    // }

    // equals() method - defines how to compare two Film objects for equality
    // hashCode() method - defines how to generate hash codes for use in collections
    // These are important if you plan to store Film objects in Sets or use them as Map keys
}