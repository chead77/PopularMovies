package com.cheadtech.popularmovies.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheadtech.popularmovies.R;
import com.cheadtech.popularmovies.models.Trailer;
import com.cheadtech.popularmovies.viewholders.TrailersViewHolder;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersViewHolder> {
    private ArrayList<Trailer> dataSet = new ArrayList<>();

    public TrailersAdapter() {}

    public void setData(@NonNull ArrayList<Trailer> trailers) {
        this.dataSet = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new TrailersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailersViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        if (holder.trailerLabel != null && holder.layout != null) {
            holder.trailerLabel.setText(holder.layout.getResources().getString(R.string.trailer_label, adapterPosition));
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://www.youtube.com/watch";
                    holder.layout.getContext().startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url).buildUpon().appendQueryParameter("v", dataSet.get(adapterPosition).key).build()
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
