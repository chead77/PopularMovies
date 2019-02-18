package com.cheadtech.popularmovies.models;

import java.util.ArrayList;

public class Movie {
    public Movie(int totalResults, ArrayList<Result> results) {
        this.totalResults = totalResults;
        this.results = results;
    }

    public Integer totalResults;
    public ArrayList<Result> results;

    public static class Result {
        public Result(
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
            this.voteCount = voteCount;
            this.id = id;
            this.video = video;
            this.voteAverage = voteAverage;
            this.title = title;
            this.popularity = popularity;
            this.posterPath = posterPath;
            this.originalLanguage = originalLanguage;
            this.originalTitle = originalTitle;
            this.genreIds = genreIds;
            this.backdropPath = backdropPath;
            this.adult = adult;
            this.overview = overview;
            this.releaseDate = releaseDate;
        }
        public Integer voteCount;
        public Integer id;
        public Boolean video;
        public Double voteAverage;
        public String title;
        public Double popularity;
        public String posterPath;
        public String originalLanguage;
        public String originalTitle;
        public ArrayList<Integer> genreIds;
        public String backdropPath;
        public Boolean adult;
        public String overview;
        public String releaseDate;
    }
}
