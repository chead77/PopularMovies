package com.cheadtech.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cheadtech.popularmovies.adapters.PostersAdapter;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.viewmodels.PostersViewModel;

import java.util.ArrayList;

public class PostersActivity extends AppCompatActivity implements PostersAdapter.PostersAdapterCallback, PostersViewModel.PostersViewModelCallback, SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView postersRV;
    private PostersViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);

        postersRV = findViewById(R.id.posters);
        if (postersRV != null) {
            postersRV.setLayoutManager(new GridLayoutManager(this, 2));
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            postersRV.setAdapter(new PostersAdapter(new ArrayList<Movie>(), size.x, this));
        }

        viewModel = ViewModelProviders.of(this).get(PostersViewModel.class);
        viewModel.moviesLiveData.observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                updateMoviesAdapter(movies);
            }
        });

        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        viewModel.init(
                prefs.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_popular_value)),
                this
        );
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
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
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(getString(R.string.extra_movie), movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.posters_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, PostersSettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.pref_sort_by_key)))
            viewModel.refreshMovieList(prefs.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_popular_value)));
    }
}
