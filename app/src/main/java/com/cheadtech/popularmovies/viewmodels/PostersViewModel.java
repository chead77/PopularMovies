package com.cheadtech.popularmovies.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Point;
import android.util.Log;

import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.models.MovieResults;
import com.cheadtech.popularmovies.network.ServiceLocator;
import com.cheadtech.popularmovies.network.TMDBService;
import com.cheadtech.popularmovies.restricted_values.PopularMoviesConstants;

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

    public void init(String sortBy, PostersViewModelCallback callback) {
        this.callback = callback;
        refreshMovieList(sortBy);
    }

    public void refreshMovieList(String sortBy) {
        getMovieList(sortBy);
    }

    private void getMovieList(String sortBy) {
        // PopularMoviesConstants.API_KEY is a String constant stored in a file that will not be included in the Github repo.
        // To use this project, a new API key will need to be obtained from https://www.themoviedb.org/account/signup
        TMDBService tmdbService = ServiceLocator.getInstance().getTMDBService();
        tmdbService.getMovies(sortBy, PopularMoviesConstants.API_KEY).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if (response.code() == 200) {
                    if (response.body() != null && response.body().results != null) {
                        moviesLiveData.postValue(response.body().results);
                        return;
                    } else {
                        Log.e(tag, " - Network response successful, but a null response was received.");
                    }
                } else if (response.errorBody() != null) {
                    Log.e(tag, response.errorBody().toString());
                } else {
                    Log.e(tag, " - Network response was unsuccessful.");
                }
                callback.onNetworkError();
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.e(tag, " - Network call failed: " + t.getMessage());
                callback.onNetworkError();
            }
        });
    }

    /*
    Sets up the poster image URL prefix, plus the path of the poster size needed. Since the grid is 2 columns wide
    with no margins or padding, find the size of the screen and divide by 2, then compare that size to the average
    between two consecutive remote image sizes to determine which size to use in the image URL. This URL path will
    be concatenated with the poster image name to produce the full url string for the poster image. The thumbnail
    on the detail screen will also use this path
     */
    public String buildPosterUrlBase(Integer width) {
        String url = "http://image.tmdb.org/t/p/";
        if (width < (92 + 154) / 2)
            url = url.concat("w92");
        else if (width < (154 + 185) / 2)
            url = url.concat("w154");
        else if (width < (185 + 342) / 2)
            url = url.concat("w185");
        else if (width < (342 + 500) / 2)
            url = url.concat("w342");
        else if (width < (500 + 780) / 2)
            url = url.concat("w500");
        else if (width <= 780)
            url = url.concat("w780");
        else
            url = url.concat("original");
        url = url.concat("/");
        return url;
    }
}
