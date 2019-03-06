package com.cheadtech.popularmovies.models;

import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MovieResults {
    public MovieResults(
            Integer page,
            Integer totalResults,
            Integer totalPages,
            ArrayList<Movie> movies)
    {
        this.page = page;
        this.total_results = totalResults;
        this.total_pages = totalPages;
        this.results = movies;
    }

    public Integer page;
    public Integer total_results;
    public Integer total_pages;
    public ArrayList<Movie> results;
}
