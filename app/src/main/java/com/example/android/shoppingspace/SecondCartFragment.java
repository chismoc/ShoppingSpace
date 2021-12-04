package com.example.android.shoppingspace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SecondCartFragment extends Fragment {
    public static final String ORDER_KEY = "order";
    private EditText address_editText, zipCode_editText, phoneNumber_editText, email_editText;
    private Button next_btn, back_btn;
    private TextView warning_textView;

    public SecondCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and return View object
        View view = inflater.inflate(R.layout.fragment_second_cart, container, false);

        //initialize view elements
        initViews(view);

        //recieve data from ThirdCartFragment
        Bundle bundle = getArguments();
        if (null != bundle) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (null != jsonOrder) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {
                }.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if (null != order) {
                    address_editText.setText(order.getAddress());
                    phoneNumber_editText.setText(order.getPhoneNumber());
                    zipCode_editText.setText(order.getZipCode());
                    email_editText.setText(order.getEmail());
                }
            }
        }

        //OnClickListener for back button to FirstCartFragement for cart Items
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new FirstCartFragment());
                transaction.commit();
            }
        });

        //onClickListener for nextButton to open ThirdCartFragment for checkout
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call ValidateData method
                if (ValidateData()) {
                    warning_textView.setVisibility(View.GONE);
                    //Get CartItems
                    ArrayList<GroceryItem> cartItems = Utils.getCartItems(getActivity());
                    //Check if cartItem is not null and create order object
                    if (null != cartItems) {
                        Order order = new Order();
                        //Set Values for Order Object
                        order.setItems(cartItems);
                        order.setAddress(address_editText.getText().toString());
                        order.setPhoneNumber(phoneNumber_editText.getText().toString());
                        order.setZipCode(zipCode_editText.getText().toString());
                        order.setEmail(email_editText.getText().toString());
                        order.setTotalPrice(CalculateTotalPrice(cartItems));

                        //Convert Order object to Json to pass it as simple text instead of using parcelable
                        //(to pass to ThitdCart Fragment
                        Gson gson = new Gson();
                        String jsonOrder = gson.toJson(order);
                        Bundle bundle = new Bundle();
                        bundle.putString(ORDER_KEY, jsonOrder);

                        ThirdCartFragment thirdCartFragment = new ThirdCartFragment();
                        thirdCartFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, thirdCartFragment);
                        transaction.commit();
                    }
                } else {
                    warning_textView.setVisibility(View.VISIBLE);
                    warning_textView.setText("Please fill all fields");
                }
            }


        });

        return view;
    }

    //Calculate TotalPrice
    private double CalculateTotalPrice(ArrayList<GroceryItem> items) {
        double price = 0;
        for (GroceryItem item : items) {
            price += item.getPrice();
        }
        price = Math.round(price * 100.0) / 100.0;
        return price;
    }

    //validate data method that all fields are filled in editTexts
    private boolean ValidateData() {
        if (address_editText.getText().toString().equals("") || phoneNumber_editText.getText().toString().equals("") ||
                zipCode_editText.getText().toString().equals("") || email_editText.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void initViews(View view) {
        address_editText = view.findViewById(R.id.address_editText);
        zipCode_editText = view.findViewById(R.id.zipCode_editText);
        phoneNumber_editText = view.findViewById(R.id.phoneNumber_editText);
        email_editText = view.findViewById(R.id.email_editText);
        next_btn = view.findViewById(R.id.next_btn);
        back_btn = view.findViewById(R.id.back_btn);
        warning_textView = view.findViewById(R.id.warning_textView);
    }
}