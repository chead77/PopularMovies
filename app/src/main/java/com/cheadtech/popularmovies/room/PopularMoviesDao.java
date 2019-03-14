package com.cheadtech.popularmovies.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PopularMoviesDao {
    @Query("SELECT * FROM favorite")
    List<Favorite> getAllFavorites();

    @Query("SELECT * FROM favorite")
    LiveData<List<Favorite>> getAllFavoritesLive();

    @Query("SELECT * FROM favorite WHERE id = :movieId")
    Favorite getFavorite(Integer movieId);

    @Query("SELECT * FROM favorite WHERE id = :movieId")
    LiveData<Favorite> getLiveFavorite(Integer movieId);

    @Insert
    void insertAll(Favorite... favorites);

    @Delete
    void delete(Favorite favorite);
}
