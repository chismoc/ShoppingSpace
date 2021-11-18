package com.example.android.shoppingspace;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static int ID = 0;
    //shared preference name
    private static final String DB_NAME = "fake database";

    //used to cast Json to ArrayList
   private static Gson gson = new Gson();

    //interface to covert Json file to ArrayList
    private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>(){}.getType();

    private static final String ALL_ITEMS_KEY = "all_items";

    public static void initSharedPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),
                groceryListType);

        //check if ArrayList is null
        if(null == allItems){
            //initialize allItems using the method
            initAllItems(context);
        }
    }

    private static void initAllItems(Context context) {
        ArrayList<GroceryItem>allItems = new ArrayList<>();

        //add GroceryItem List
        GroceryItem cake = new GroceryItem("Cake","Cake is so delicious",
                "https://images.pexels.com/photos/2144200/pexels-photo-2144200.jpeg",
                "food",2.30,8
        );
        allItems.add(cake);

        GroceryItem iceCream = new GroceryItem("ice cream","Delicious","https://images.pexels.com/photos/108370/pexels-photo-108370.jpeg","food",5.4,8);
       iceCream.setPopularityPoint(10);
       iceCream.setUserPoint(7);
        allItems.add(iceCream);



        GroceryItem soda = new GroceryItem("Soda","Tasty","https://cdn.diffords.com/contrib/bws/2019/05/5cc9b8261f976.jpg","Drink",0.99,15);
        soda.setPopularityPoint(5);
        soda.setUserPoint(15);
        allItems.add(soda);

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY,gson.toJson(allItems));
        editor.commit();
    }
//method to get ArrayList
    public static ArrayList<GroceryItem> getAllItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),
                groceryListType);
        return allItems;
    }
    //method to recieve item Id and rating
    public static void changeRate(Context context, int itemId, int newRate){
        //initialize shared preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Recieve ArrayList of items
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);

        //check if Arraylist is null
        if(null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getId() == itemId) {
                    item.setRate(newRate);
                    newItems.add(item);
                } else {
                    newItems.add(item);
                }
            }
            //remove items fro ArrayList
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }
    public static int getID() {
        ID++;
        return ID;
    }
    public static void addReviews(Context context, Review review){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null != allItems){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem item : allItems){
                if(item.getId() == review.getGroceryItemId()){
                    ArrayList<Review> reviews = item.getReviews();
                    reviews.add(review);
                    item.setReviews(reviews);
                    newItems.add(item);
                }else{
                    newItems.add(item);
                }
            }
            //remove items from Arraylist
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }
    //method to get reviews
    public static ArrayList<Review> getReviewsById(Context context, int itemId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null != allItems){
            for(GroceryItem item : allItems){
                if(item.getId() == itemId){
                    ArrayList<Review> reviews = item.getReviews();
                    return reviews;
                }
            }
        }
        return null;

    }

 }

