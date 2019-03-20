package com.cheadtech.popularmovies.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cheadtech.popularmovies.BuildConfig;
import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.models.Review;
import com.cheadtech.popularmovies.models.ReviewResults;
import com.cheadtech.popularmovies.models.Trailer;
import com.cheadtech.popularmovies.models.TrailerResults;
import com.cheadtech.popularmovies.network.ServiceLocator;
import com.cheadtech.popularmovies.network.TMDBService;
import com.cheadtech.popularmovies.room.Favorite;
import com.cheadtech.popularmovies.room.PopularMoviesDB;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private PopularMoviesDB db;

    public MutableLiveData<ArrayList<Trailer>> trailersLD = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Review>> reviewsLD = new MutableLiveData<>();

    public interface DetailViewModelCallback {
        void onNetworkError(int messageResourceStringId);
    }
    private DetailViewModelCallback callback;

    public void init(Movie movie, @NonNull PopularMoviesDB db, DetailViewModelCallback callback) {
        this.db = db;
        this.callback = callback;
        getTrailerList(movie);
        getReviewsList(movie);
    }

    private void getTrailerList(Movie movie) {
        TMDBService tmdbService = ServiceLocator.getInstance().getTMDBService();
        tmdbService.getTrailers(movie.id, BuildConfig.API_KEY).enqueue(new Callback<TrailerResults>() {
            @Override
            public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {
                if (response.code() == 200) {
                    if (response.body() != null && response.body().results != null) {
                        trailersLD.postValue(response.body().results);
                        return;
                    } else {
                        Log.e(DetailViewModel.this.getClass().toString(), " - Network response successful, but a null response was received.");
                    }
                } else if (response.errorBody() != null) {
                    Log.e(DetailViewModel.this.getClass().toString(), response.errorBody().toString());
                } else {
                    Log.e(DetailViewModel.this.getClass().toString(), " - Network response was unsuccessful.");
                }
                callback.onNetworkError(R.string.alert_network_error_trailers);
            }

            @Override
            public void onFailure(Call<TrailerResults> call, Throwable t) {
                Log.e(DetailViewModel.this.getClass().toString(), " - Network call failed: " + t.getMessage());
                callback.onNetworkError(R.string.alert_network_error_trailers);
            }
        });
    }

    private void getReviewsList(Movie movie) {
        TMDBService tmdbService = ServiceLocator.getInstance().getTMDBService();
        tmdbService.getReviews(movie.id, BuildConfig.API_KEY).enqueue(new Callback<ReviewResults>() {
            @Override
            public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                if (response.code() == 200) {
                    if (response.body() != null && response.body().results != null) {
                        reviewsLD.postValue(response.body().results);
                        return;
                    } else {
                        Log.e(DetailViewModel.this.getClass().toString(), " - Network response successful, but a null response was received.");
                    }
                } else if (response.errorBody() != null) {
                    Log.e(DetailViewModel.this.getClass().toString(), response.errorBody().toString());
                } else {
                    Log.e(DetailViewModel.this.getClass().toString(), " - Network response was unsuccessful.");
                }
                callback.onNetworkError(R.string.alert_network_error_reviews);
            }

            @Override
            public void onFailure(Call<ReviewResults> call, Throwable t) {
                Log.e(DetailViewModel.this.getClass().toString(), " - Network call failed: " + t.getMessage());
                callback.onNetworkError(R.string.alert_network_error_reviews);
            }
        });
    }

    public void onFavoriteClicked(final Movie movie) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Favorite favorite = db.popularMoviesDao().getFavorite(movie.id);
                    if (favorite != null)
                        db.popularMoviesDao().delete(favorite);
                    else {
                        Favorite fav = new Favorite();
                        fav.id = movie.id;
                        fav.title = movie.title;
                        fav.originalTitle = movie.original_title;
                        fav.posterPath = movie.poster_path;
                        fav.releaseDate = movie.release_date;
                        fav.voteAverage = movie.vote_average;
                        fav.movieOverview = movie.overview;
                        db.popularMoviesDao().insertAll(fav);
                    }
                } catch (Exception e) {
                    Log.e(DetailViewModel.this.getClass().toString(), e.getMessage());
                }
            }
        }).start();
    }
}
