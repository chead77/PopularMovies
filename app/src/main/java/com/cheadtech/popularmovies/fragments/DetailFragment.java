package com.cheadtech.popularmovies.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.viewmodels.DetailViewModel;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
    private final String tag = getClass().toString();

    private ImageView poster;
    private TextView originalTitle;
    private TextView synopsis;
    private TextView userRating;
    private TextView releaseDate;

//    private DetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        poster = view.findViewById(R.id.poster_thumbnail);
        originalTitle = view.findViewById(R.id.original_title);
        synopsis = view.findViewById(R.id.synopsis);
        userRating = view.findViewById(R.id.user_rating);
        releaseDate = view.findViewById(R.id.release_date);
        Activity activity = getActivity();

        if (activity != null && poster != null && originalTitle != null && synopsis != null
                && userRating != null && releaseDate != null) {
            Movie movie = (Movie) activity.getIntent().getSerializableExtra(getString(R.string.extra_movie));
            String posterUrl = "";
            if (activity.getIntent().hasExtra(getString(R.string.extra_poster_url))) {
                posterUrl = activity.getIntent().getStringExtra(getString(R.string.extra_poster_url));
            }

            if ((poster != null) && (releaseDate != null)
                    && (originalTitle != null) && (synopsis != null) && (userRating != null)) {
                Picasso.get()
                        .load(posterUrl)
                        .placeholder(android.R.drawable.stat_notify_error)
                        .error(android.R.drawable.stat_notify_error)
                        .into(poster);
                originalTitle.setText(movie.original_title);
                synopsis.setText(movie.overview);
                userRating.setText(movie.vote_average.toString());
                releaseDate.setText(movie.release_date);
            }
        }

//        viewModel.init();
    }
}
