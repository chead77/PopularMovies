package com.cheadtech.popularmovies.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.room.Favorite;
import com.cheadtech.popularmovies.room.PopularMoviesDB;
import com.cheadtech.popularmovies.room.PopularMoviesDao;

import java.util.List;

public class DetailViewModel extends ViewModel {
    public MutableLiveData<Boolean> favorite = new MutableLiveData<>();

    private PopularMoviesDao pmDao;

    public void init(PopularMoviesDB db, Movie movie) {
        if (db != null) {
            pmDao = db.popularMoviesDao();
            checkForFavorite(movie.id);
        } else
            // in a production app, I would use an interface to tell the activity or fragment to notify the user
            // for the purposes of this app, simply logging the error should suffice
            Log.e(getClass().toString(), "Database not found");
    }

    private void checkForFavorite(final Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    favorite.postValue(pmDao.getFavorite(id).size() > 0);
                } catch (Exception e) {
                    Log.e(DetailViewModel.this.getClass().toString(), e.getMessage());
                    // in a production app, I would use an interface to tell the activity or fragment to notify the user
                    // for the purposes of this app, simply posting false should suffice
                    favorite.postValue(false);
                }
            }
        }).start();
    }

    public void onFavoriteClicked(final Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Favorite> favorites = pmDao.getFavorite(id);
                    if (favorites.size() > 0) {
                        pmDao.delete(favorites.get(0));
                        favorite.postValue(false);
                    } else {
                        Favorite fav = new Favorite();
                        fav.id = id;
                        pmDao.insertAll(fav);
                        favorite.postValue(true);
                    }
                } catch (Exception e) {
                    Log.e(DetailViewModel.this.getClass().toString(), e.getMessage());
                    // in a production app, I would use an interface to tell the activity or fragment to notify the user
                    // for the purposes of this app, simply posting false should suffice
                    favorite.postValue(false);
                }
            }
        }).start();
    }
}
