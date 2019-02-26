package com.cheadtech.popularmovies.activities;

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
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.adapters.PostersAdapter;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.network.NetworkUtils;
import com.cheadtech.popularmovies.viewmodels.PostersViewModel;

import java.util.ArrayList;

public class PostersActivity extends AppCompatActivity
        implements PostersAdapter.PostersAdapterCallback, PostersViewModel.PostersViewModelCallback, SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView postersRV;
    private PostersViewModel viewModel;

    private static final Integer numCols = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);

        viewModel = ViewModelProviders.of(this).get(PostersViewModel.class);

        postersRV = findViewById(R.id.posters);
        if (postersRV != null) {
            postersRV.setLayoutManager(new GridLayoutManager(this, numCols));
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            postersRV.setAdapter(new PostersAdapter(new ArrayList<Movie>(),
                    NetworkUtils.buildPosterUrlBase(metrics.widthPixels / numCols), this));
        }

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
                this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
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


    /*
    Everything from here down could be moved to a fragment
     */

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
        // might not finish() here in a production app, but this exits cleanly if the list of movies can't be refreshed
        Toast.makeText(this, getString(R.string.alert_network_error), Toast.LENGTH_LONG).show();
        finish();
    }

    // PostersAdapter.PostersAdapterCallback method
    @Override
    public void onPosterClicked(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(getString(R.string.extra_movie), movie);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        intent.putExtra(getString(R.string.extra_poster_url),
                NetworkUtils.buildPosterUrlBase(metrics.widthPixels / 2) + movie.poster_path);
        startActivity(intent);
    }
}
