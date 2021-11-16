package com.example.android.shoppingspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity {
    public static final String GROCERY_ITEM_KEY = "incoming_item";
    //initialise widgets
    private RecyclerView reviewsRecView;
    private TextView name_textView, price_textView, description_textView,addReview_textView;
    private ImageView itemImage_img, firstEmptyStar_img, secondEmptyStar_img, thirdEmptyStar_img,
            firstFilledStar_img, secondFilledStar_img, thirdFilledStar_img;
    private Button addToCart_btn;
    private RelativeLayout firstStarRelLayout, secondStarRelLayout, thirdStarRelLayout;
    private GroceryItem incomingItem;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        initViews();

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if(null != intent){
            //recieve incoming GroceryItem
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            //check if incomingItem is not null
            if(null != incomingItem){
                name_textView.setText(incomingItem.getName());
                description_textView.setText(incomingItem.getDescription());
                price_textView.setText(String.valueOf(incomingItem.getPrice()) + " $");

                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(itemImage_img);

                ArrayList<Review> reviews = incomingItem.getReviews();

                //check if reviews is not null
                if(null != reviews){
                    if(reviews.size() > 0){

                        ReviewsAdapter adapter = new ReviewsAdapter();
                        reviewsRecView.setAdapter(adapter);
                        reviewsRecView.setLayoutManager(new LinearLayoutManager(this));
                        adapter.setReviews(reviews);
                    }
                }
                addToCart_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO add item to cart
                    }
                });

                addReview_textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO show a dialog
                    }
                });
            }

        }
    }

    private void initViews() {
        name_textView = findViewById(R.id.name_textView);
        description_textView = findViewById(R.id.description_textView);
        price_textView = findViewById(R.id.price_textView);
        addReview_textView = findViewById(R.id.addReview_textView);
        itemImage_img = findViewById(R.id.itemImage_img);
        firstEmptyStar_img = findViewById(R.id.firstEmptyStar_img);
        secondEmptyStar_img = findViewById(R.id.secondEmptyStar_img);
        thirdEmptyStar_img = findViewById(R.id.thirdEmptyStar_img);
        firstFilledStar_img = findViewById(R.id.firstFilledStar_img);
        secondFilledStar_img = findViewById(R.id.secondFilledStar_img);
        thirdFilledStar_img = findViewById(R.id.thirdFilledStar_img);
        addToCart_btn = findViewById(R.id.addToCart_btn);
        reviewsRecView = findViewById(R.id.reviewsRecView);
        firstStarRelLayout = findViewById(R.id.firstStarRelLayout);
        secondStarRelLayout = findViewById(R.id.secondStarRelLayout);
        thirdStarRelLayout = findViewById(R.id.thirdStarRelLayout);

        toolbar = findViewById(R.id.toolbar);


    }
}