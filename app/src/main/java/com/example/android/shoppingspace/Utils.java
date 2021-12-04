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
    //Order key
    private static int ORDER_ID = 0;
    //Cart item key
    private static String CART_ITEM_KEY = "cart_items";

    //shared preference name
    private static final String DB_NAME = "fake database";

    //used to cast Json to ArrayList
    private static Gson gson = new Gson();

    //interface to covert Json file to ArrayList
    private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
    }.getType();

    private static final String ALL_ITEMS_KEY = "all_items";

    public static void initSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null),
                groceryListType);

        //check if ArrayList is null
        if (null == allItems) {
            //initialize allItems using the method
            initAllItems(context);
        }
    }

    private static void initAllItems(Context context) {
        ArrayList<GroceryItem> allItems = new ArrayList<>();

        //add GroceryItem List
        GroceryItem cake = new GroceryItem("Cake", "Cake is so delicious",
                "https://images.pexels.com/photos/2144200/pexels-photo-2144200.jpeg",
                "food", 2.30, 8
        );
        allItems.add(cake);

        GroceryItem iceCream = new GroceryItem("ice cream", "Delicious", "https://images.pexels.com/photos/108370/pexels-photo-108370.jpeg", "food", 5.4, 8);
        iceCream.setPopularityPoint(10);
        iceCream.setUserPoint(7);
        allItems.add(iceCream);

        allItems.add(new GroceryItem("Cucumber", "it is fresh",
                "https://images.pexels.com/photos/2329440/pexels-photo-2329440.jpeg", "vegetables", 10, 8));
        allItems.add(new GroceryItem("Coca cola", "it is a tasty drink",
                "https://www.myamericanmarket.com/873-large_default/coca-cola-classic.jpg", "drinks", 100, 1));
        allItems.add(new GroceryItem("Spaghetti", "it is an easy to cook meal",
                "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/barilla-rotini-pasta-1527694739.jpg", "food", 16, 5));
        allItems.add(new GroceryItem("Chips and Nugget", "usually enough for 4 person",
                "https://images.pexels.com/photos/6941010/pexels-photo-6941010.jpeg", "food", 15, 10));
        allItems.add(new GroceryItem("Clear Shampoo", "you won't experience hair fall with this",
                "https://100comments.com/wp-content/uploads/2018/02/Untitled-10-3.jpg", "hygiene", 42, 12));
        allItems.add(new GroceryItem("Axe body spray", "is hot and sweaty? not any more",
                "https://pics.drugstore.com/prodimg/519930/900.jpg", "hygiene", 9, 8));
        allItems.add(new GroceryItem("Kleenex", "soft and famous!",
                "https://images-na.ssl-images-amazon.com/images/I/91ZyGoGBMAL._SY355_.jpg", "hygiene", 12, 3));

        GroceryItem soda = new GroceryItem("Soda", "Tasty", "https://cdn.diffords.com/contrib/bws/2019/05/5cc9b8261f976.jpg", "Drink", 0.99, 15);
        soda.setPopularityPoint(5);
        soda.setUserPoint(15);
        allItems.add(soda);

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.commit();
    }

    //method to get ArrayList
    public static ArrayList<GroceryItem> getAllItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null),
                groceryListType);
        return allItems;
    }

    //method to recieve item Id and rating
    public static void changeRate(Context context, int itemId, int newRate) {
        //initialize shared preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Recieve ArrayList of items
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);

        //check if Arraylist is null
        if (null != allItems) {
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
    //Getter for OrderID

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    //Getter for ID
    public static int getID() {
        ID++;
        return ID;
    }

    public static void addReviews(Context context, Review review) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getId() == review.getGroceryItemId()) {
                    ArrayList<Review> reviews = item.getReviews();
                    reviews.add(review);
                    item.setReviews(reviews);
                    newItems.add(item);
                } else {
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
    public static ArrayList<Review> getReviewsById(Context context, int itemId) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            for (GroceryItem item : allItems) {
                if (item.getId() == itemId) {
                    ArrayList<Review> reviews = item.getReviews();
                    return reviews;
                }
            }
        }
        return null;

    }

    //Add Item to cart
    //method to recieve item Id and rating
    public static void addItemToCart(Context context, GroceryItem item) {
        //initialize shared preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);

        //Recieve ArrayList of items
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEM_KEY, null), groceryListType);

        //check if Arraylist is null
        if (cartItems == null) {
            //instatiate cartItems
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //remove items fro ArrayList
        editor.remove(CART_ITEM_KEY);
        editor.putString(CART_ITEM_KEY, gson.toJson(cartItems));
        editor.commit();
    }

    //Get All CartItems
    public static ArrayList<GroceryItem> getCartItems(Context context) {
        //initialize shared preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);

        //Recieve ArrayList of items
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEM_KEY, null), groceryListType);
        return cartItems;
    }
    //searchForItems method
    public static ArrayList<GroceryItem> searchForItems(Context context, String text) {
        //Get ArrayList of all items
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    items.add(item);
                }
                String[] names = item.getName().split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (text.equalsIgnoreCase(names[i])) {
                        boolean doesExist = false;

                        for (GroceryItem j : items) {
                            if (j.getId() == item.getId()) {
                                doesExist = true;
                            }
                        }
                        if (!doesExist) {
                            items.add(item);
                        }
                    }
                }
            }
            return items;
        }
        return null;
    }
    // get all categories
    public static ArrayList<String>getCategories(Context context){
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null != allItems){
            ArrayList<String> categories = new ArrayList<>();
            for(GroceryItem item : allItems){
                boolean doesExist = false;
                for(String s: categories){
                    if(item.getCategory().equals(s)){
                        doesExist = true;
                    }
                }if(!doesExist){
                    categories.add(item.getCategory());
                }
            }return categories;
        }return null;
    }
    public static ArrayList<GroceryItem> getItemByCategory(Context context, String category){
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null != allItems){
            ArrayList<GroceryItem> items = new ArrayList<>();
            for(GroceryItem item : allItems){
                if(item.getCategory().equals(category)){
                    items.add(item);
                }
            }return items;
        }
        return null;
    }
    public static void deleteItemFromCart(Context context, GroceryItem item){
        ArrayList<GroceryItem> cartItems = getCartItems(context);
        if(null != cartItems){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem i : cartItems){
                if(i.getId() != item.getId()){
                    newItems.add(i);
                }
            }
            //initialize shared preference
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEM_KEY);
            editor.putString(CART_ITEM_KEY, gson.toJson(newItems));
            editor.commit();

        }
    }
}



