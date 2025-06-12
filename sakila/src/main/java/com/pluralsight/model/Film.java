package com.pluralsight.model;

public class Film {
    private int filmId;
    private String title;
    private int releaseYear;
    private int languageId;



    public Film(int filmId, String title, int releaseYear) {
        this.filmId = filmId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.languageId = 1;
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

    public int getLanguageId() {
        return languageId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

}
