package com.cheadtech.popularmovies.models;

import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ReviewResults {
    public ReviewResults(Integer id, Integer page, ArrayList<Review> results, Integer total_pages, Integer total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public Integer id;
    public Integer page;
    public ArrayList<Review> results;
    public Integer total_pages;
    public Integer total_results;
}
