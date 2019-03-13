package com.cheadtech.popularmovies.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheadtech.popularmovies.R;

public class TrailersViewHolder extends RecyclerView.ViewHolder {
    public TextView trailerLabel;
    public LinearLayout layout;

    public TrailersViewHolder(@NonNull View itemView) {
        super(itemView);
        trailerLabel = itemView.findViewById(R.id.trailer_label);
        layout = itemView.findViewById(R.id.trailer_item);
    }
}
