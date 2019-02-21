package com.cheadtech.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cheadtech.popularmovies.models.Movie;

public class DetailActivity extends AppCompatActivity {
    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie = (Movie) getIntent().getSerializableExtra(getString(R.string.extra_movie));
    }
}
