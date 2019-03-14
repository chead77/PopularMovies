package com.cheadtech.popularmovies.network;

import com.cheadtech.popularmovies.models.MovieResults;
import com.cheadtech.popularmovies.models.ReviewResults;
import com.cheadtech.popularmovies.models.TrailerResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBService {
    @GET("3/movie/{sort_by}")
    Call<MovieResults> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<TrailerResults> getTrailers(@Path("id") Integer id, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<ReviewResults> getReviews(@Path("id") Integer id, @Query("api_key") String apiKey);
}
