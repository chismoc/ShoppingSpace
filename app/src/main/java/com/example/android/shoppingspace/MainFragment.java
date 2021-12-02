package com.example.android.shoppingspace;

import android.content.Intent;
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
import java.util.Collections;
import java.util.Comparator;

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

        //method to select home by default
        initBottomNavView();
   return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecViews();
            }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Add onClickListener
      bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              switch (item.getItemId()){
                  case R.id.home:
                      Toast.makeText(getActivity(), "home selected", Toast.LENGTH_SHORT).show();
                      break;
                  case R.id.search:
                      Intent intent = new Intent(getActivity(), SearchActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                      startActivity(intent);

                      //set Adapter
                      break;

                  case R.id.cart:
                   Intent cartIntent = new Intent(getActivity(), CartActivity.class);
                   cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(cartIntent);
                   break;
                  default:
                      break;
              }
return false;
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

        //Recieve ArrayList for newItems
        ArrayList<GroceryItem> newItems = Utils.getAllItems(getActivity());
        //check if ArrayList is null
        if(null != newItems){
            //Create comparator Inteface to compare items in array and sort in descending order using itemId
            Comparator<GroceryItem> newItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    //compare Id if Id o1 < o2 returm -1 , if o1>02 return 1, if o1== 02 return 0
                    return  o1.getId() - o2.getId();
                }
            };
            Comparator<GroceryItem> reverseComparator = Collections.reverseOrder(newItemsComparator);
            Collections.sort(newItems, reverseComparator);

            //pass item to newitemAdapter
            newItemsAdapter.setItems(newItems);
        }

        //Recieve ArrayList for popularItems
        ArrayList<GroceryItem> popularItems = Utils.getAllItems(getActivity());
        //check if ArrayList is null
        if(null != popularItems){
            //Create comparator Inteface to compare items in array and sort in descending order using itemId
            Comparator<GroceryItem> popularItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    //compare Id if Id o1 < o2 returm -1 , if o1>02 return 1, if o1== 02 return 0
                    return  o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };
            Collections.sort(popularItems, Collections.reverseOrder(popularItemsComparator));

            //pass popularItem to newitemAdapter
            popularItemsAdapter.setItems(popularItems);
        }
        //Recieve ArrayList for suggestedItems
        ArrayList<GroceryItem> suggestedItems = Utils.getAllItems(getActivity());
        //check if ArrayList is null
        if(null != suggestedItems){
            //Create comparator Inteface to compare items in array and sort in descending order using itemId
            Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    //compare Id if Id o1 < o2 returm -1 , if o1>02 return 1, if o1== 02 return 0
                    return  o1.getUserPoint() - o2.getUserPoint();
                }
            };
            Collections.sort(suggestedItems, Collections.reverseOrder(suggestedItemsComparator));

            //pass popularItem to newitemAdapter
            suggestedItemsAdapter.setItems(suggestedItems);
        }


    }
}