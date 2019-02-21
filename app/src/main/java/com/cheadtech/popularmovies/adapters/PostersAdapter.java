package com.cheadtech.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private String posterPathPrefix;

    public PostersAdapter(ArrayList<Movie> data, int screenWidth, PostersAdapterCallback callback) {
        posterPathPrefix = buildPosterPathPrefix(screenWidth / 2) + "/";
        dataSet = data;
        this.callback = callback;
    }

    public interface PostersAdapterCallback {
        void onPosterClicked(Movie movie);
    }
    private PostersAdapterCallback callback;

    private ArrayList<Movie> dataSet;
    public void setData(ArrayList<Movie> data) {
        dataSet = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new PosterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PosterViewHolder holder, int position) {
        ImageView poster = holder.poster;
        if (poster != null) {
            Picasso.get().load(posterPathPrefix + dataSet.get(position).poster_path).into(poster);
            poster.setContentDescription(poster.getContext()
                    .getString(R.string.movie_poster_content_description) + " - " + dataSet.get(position).title);
            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onPosterClicked(dataSet.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() { return dataSet.size(); }

    private String buildPosterPathPrefix(Integer columnWidth) {
        String resStr = "http://image.tmdb.org/t/p/w";
        if (columnWidth < ((92+154) / 2))
            resStr = resStr.concat("92");
        if (columnWidth < ((154 + 185) / 2))
            resStr = resStr.concat("154");
        if (columnWidth < ((185 + 342) / 2))
            resStr = resStr.concat("185");
        if (columnWidth < ((342 + 500) / 2))
            resStr = resStr.concat("342");
        if (columnWidth < ((500 + 780) / 2))
            resStr = resStr.concat("500");
        else
            resStr = resStr.concat("780");
        return resStr;
    }
}
