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
    public static int getID() {
        ID++;
        return ID;
    }

 }

