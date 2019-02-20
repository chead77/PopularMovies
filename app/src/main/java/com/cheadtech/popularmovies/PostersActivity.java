package com.cheadtech.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cheadtech.popularmovies.adapters.PostersAdapter;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.viewmodels.PostersViewModel;

import java.util.ArrayList;

public class PostersActivity extends AppCompatActivity implements PostersAdapter.PostersAdapterCallback, PostersViewModel.PostersViewModelCallback {
    RecyclerView postersRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);

        PostersViewModel viewModel = ViewModelProviders.of(this).get(PostersViewModel.class);

        postersRV = findViewById(R.id.posters);
        if (postersRV != null) {
            postersRV.setLayoutManager(new GridLayoutManager(this, 2));
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            postersRV.setAdapter(new PostersAdapter(new ArrayList<Movie>(), size.x, this));
        }

        viewModel.moviesLiveData.observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                updateMoviesAdapter(movies);
            }
        });
        viewModel.init();
        viewModel.setPostersViewModelCallback(this);
    }

    private void updateMoviesAdapter(ArrayList<Movie> movies) {
        if (postersRV != null) {
            PostersAdapter adapter = (PostersAdapter) postersRV.getAdapter();
            if (adapter != null)
                adapter.setData(movies);
        }
    }

    // PostersViewModel.PostersViewModelCallback method
    @Override
    public void onNetworkError() {
        Toast.makeText(this, "", Toast.LENGTH_LONG).show();
    }

    // PostersAdapter.PostersAdapterCallback method
    @Override
    public void onPosterClicked(Movie movie) {
        // TODO - open the detail activity
        Toast.makeText(this, movie.title + " Clicked", Toast.LENGTH_LONG).show();
    }
}
