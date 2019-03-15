package com.cheadtech.popularmovies.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Review;
import com.cheadtech.popularmovies.viewholders.ReviewsViewHolder;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {
    private ArrayList<Review> dataSet = new ArrayList<>();

    public ReviewsAdapter() {}

    public void setData(@NonNull ArrayList<Review> trailers) {
        this.dataSet = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ReviewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewsViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        if (holder.author != null && holder.content != null && holder.layout != null) {
            holder.author.setText(holder.author.getContext().getString(R.string.written_by, dataSet.get(adapterPosition).author));
            holder.content.setText(dataSet.get(adapterPosition).content);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.layout.getContext().startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(dataSet.get(adapterPosition).url)
                    ));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
