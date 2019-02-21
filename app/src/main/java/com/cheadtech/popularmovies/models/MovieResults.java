package com.cheadtech.popularmovies.models;

import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MovieResults {
    public MovieResults(int totalResults, int totalPages, ArrayList<Movie> movies) {
        page = 1;
        total_results = totalResults;
        total_pages = totalPages;
        results = movies;
    }

    public int page;
    public int total_results;
    public int total_pages;
    public ArrayList<Movie> results;
}
