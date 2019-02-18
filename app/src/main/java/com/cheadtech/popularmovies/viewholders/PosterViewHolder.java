package com.cheadtech.popularmovies.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class PosterViewHolder extends RecyclerView.ViewHolder {
    public PosterViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public ImageView poster;
}
