package com.example.android.shoppingspace;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

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

        //set toolbar
        setSupportActionBar(toolbar);

        //set toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.drawer_open, R.string.drawer_closed);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolBar);
    }
}