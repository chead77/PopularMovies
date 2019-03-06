package com.cheadtech.popularmovies.models;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Review implements Serializable {
    public Review(
            String author,
            String content,
            String id,
            String url
    ) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }
    public String author;
    public String content;
    public String id;
    public String url;
}
