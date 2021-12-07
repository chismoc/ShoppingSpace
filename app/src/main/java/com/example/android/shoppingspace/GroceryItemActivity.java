package com.example.android.shoppingspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {

private static final String TAG = "GroceryItemActivity";
private boolean isBound;
private TrackUserTime mService;

private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        TrackUserTime.LocalBinder binder =(TrackUserTime.LocalBinder) service;
        mService = binder.getService();
        isBound = true;
        mService.setItem(incomingItem);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
isBound = false;
    }
};
private ReviewsAdapter adapter;
    public static final String GROCERY_ITEM_KEY = "incoming_item";
    //initialise widgets
    private RecyclerView reviewsRecView;
    private TextView name_textView, price_textView, description_textView, addReview_textView;
    private ImageView itemImage_img, firstEmptyStar_img, secondEmptyStar_img, thirdEmptyStar_img,
            firstFilledStar_img, secondFilledStar_img, thirdFilledStar_img;
    private Button addToCart_btn;
    private RelativeLayout firstStarRelLayout, secondStarRelLayout, thirdStarRelLayout;
    private GroceryItem incomingItem;
    private MaterialToolbar toolbar;

    @Override
    public void onAddReviewResult(Review review) {
        Utils.addReviews(this,review);
        //Add 3 points if user add review
        Utils.changeUserPoints(this,incomingItem,3);

        //get Reviews
        ArrayList<Review> reviews = Utils.getReviewsById(this,review.getGroceryItemId());

        //check if null
        if(null != reviews){
            adapter.setReviews(reviews);
        }
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        initViews();

        setSupportActionBar(toolbar);
        adapter = new ReviewsAdapter();

        Intent intent = getIntent();
        if (null != intent) {
            //recieve incoming GroceryItem
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            //check if incomingItem is not null
            if (null != incomingItem) {
                Utils.changeUserPoints(this, incomingItem, 1);
                name_textView.setText(incomingItem.getName());
                description_textView.setText(incomingItem.getDescription());
                price_textView.setText(String.valueOf(incomingItem.getPrice()) + " $");

                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(itemImage_img);

                ArrayList<Review> reviews = Utils.getReviewsById(this,incomingItem.getId());
                reviewsRecView.setAdapter(adapter);
                reviewsRecView.setLayoutManager(new LinearLayoutManager(this));
                //check if reviews is not null
                if (null != reviews) {
                    if (reviews.size() > 0) {

                      adapter.setReviews(reviews);
                    }
                }
                addToCart_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Utils.addItemToCart(GroceryItemActivity.this, incomingItem);
                        Intent cartIntent = new Intent(GroceryItemActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);

                    }
                });

                addReview_textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO show a dialog
                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY, incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "Add Review");
                    }
                });

                //method to handle product Rating

                handleRating();
            }

        }
    }

    private void handleRating() {
        switch (incomingItem.getRate()) {
            case 0:
                firstEmptyStar_img.setVisibility(View.VISIBLE);
                firstFilledStar_img.setVisibility(View.GONE);
                secondEmptyStar_img.setVisibility(View.VISIBLE);
                secondFilledStar_img.setVisibility(View.GONE);
                thirdEmptyStar_img.setVisibility(View.VISIBLE);
                thirdFilledStar_img.setVisibility(View.GONE);
                break;
            case 1:
                firstEmptyStar_img.setVisibility(View.GONE);
                firstFilledStar_img.setVisibility(View.VISIBLE);
                secondEmptyStar_img.setVisibility(View.VISIBLE);
                secondFilledStar_img.setVisibility(View.GONE);
                thirdEmptyStar_img.setVisibility(View.VISIBLE);
                thirdFilledStar_img.setVisibility(View.GONE);
                break;
            case 2:
                firstEmptyStar_img.setVisibility(View.GONE);
                firstFilledStar_img.setVisibility(View.VISIBLE);
                secondEmptyStar_img.setVisibility(View.GONE);
                secondFilledStar_img.setVisibility(View.VISIBLE);
                thirdEmptyStar_img.setVisibility(View.VISIBLE);
                thirdFilledStar_img.setVisibility(View.GONE);
                break;
            case 3:
                firstEmptyStar_img.setVisibility(View.GONE);
                firstFilledStar_img.setVisibility(View.VISIBLE);
                secondEmptyStar_img.setVisibility(View.GONE);
                secondFilledStar_img.setVisibility(View.VISIBLE);
                thirdEmptyStar_img.setVisibility(View.GONE);
                thirdFilledStar_img.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
        firstStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRate() !=1){
                    //change user rating
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(),1);
                   //change userpoints according to rating. give 2 points
                    Utils.changeUserPoints(GroceryItemActivity.this, incomingItem, (1-incomingItem.getRate()) * 2);
                    incomingItem.setRate(1);
                    handleRating();
                }
            }
        });

        secondStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRate() !=2){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(),2);
                    //change userpoints according to rating. give 2 points
                    Utils.changeUserPoints(GroceryItemActivity.this, incomingItem, (2-incomingItem.getRate()) * 2);
                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });
        thirdStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRate() !=3){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(),3);
                    //change userpoints according to rating. give 2 points
                    Utils.changeUserPoints(GroceryItemActivity.this, incomingItem, (3-incomingItem.getRate()) * 2);
                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });
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

    @Override
    protected void onStop() {
        super.onStop();
        if(isBound){
            unbindService(connection);
        }
    }
}