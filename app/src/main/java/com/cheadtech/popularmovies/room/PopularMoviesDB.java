package com.cheadtech.popularmovies.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1)
public abstract class PopularMoviesDB extends RoomDatabase {
    public abstract PopularMoviesDao popularMoviesDao();
}
