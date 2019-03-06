package com.cheadtech.popularmovies.models;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Trailer implements Serializable {
    public Trailer(
            String id,
            String iso_639_1,
            String iso_3166_1,
            String key,
            String name,
            String site,
            Integer size,
            String type
    ) {
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }
    public String id;
    public String iso_639_1;
    public String iso_3166_1;
    public String key;
    public String name;
    public String site;
    public Integer size;
    public String type;
}
