package com.cheadtech.popularmovies.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.models.MovieResults;
import com.cheadtech.popularmovies.network.ServiceLocator;
import com.cheadtech.popularmovies.restricted_values.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostersViewModel extends ViewModel {
    private final String tag = getClass().toString();
    public MutableLiveData<ArrayList<Movie>> moviesLiveData = new MutableLiveData<>();

    public interface PostersViewModelCallback {
        void onNetworkError();
    }
    private PostersViewModelCallback callback;
    public void setPostersViewModelCallback(PostersViewModelCallback newCallback) {
        callback = newCallback;
    }

    public void init() {
        getMovieList();
    }

    private void getMovieList() {
        // Constants.API_KEY is a String constant stored in a file that will not be included in the Github repo.
        // To use this project, a new API key will need to be obtained from https://www.themoviedb.org/account/signup
        ServiceLocator.getInstance().getTMDBService().getPopularMovies(Constants.API_KEY).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        moviesLiveData.postValue(response.body().results);
                    } else {
                        Log.e(tag, " - Network response was successful, but response body was empty.");
                        callback.onNetworkError();
                    }
                } else {
                    Log.e(tag, " - Network response was unsuccessful.");
                    callback.onNetworkError();
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.e(tag, " - Network call failed.");
                callback.onNetworkError();
            }
        });
    }
}
