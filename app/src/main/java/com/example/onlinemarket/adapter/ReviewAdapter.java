package com.example.onlinemarket.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {


    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ReviewHolder extends RecyclerView.ViewHolder{

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
