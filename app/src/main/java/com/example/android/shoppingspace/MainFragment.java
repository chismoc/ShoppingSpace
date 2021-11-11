package com.example.android.shoppingspace;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MainFragment extends Fragment {
    //instatiate widget
    private BottomNavigationView bottomNavigationView;


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


    }


}