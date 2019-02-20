package com.cheadtech.popularmovies.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {
    private static ServiceLocator serviceLocator;

    public static ServiceLocator getInstance() {
        if (serviceLocator == null)
            return new ServiceLocator();
        else
            return serviceLocator;
    }

    private TMDBService tmdbService;
    public TMDBService getTMDBService() {
        if (tmdbService == null) {
            tmdbService = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TMDBService.class);
        }
        return tmdbService;
    }
}
