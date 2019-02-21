package com.cheadtech.popularmovies.network;

import com.cheadtech.popularmovies.models.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBService {
    @GET("3/movie/{sort_by}")
    Call<MovieResults> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);
}
