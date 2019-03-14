package com.cheadtech.popularmovies.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.activities.DetailActivity;
import com.cheadtech.popularmovies.adapters.PostersAdapter;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.network.NetworkUtils;
import com.cheadtech.popularmovies.room.DatabaseLoader;
import com.cheadtech.popularmovies.room.Favorite;
import com.cheadtech.popularmovies.viewmodels.PostersViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostersFragment extends Fragment implements PostersAdapter.PostersAdapterCallback, PostersViewModel.PostersViewModelCallback, SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView postersRV;
    private PostersViewModel viewModel;

    private static final Integer numCols = 2;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_posters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PostersViewModel.class);

        postersRV = view.findViewById(R.id.posters);
        if (postersRV != null) {
            postersRV.setLayoutManager(new GridLayoutManager(view.getContext(), numCols));
            DisplayMetrics metrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            postersRV.setAdapter(new PostersAdapter(new ArrayList<Movie>(),
                    NetworkUtils.buildPosterUrlBase(metrics.widthPixels / numCols), this));
        }

        viewModel.moviesLiveData.observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                updateMoviesAdapter(movies);
            }
        });

        // TODO - leaving this here for now because it needs a lifecycle owner for the observe function... look into moving this and the retrofit logic in the viewmodels to a repository layer
        DatabaseLoader.getDbInstance(getContext()).popularMoviesDao().getAllFavoritesLive().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                if (sharedPreferences.contains(getString(R.string.pref_sort_by_key))) {
                    String sort = sharedPreferences.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_popular_value));
                    if (sort != null && sort.equals(getString(R.string.pref_sort_by_favorites_value))) {
                        viewModel.sortByFavorites(favorites);
                    }
                }
            }
        });

        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        viewModel.init(
                sharedPreferences.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_popular_value)),
                DatabaseLoader.getDbInstance(getContext()),
                this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sharedPreferences != null)
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
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
        Toast.makeText(requireContext(), getString(R.string.alert_network_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEmptyFavorites() {
        // had to surround this toast in a post() to avoid triggering a runtime error when the app initially loads.
        // The Toast may have been firing off before the fragment was fully loaded and displayed
        if (postersRV != null) {
            postersRV.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(requireContext(), getString(R.string.error_no_favorites_found), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onDBError() {
        Toast.makeText(requireContext(), getString(R.string.error_database), Toast.LENGTH_LONG).show();
    }

    // PostersAdapter.PostersAdapterCallback method
    @Override
    public void onPosterClicked(Movie movie) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra(getString(R.string.extra_movie), movie);
        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        intent.putExtra(getString(R.string.extra_poster_url),
                NetworkUtils.buildPosterUrlBase(metrics.widthPixels / 2) + movie.poster_path);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_by_key)))
            viewModel.refreshMovieList(sharedPreferences.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_popular_value)));
    }
}
