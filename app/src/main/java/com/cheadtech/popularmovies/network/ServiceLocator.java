package com.cheadtech.popularmovies.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
// In most of my Android projects, I create a ServiceLocator class to store non-static references to
// individual Retrofit service interfaces. For larger scale applications which connect to different
// endpoints, this provides easy access to those endpoints and handles the singleton logic to create
// generic objects that implement those services.
*/

public class ServiceLocator {
    private static ServiceLocator serviceLocator;

    public static ServiceLocator getInstance() {
        if (serviceLocator == null)
            serviceLocator = new ServiceLocator();
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
