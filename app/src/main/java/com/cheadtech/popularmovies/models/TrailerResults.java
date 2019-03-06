package com.cheadtech.popularmovies.models;

import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TrailerResults {
    public TrailerResults(int id, ArrayList<Trailer> results) {
        this.results = results;
    }

    public int id;
    public ArrayList<Trailer> results;
}
