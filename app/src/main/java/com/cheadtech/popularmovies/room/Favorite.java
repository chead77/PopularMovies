package com.cheadtech.popularmovies.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Favorite {
    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "original_title")
    public String originalTitle;

    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @ColumnInfo(name = "vote_average")
    public Double voteAverage;

    @ColumnInfo(name = "movie_overview")
    public String movieOverview;

}
