package com.cheadtech.popularmovies.room;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.cheadtech.popularmovies.R;

public class DatabaseLoader {
    private static PopularMoviesDB dbInstance;
    public static PopularMoviesDB getDbInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context,
                    PopularMoviesDB.class, context.getString(R.string.app_db_name)).build();
        }
        return dbInstance;
    }
}
