package com.example.android.shoppingspace;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GroceryItemDao {
    @Insert
    void insert(GroceryItem groceryItem);

    //GetAllItems Method
    @Query("SELECT * FROM grocery_items")
    //return type is List of GroceryItems and name it getAllItems
    List<GroceryItem> getAllItems();

    //Change rate method
    @Query("UPDATE grocery_items SET rate = :newRate WHERE id = :id")
    Void updateRate(int id, int newRate);

    //Add Review method
    @Query("SELECT * FROM grocery_items WHERE id = :id")
    //return type GroceryItem named getItemByid
    GroceryItem getItemById(int id);

    //Update Reviews
    @Query("UPDATE grocery_items SET reviews = :reviews WHERE id = :id")
    Void updateReviews(int id, String reviews);

    //Search for items
    @Query("SELECT * FROM grocery_items WHERE name LIKE :text")
    List<GroceryItem> searchForItem(String text);
    // Get Categories
    @Query("SELECT DISTINCT category FROM grocery_items")
    List<String > getCategories();

    //get Items By Category
    @Query("SELECT * FROM grocery_items WHERE category = :category")
    List<GroceryItem> getItemsByCategory(String category);

    //Increase popularity Points
    @Query("UPDATE grocery_items SET popularityPoint = :points WHERE id = :id")
    Void updatePopularityPoint(int id, int points);

    //Change User points
    @Query("UPDATE grocery_items SET userPoint = :points WHERE id = :id")
    Void updateUserPoint(int id, int points);

}
