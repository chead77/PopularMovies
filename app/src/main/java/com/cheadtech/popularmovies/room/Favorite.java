package com.cheadtech.popularmovies.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Favorite {
    @PrimaryKey
    public Integer id;
}
