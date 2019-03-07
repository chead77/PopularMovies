package com.cheadtech.popularmovies.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.room.DatabaseLoader;
import com.cheadtech.popularmovies.viewmodels.DetailViewModel;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
    private Button favorite;

    private DetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        Activity activity = getActivity();
        ImageView poster = view.findViewById(R.id.poster_thumbnail);
        TextView originalTitle = view.findViewById(R.id.original_title);
        TextView synopsis = view.findViewById(R.id.synopsis);
        TextView userRating = view.findViewById(R.id.user_rating);
        TextView releaseDate = view.findViewById(R.id.release_date);
        favorite = view.findViewById(R.id.favorite);

        if (activity != null && poster != null && originalTitle != null && synopsis != null
                && userRating != null && releaseDate != null && favorite != null) {

            final Movie movie = (Movie) activity.getIntent().getSerializableExtra(getString(R.string.extra_movie));

            String posterUrl = "";
            if (activity.getIntent().hasExtra(getString(R.string.extra_poster_url))) {
                posterUrl = activity.getIntent().getStringExtra(getString(R.string.extra_poster_url));
            } else {
                Log.e(getClass().toString(), "poster URL not provided");
            }

            Picasso.get()
                    .load(posterUrl)
                    .placeholder(android.R.drawable.stat_notify_error)
                    .error(android.R.drawable.stat_notify_error)
                    .into(poster);
            originalTitle.setText(movie.original_title);
            synopsis.setText(movie.overview);
            userRating.setText(movie.vote_average.toString());
            releaseDate.setText(movie.release_date);
            viewModel.favorite.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean fav) { setFavoriteButtonState(fav); }
            });
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { viewModel.onFavoriteClicked(movie.id); }
            });

            viewModel.init(DatabaseLoader.getDbInstance(activity.getApplicationContext()), movie);
        }
    }

    private void setFavoriteButtonState(Boolean fav) {
        Activity activity = getActivity();
        // TODO - review and update as needed
        if (activity != null) {
            if (fav) {
                favorite.setBackground(activity.getDrawable(R.color.colorAccent));
            } else {
                favorite.setBackground(activity.getDrawable(R.color.colorPrimary));
            }
        }
    }
}
