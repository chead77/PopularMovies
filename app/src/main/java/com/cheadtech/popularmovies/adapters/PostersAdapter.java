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
    private String posterPathBase;

    public PostersAdapter(ArrayList<Movie> data, String base, PostersAdapterCallback callback) {
        posterPathBase = base;
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
            Picasso.get().load(posterPathBase + dataSet.get(position).poster_path).into(poster);
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
}
