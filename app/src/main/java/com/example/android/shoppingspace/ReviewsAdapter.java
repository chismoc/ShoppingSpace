package com.example.android.shoppingspace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsAdapter extends  RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{

    private ArrayList<Review> reviews = new ArrayList<>();

    //blank constructor

    public ReviewsAdapter( ) {
            }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set values for textViews
        holder.review_textView.setText(reviews.get(position).getText());
        holder.date_textView.setText(reviews.get(position).getDate());
        holder.userName_textView.setText(reviews.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //initalize widgets
        private TextView userName_textView, review_textView, date_textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //instatiate widgets
            userName_textView = itemView.findViewById(R.id.userName_textView);
            review_textView = itemView.findViewById(R.id.review_TextView);
            date_textView = itemView.findViewById(R.id.date_textView);
        }
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
