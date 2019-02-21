package com.cheadtech.popularmovies.models;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Movie implements Serializable {
    public Movie(
            Integer voteCount,
            Integer id,
            Boolean video,
            Double voteAverage,
            String title,
            Double popularity,
            String posterPath,
            String originalLanguage,
            String originalTitle,
            ArrayList<Integer> genreIds,
            String backdropPath,
            Boolean adult,
            String overview,
            String releaseDate
    ) {
        this.vote_count = voteCount;
        this.id = id;
        this.video = video;
        this.vote_average = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = posterPath;
        this.original_language = originalLanguage;
        this.original_title = originalTitle;
        this.genre_ids = genreIds;
        this.backdrop_path = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.release_date = releaseDate;
    }
    public Integer vote_count;
    public Integer id;
    public Boolean video;
    public Double vote_average;
    public String title;
    public Double popularity;
    public String poster_path;
    public String original_language;
    public String original_title;
    public ArrayList<Integer> genre_ids;
    public String backdrop_path;
    public Boolean adult;
    public String overview;
    public String release_date;
}
