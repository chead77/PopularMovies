package com.cheadtech.popularmovies.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.adapters.ReviewsAdapter;
import com.cheadtech.popularmovies.adapters.TrailersAdapter;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.models.Review;
import com.cheadtech.popularmovies.models.Trailer;
import com.cheadtech.popularmovies.room.DatabaseLoader;
import com.cheadtech.popularmovies.room.Favorite;
import com.cheadtech.popularmovies.room.PopularMoviesDB;
import com.cheadtech.popularmovies.viewmodels.DetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailFragment extends Fragment {
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
        CollapsingToolbarLayout toolbar = view.findViewById(R.id.collapsing_toolbar);
        ImageView poster = view.findViewById(R.id.poster_thumbnail);
        TextView synopsis = view.findViewById(R.id.synopsis);
        TextView userRating = view.findViewById(R.id.user_rating);
        TextView releaseDate = view.findViewById(R.id.release_date);
        ImageButton favorite = view.findViewById(R.id.favorite);
        final RecyclerView trailersRV = view.findViewById(R.id.trailers);
        final RecyclerView reviewsRV = view.findViewById(R.id.reviews);

        if (activity != null && poster != null &&  synopsis != null && toolbar != null && reviewsRV != null
                && userRating != null && releaseDate != null && trailersRV != null && favorite != null) {

            final Movie movie = (Movie) activity.getIntent().getSerializableExtra(getString(R.string.extra_movie));

            toolbar.setTitle(movie.title);

            String posterUrl = "";
            if (activity.getIntent().hasExtra(getString(R.string.extra_poster_url))) {
                posterUrl = activity.getIntent().getStringExtra(getString(R.string.extra_poster_url));
            } else {
                Log.e(getClass().toString(), "poster URL not provided in fragment extras");
            }
            Picasso.get().load(posterUrl)
                    .placeholder(R.drawable.ic_hourglass_empty_black)
                    .error(R.drawable.ic_error_outline_black)
                    .into(poster);

            synopsis.setText(movie.overview);
            userRating.setText(movie.vote_average.toString() + "/10");
            releaseDate.setText(movie.release_date.substring(0,4));
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { viewModel.onFavoriteClicked(movie); }
            });

            // RecyclerView adapters and observers
            trailersRV.setAdapter(new TrailersAdapter());
            trailersRV.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
            viewModel.trailersLD.observe(this, new Observer<ArrayList<Trailer>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Trailer> trailers) {
                    TrailersAdapter adapter = (TrailersAdapter) trailersRV.getAdapter();
                    if (adapter != null && trailers != null)
                        adapter.setData(trailers);
                }
            });
            reviewsRV.setAdapter(new ReviewsAdapter());
            reviewsRV.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
            viewModel.reviewsLD.observe(this, new Observer<ArrayList<Review>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Review> reviews) {
                    ReviewsAdapter adapter = (ReviewsAdapter) reviewsRV.getAdapter();
                    if (adapter != null && reviews != null)
                        adapter.setData(reviews);
                }
            });

            PopularMoviesDB db = DatabaseLoader.getDbInstance(activity.getApplicationContext());
            viewModel.init(movie, db, new DetailViewModel.DetailViewModelCallback() {
                @Override
                public void onNetworkError(int messageResourceStringId) {
                    Toast.makeText(getContext(), getString(messageResourceStringId), Toast.LENGTH_LONG).show();
                }
            });
            // observe the favorites table in case someone removes a favorite
            db.popularMoviesDao().getLiveFavorite(movie.id).observe(this, new Observer<Favorite>() {
                @Override
                public void onChanged(@Nullable Favorite favorite) { setFavoriteButtonState(favorite != null); }
            });
        }
    }

    private void setFavoriteButtonState(Boolean fav) {
        Activity activity = getActivity();
        if (activity != null) {
            ImageButton favoriteButton = activity.findViewById(R.id.favorite);
            if (fav) {
                favoriteButton.setImageDrawable(activity.getDrawable(android.R.drawable.star_big_on));
            } else {
                favoriteButton.setImageDrawable(activity.getDrawable(android.R.drawable.star_big_off));
            }
        }
    }
}
