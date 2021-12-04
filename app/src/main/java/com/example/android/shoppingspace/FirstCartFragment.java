package com.example.android.shoppingspace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FirstCartFragment extends Fragment implements CartAdapter.DeleteItem, CartAdapter.TotalPrice {
    private CartAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalPrice_textView, noItems_textView;
    private Button next_btn;
    private RelativeLayout itemsRelLayout;


    public FirstCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_cart, container, false);
        initViews(view);
        adapter = new CartAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<GroceryItem> cartItems = Utils.getCartItems(getActivity());

        if(null != cartItems){
            if(cartItems.size() > 0){
                noItems_textView.setVisibility(View.GONE);
                itemsRelLayout.setVisibility(View.VISIBLE);
                adapter.setItems(cartItems);
            }else{
                noItems_textView.setVisibility(View.VISIBLE);
                itemsRelLayout.setVisibility(View.GONE);
            }
        }else{
            noItems_textView.setVisibility(View.VISIBLE);
            itemsRelLayout.setVisibility(View.GONE);
        }
        //onClickListener for nextButton to open SecongCartFragment for customer details
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new SecondCartFragment());
                transaction.commit();
            }
        });

        return view;
    }

    //initialize widgets
    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        totalPrice_textView = view.findViewById(R.id.totalPrice_textView);
        noItems_textView = view.findViewById(R.id.noItem_textView);
        next_btn = view.findViewById(R.id.next_btn);
        itemsRelLayout = view.findViewById(R.id.itemsRelLayout);
    }

    @Override
    public void onDeleteResult(GroceryItem item) {
        Utils.deleteItemFromCart(getActivity(), item);
        ArrayList<GroceryItem> cartItems = Utils.getCartItems(getActivity());

        if(null != cartItems){
            if(cartItems.size() > 0){
                noItems_textView.setVisibility(View.GONE);
                itemsRelLayout.setVisibility(View.VISIBLE);
                adapter.setItems(cartItems);
            }else{
                noItems_textView.setVisibility(View.VISIBLE);
                itemsRelLayout.setVisibility(View.GONE);
            }
        }else{
            noItems_textView.setVisibility(View.VISIBLE);
            itemsRelLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTotalPrice(double price) {
        totalPrice_textView.setText(String.valueOf(price) + " $");
    }
}