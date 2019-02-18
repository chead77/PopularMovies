package com.cheadtech.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Movie;
import com.cheadtech.popularmovies.viewholders.PosterViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostersAdapter extends RecyclerView.Adapter<PosterViewHolder> {
    public PostersAdapter(ArrayList<Movie.Result> data, PostersAdapterCallback callback) {
        dataSet = data;
        this.callback = callback;
    }

    public interface PostersAdapterCallback {
        void onPosterClicked(Movie.Result movie);
    }
    private PostersAdapterCallback callback;

    private ArrayList<Movie.Result> dataSet;
    public void setData(ArrayList<Movie.Result> data) { dataSet = data; }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new PosterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_item, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull final PosterViewHolder holder, int position) {
        ImageView poster = holder.poster;
        if (poster != null) {
            Picasso.get().load("http://image.tmdb.org/t/p/w500/" + dataSet.get(position).posterPath).into(poster); // TODO
            poster.setContentDescription(poster.getContext()
                    .getString(R.string.movie_poster_content_description) + " - " + dataSet.get(position)); // TODO - remember to get the movie title once the model is built
            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("PostersActivity:onBindViewHolder.onClick", " - item at position " + holder.getAdapterPosition() + " clicked");
                    callback.onPosterClicked(dataSet.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
