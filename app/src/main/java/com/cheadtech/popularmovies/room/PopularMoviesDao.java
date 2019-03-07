package com.cheadtech.popularmovies.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PopularMoviesDao {
    @Query("SELECT * FROM favorite WHERE id = :movieId")
    List<Favorite> getFavorite(Integer movieId);

    @Insert
    void insertAll(Favorite... favorites);

    @Delete
    void delete(Favorite favorite);
}
