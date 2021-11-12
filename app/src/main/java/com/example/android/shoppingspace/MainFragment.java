package com.example.android.shoppingspace;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MainFragment extends Fragment {
    //instatiate widget
    private BottomNavigationView bottomNavigationView;

    //instatiate Reclerviews
    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView;
    //instatiate Adapters
    private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //method to initiate Views
        initView(view);
        initRecViews();
        //method to select home by default
        initButtomNavView();
   return view;
    }

    private void initButtomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Add onClickListener
      bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
          @Override
          public void onNavigationItemReselected(@NonNull MenuItem item) {
              switch (item.getItemId()){
                  case R.id.home:
                      Toast.makeText(getActivity(), "home selected", Toast.LENGTH_SHORT).show();
                      break;
                  case R.id.search:
                      Toast.makeText(getActivity(), "search selected", Toast.LENGTH_SHORT).show();
                      break;

                  case R.id.cart:
                      Toast.makeText(getActivity(), "cart selected", Toast.LENGTH_SHORT).show();
                      break;
                  default:
                      break;
              }

          }
      });
    }

    private void initView(View view) {
        bottomNavigationView = view.findViewById(R.id.bottomNavView);
        newItemsRecView = view.findViewById(R.id.newItemsRecView);
        popularItemsRecView = view.findViewById(R.id.popularItemsRecView);
        suggestedItemsRecView = view.findViewById(R.id.suggestedItemsRecView);
    }
    private void initRecViews() {
        newItemsAdapter = new GroceryItemAdapter(getActivity());
        newItemsRecView.setAdapter(newItemsAdapter);
        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemsAdapter = new GroceryItemAdapter(getActivity());
        popularItemsRecView.setAdapter(popularItemsAdapter);
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemsRecView.setAdapter(suggestedItemsAdapter);
        suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        //Recieve ArrayList
        ArrayList<GroceryItem> allItems = Utils.getAllItems(getActivity());
        //check if ArrayList is null
        if(null != allItems){
            newItemsAdapter.setItems(allItems);
        }
    }


}