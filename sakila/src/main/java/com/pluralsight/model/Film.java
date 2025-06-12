package com.pluralsight.model;

public class Film {
    private int filmId;
    private String title;
    private int releaseYear;

    public Film(int filmId, String title, int releaseYear) {
        this.filmId = filmId;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public int getFilmId() {
        return filmId;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

}
