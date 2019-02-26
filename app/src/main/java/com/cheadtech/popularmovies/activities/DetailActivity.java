package com.cheadtech.popularmovies.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Movie movie = (Movie) getIntent().getSerializableExtra(getString(R.string.extra_movie));
        String posterUrl = "";
        if (getIntent().hasExtra(getString(R.string.extra_poster_url))) {
            posterUrl = getIntent().getStringExtra(getString(R.string.extra_poster_url));
        }

        ImageView posterThumb = findViewById(R.id.poster_thumbnail);
        TextView originalTitle = findViewById(R.id.original_title);
        TextView synopsis = findViewById(R.id.synopsis);
        TextView userRating = findViewById(R.id.user_rating);
        TextView releaseDate = findViewById(R.id.release_date);

        if ((posterThumb != null) && (releaseDate != null)
                && (originalTitle != null) && (synopsis != null) && (userRating != null)) {
            Picasso.get()
                    .load(posterUrl)
                    .placeholder(android.R.drawable.stat_notify_error)
                    .error(android.R.drawable.stat_notify_error)
                    .into(posterThumb);
            originalTitle.setText(movie.original_title);
            synopsis.setText(movie.overview);
            userRating.setText(movie.vote_average.toString());
            releaseDate.setText(movie.release_date);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
