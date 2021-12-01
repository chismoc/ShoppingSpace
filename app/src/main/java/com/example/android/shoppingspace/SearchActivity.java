package com.example.android.shoppingspace;

import static com.example.android.shoppingspace.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.android.shoppingspace.AllCategoriesDialog.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {
    private static final String TAG = "searchActivity";

    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<GroceryItem> items = Utils.getItemByCategory(this, category);
        if (null != items) {
            adapter.setItems(items);
        }
    }

    //Initialize widgets
    private MaterialToolbar toolbar;
    private EditText searchBox_editText;
    private ImageView search_btn;
    private TextView firstCat_textVew, secondCat_textView, thirdCat_textView, allCategories_textView;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;

    //Initialize GroceryItemAdapter
    GroceryItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        initBottomNavView();
        setSupportActionBar(toolbar);
        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
if(null != intent){
    String category = intent.getStringExtra("category");
    if(null != category){
        ArrayList<GroceryItem> items = Utils.getItemByCategory(this, category);
        if(null != items){
            adapter.setItems(items);
        }
    }
}
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch();
            }
        });
        //Search inside preferences whenever text inside edit text changes
        searchBox_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //call initSearch method
                initSearch();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ArrayList<String> categories = Utils.getCategories(this);
        if (null != categories) {
            if (categories.size() > 0) {
                if (categories.size() == 1) {
                    showCategories(categories, 1);
                } else if (categories.size() == 2) {
                    showCategories(categories, 2);
                } else {
                    showCategories(categories, 3);
                }
            }
        }
        allCategories_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategoriesDialog dialog = new AllCategoriesDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES, Utils.getCategories(SearchActivity.this));
                bundle.putString(CALLING_ACTIVITY, "search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "All categories dialog");
            }
        });
    }

    private void showCategories(final ArrayList<String> categories, int i) {
        switch (i) {
            case 1:
                firstCat_textVew.setVisibility(View.VISIBLE);
                firstCat_textVew.setText(categories.get(0));
                secondCat_textView.setVisibility(View.GONE);
                thirdCat_textView.setVisibility(View.GONE);
                firstCat_textVew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                break;

            case 2:
                firstCat_textVew.setVisibility(View.VISIBLE);
                firstCat_textVew.setText(categories.get(0));
                secondCat_textView.setVisibility(View.VISIBLE);
                secondCat_textView.setText(categories.get(1));
                thirdCat_textView.setVisibility(View.GONE);
                firstCat_textVew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                secondCat_textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(1));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            case 3:
                firstCat_textVew.setVisibility(View.VISIBLE);
                firstCat_textVew.setText(categories.get(0));
                secondCat_textView.setVisibility(View.VISIBLE);
                secondCat_textView.setText(categories.get(1));
                thirdCat_textView.setVisibility(View.VISIBLE);
                thirdCat_textView.setText(categories.get(2));
                firstCat_textVew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                secondCat_textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(1));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                thirdCat_textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(2));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                break;

            default:
                firstCat_textVew.setVisibility(View.GONE);
                secondCat_textView.setVisibility(View.GONE);
                thirdCat_textView.setVisibility(View.GONE);
        }
    }

    private void initSearch() {
        //check if user has entered anything
        if (!searchBox_editText.getText().toString().equals("")) {
            //Get text of edit text
            String name = searchBox_editText.getText().toString();
            ArrayList<GroceryItem> items = Utils.searchForItems(this, name);
            if (null != items) {
                adapter.setItems(items);
            }
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolBar);
        searchBox_editText = findViewById(R.id.searchBox);
        search_btn = findViewById(R.id.search_btn);
        firstCat_textVew = findViewById(R.id.firstCart_textView);
        secondCat_textView = findViewById(R.id.secondCart_textView);
        thirdCat_textView = findViewById(R.id.thirdCart_textView);
        allCategories_textView = findViewById(R.id.allCategories_textView);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.search);
        //Add onClickListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.search:

                        break;

                    case R.id.cart:

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


}