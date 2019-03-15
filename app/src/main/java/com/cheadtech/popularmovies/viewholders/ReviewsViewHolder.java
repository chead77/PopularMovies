package com.cheadtech.popularmovies.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheadtech.popularmovies.R;

public class ReviewsViewHolder extends RecyclerView.ViewHolder {
    public TextView author;
    public TextView content;
    public LinearLayout layout;

    public ReviewsViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.review_item);
        author = itemView.findViewById(R.id.author);
        content = itemView.findViewById(R.id.content);
    }
}
