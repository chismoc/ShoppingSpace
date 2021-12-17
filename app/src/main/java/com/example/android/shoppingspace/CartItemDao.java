package com.example.android.shoppingspace;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartItemDao {
    //insert method
    @Query("INSERT INTO cart_items(groceryItemId) VALUES (:id)")
    void insert(int id);
    //get cart items

    @Query("SELECT grocery_items.id, grocery_items.description, grocery_items.imageUrl," +
            "grocery_items.category, grocery_items.price, grocery_items.availableAmount," +
            "grocery_items.rate, grocery_items.userPoint, grocery_items.popularityPoint, " +
            "grocery_items.reviews FROM grocery_items INNER JOIN cart_items ON cart_items.groceryItemId = groceryItemId")
    List<GroceryItem> getAllCartItems();

    //Delete items from cart
    @Query("DELETE FROM cart_items WHERE groceryItemId = :id")
    Void deleteItemById(int id);

    //Clear cart Items
    @Query("DELETE FROM cart_items")
    Void clearCart();

}
