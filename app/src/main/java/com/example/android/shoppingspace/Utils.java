package com.example.android.shoppingspace;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
        GroceryItem milk = new GroceryItem("milk","milk is so delicious",
                "https://www.psdmockups.com/wp-content/uploads/2019/06/16-Tetra-Pak-Carton-Boxes-PSD-Mockup.jpg",
                "drink",2.30,8
        );
        allItems.add(milk);

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

