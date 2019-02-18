package com.cheadtech.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cheadtech.popularmovies.adapters.PostersAdapter;
import com.cheadtech.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostersActivity extends AppCompatActivity implements PostersAdapter.PostersAdapterCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);

        RecyclerView postersRV = findViewById(R.id.posters);
        if (postersRV != null) {
            postersRV.setLayoutManager(new GridLayoutManager(this, 2));
            ArrayList<Movie> data = new ArrayList<>();
            ArrayList<Movie.Result> movies = new ArrayList<>();
            movies.add(new Movie.Result(1, 1, false, 1.1, "title", 1.1, "", "", "", new ArrayList<Integer>(), "", false, "", ""));
            data.add(new Movie(12, movies));
//            postersRV.setAdapter(new PostersAdapter(data, this));
        }

    }

    // PostersAdapter.PostersAdapterCallback methods
    @Override
    public void onPosterClicked(Movie.Result movie) {
        Toast.makeText(this, movie.title + " Clicked", Toast.LENGTH_LONG).show();
    }
}
