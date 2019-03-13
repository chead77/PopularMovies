package com.cheadtech.popularmovies.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.models.Trailer;
import com.cheadtech.popularmovies.models.TrailerResults;
import com.cheadtech.popularmovies.network.ServiceLocator;
import com.cheadtech.popularmovies.network.TMDBService;
import com.cheadtech.popularmovies.restricted_values.PopularMoviesConstants;
import com.cheadtech.popularmovies.room.Favorite;
import com.cheadtech.popularmovies.room.PopularMoviesDB;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private PopularMoviesDB db;

    public MutableLiveData<ArrayList<Trailer>> trailersLD = new MutableLiveData<>();

    public interface DetailViewModelCallback {
        void onNetworkError(int messageResourceStringId);
    }
    private DetailViewModelCallback callback;

    public void init(Movie movie, @NonNull PopularMoviesDB db, DetailViewModelCallback callback) {
        this.db = db;
        this.callback = callback;
        getTrailerList(movie);
    }

    private void getTrailerList(Movie movie) {
        // PopularMoviesConstants.API_KEY is a String constant stored in a file that will not be included in the Github repo.
        // To use this project, a new API key will need to be obtained from https://www.themoviedb.org/account/signup
        TMDBService tmdbService = ServiceLocator.getInstance().getTMDBService();
        tmdbService.getTrailers(movie.id, PopularMoviesConstants.API_KEY).enqueue(new Callback<TrailerResults>() {
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

    public void onFavoriteClicked(final Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Favorite favorite = db.popularMoviesDao().getFavorite(id);
                    if (favorite != null)
                        db.popularMoviesDao().delete(favorite);
                    else {
                        Favorite fav = new Favorite();
                        fav.id = id;
                        db.popularMoviesDao().insertAll(fav);
                    }
                } catch (Exception e) {
                    Log.e(DetailViewModel.this.getClass().toString(), e.getMessage());
                }
            }
        }).start();
    }
}
