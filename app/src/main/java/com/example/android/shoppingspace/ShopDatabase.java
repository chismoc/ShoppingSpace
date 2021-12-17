package com.example.android.shoppingspace;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

import kotlin.jvm.Synchronized;

@Database(entities = {GroceryItem.class, CartItem.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase {
    //create abstract methods for getting DAO OBJECTS
    public abstract GroceryItemDao groceryItemDao();
    public abstract CartItemDao cartItemDao();

    //Singletone pattern for single database instance
    private static ShopDatabase instance;

    //make constructor Thread safe, add context, class and name of database
    public  static synchronized ShopDatabase getInstance(Context context){
        if(null == instance){
          instance = Room.databaseBuilder(context, ShopDatabase.class,"shop_database")
                  .fallbackToDestructiveMigration()
                  .addCallback(initialCallBack)
                  .allowMainThreadQueries()
                  .build();
        }
        return instance;
    }
    //RoomDatabase Callback
    private static RoomDatabase.Callback initialCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new intitialAsyncTask(instance).execute();
        }
    };
    private static class intitialAsyncTask extends AsyncTask<Void, Void, Void>{
        private  GroceryItemDao groceryItemDao;


        public intitialAsyncTask(ShopDatabase db){
            this.groceryItemDao = db.groceryItemDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<GroceryItem> allItems = new ArrayList<>();

            //add GroceryItem List
            GroceryItem cake = new GroceryItem("Cake", "Cake is so delicious",
                    "https://images.pexels.com/photos/2144200/pexels-photo-2144200.jpeg",
                    "food", 2.30, 8
            );
            allItems.add(cake);

            GroceryItem iceCream = new GroceryItem("ice cream", "Delicious", "https://images.pexels.com/photos/108370/pexels-photo-108370.jpeg", "food", 5.4, 8);
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
            allItems.add(soda);

            for (GroceryItem g: allItems){
                groceryItemDao.insert(g);
            }
            return null;
        }


    }
}
