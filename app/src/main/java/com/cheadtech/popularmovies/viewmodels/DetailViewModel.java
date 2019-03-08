package com.cheadtech.popularmovies.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.cheadtech.popularmovies.room.Favorite;
import com.cheadtech.popularmovies.room.PopularMoviesDB;
import com.cheadtech.popularmovies.room.PopularMoviesDao;

public class DetailViewModel extends ViewModel {
    private PopularMoviesDao pmDao;

    public void init(PopularMoviesDB db) {
        if (db != null) {
            pmDao = db.popularMoviesDao();
        } else
            // in a production app, I would use an interface to tell the activity or fragment to notify the user
            // for the purposes of this app, simply logging the error should suffice
            Log.e(getClass().toString(), "Database not found");
    }

    public void onFavoriteClicked(final Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Favorite favorite = pmDao.getFavorite(id);
                    if (favorite != null)
                        pmDao.delete(favorite);
                    else {
                        Favorite fav = new Favorite();
                        fav.id = id;
                        pmDao.insertAll(fav);
                    }
                } catch (Exception e) {
                    Log.e(DetailViewModel.this.getClass().toString(), e.getMessage());
                }
            }
        }).start();
    }
}
