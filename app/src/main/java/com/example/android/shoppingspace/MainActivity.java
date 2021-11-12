package com.example.android.shoppingspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //Initialize View Items

    private View items;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize Views method
        initViews();

        Utils.initSharedPreferences(this);

        //set toolbar
        setSupportActionBar(toolbar);

        //set toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.drawer_open, R.string.drawer_closed);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        
        //add onclickListener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.cart:
                        Toast.makeText(MainActivity.this, "Cart Selected", Toast.LENGTH_SHORT).show();
               break;
                    default:
                        break;
                }
                return false;
            }
        });
        //Create fragment Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new MainFragment());
        transaction.commit();
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolBar);
    }
}