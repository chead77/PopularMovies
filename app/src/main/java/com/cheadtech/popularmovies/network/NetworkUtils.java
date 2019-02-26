package com.cheadtech.popularmovies.network;

public class NetworkUtils {
    /*
    Sets up the poster image URL prefix, plus the path of the poster size needed. Since the grid is 2 columns wide
    with no margins or padding, find the size of the screen and divide by 2, then compare that size to the average
    between two consecutive remote image sizes to determine which size to use in the image URL. This URL path will
    be concatenated with the poster image name to produce the full url string for the poster image. The thumbnail
    on the detail screen will also use this path
     */
    public static String buildPosterUrlBase(Integer width) {
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
        else if (width <= 920)
            url = url.concat("w780");
        else
            url = url.concat("original");
        url = url.concat("/");
        return url;
    }
}
