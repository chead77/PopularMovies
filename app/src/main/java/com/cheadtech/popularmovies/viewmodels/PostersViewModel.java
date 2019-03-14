package com.cheadtech.popularmovies.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.models.MovieResults;
import com.cheadtech.popularmovies.network.ServiceLocator;
import com.cheadtech.popularmovies.network.TMDBService;
import com.cheadtech.popularmovies.restricted_values.PopularMoviesConstants;
import com.cheadtech.popularmovies.room.Favorite;
import com.cheadtech.popularmovies.room.PopularMoviesDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostersViewModel extends ViewModel {
    private final String tag = getClass().toString();
    public MutableLiveData<ArrayList<Movie>> moviesLiveData = new MutableLiveData<>();

    private PopularMoviesDB db;

    public interface PostersViewModelCallback {
        void onNetworkError();
        void onEmptyFavorites();
        void onDBError();
    }
    private PostersViewModelCallback callback;

    public void init(String sortBy, PopularMoviesDB dbInstance, PostersViewModelCallback callback) {
        this.callback = callback;
        db = dbInstance;
        refreshMovieList(sortBy);
    }

    public void refreshMovieList(String sortBy) {
        getMovieList(sortBy);
    }

    private void getMovieList(String sortBy) {
        // PopularMoviesConstants.API_KEY is a String constant stored in a file that will not be included in the Github repo.
        // To use this project, a new API key will need to be obtained from https://www.themoviedb.org/account/signup
        if (sortBy.equals("popular") || sortBy.equals("top_rated")) {
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
        } else if (sortBy.equals("favorites")) {
            sortByFavorites();
        }
    }

    private void sortByFavorites() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Favorite> favorites = db.popularMoviesDao().getAllFavorites();
                    sortByFavorites(favorites);
                } catch (Exception e) {
                    Log.e(tag, e.getMessage());
                    callback.onDBError();
                }
            }
        }).start();
    }

    public void sortByFavorites(List<Favorite> favorites) {
        if (favorites != null) {
            if (favorites.size() == 0) {
                Log.w(tag, "No favorites selected");
                callback.onEmptyFavorites();
            }
            ArrayList<Movie> movies = new ArrayList<>();
            for (int i = 0 ; i < favorites.size() ; i++) {
                Favorite favorite = favorites.get(i);
                movies.add(new Movie(0, favorite.id, true, favorite.voteAverage,
                        favorite.title, 0.0, favorite.posterPath, null,
                        favorite.originalTitle, new ArrayList<Integer>(), null,
                        false, favorite.movieOverview, favorite.releaseDate));
            }
            moviesLiveData.postValue(movies);
        }
    }
}
