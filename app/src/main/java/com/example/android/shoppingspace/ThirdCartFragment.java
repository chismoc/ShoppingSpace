package com.example.android.shoppingspace;

import static com.example.android.shoppingspace.SecondCartFragment.ORDER_KEY;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ThirdCartFragment extends Fragment {
private Button back_btn, checkout_btn;
private TextView items_textView, address_textView, phoneNumber_textView, totalPrice_textView;
private RadioGroup payment_rg;
private RadioButton payPal_rb, creditCard_rb;

     public ThirdCartFragment() {
        // Required empty public constructor
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_cart, container, false);

         initViews(view);
         Bundle bundle = getArguments();
         if(null != bundle){
             //Get jsonOrder
             String jsonOrder = bundle.getString(ORDER_KEY);
             if(null != jsonOrder){
                 //convert String to java object
                 Gson gson = new Gson();
                 Type type = new TypeToken<Order>(){}.getType();
                 Order order = gson.fromJson(jsonOrder, type);
                 //check that order object is not null
                 if(null != order){
                     //set textViews to values of order object
                     String items = "";
                     for(GroceryItem i : order.getItems()){
                         items += "\n\t" + i.getName();
                     }
                     items_textView.setText(items);
                     address_textView.setText(order.getAddress());
                     phoneNumber_textView.setText(order.getPhoneNumber());
                     totalPrice_textView.setText(String.valueOf(order.getTotalPrice()));

                     //back_btn navigate user to SecondCartFragment with checkout info
                     back_btn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Bundle backBundle = new Bundle();
                             backBundle.putString(ORDER_KEY, jsonOrder);

                             SecondCartFragment secondCartFragment = new SecondCartFragment();
                             secondCartFragment.setArguments(backBundle);

                             FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.container, secondCartFragment);
                             transaction.commit();
                         }
                     });

                     //checkout Button
                     checkout_btn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             //set payment method for order
                             switch (payment_rg.getCheckedRadioButtonId()){
                                 case R.id.payPal_rb:
                                     order.setPaymentMethod("PayPal");
                                     break;

                                 case R.id.creditCard_rb:
                                     order.setPaymentMethod("Credit Card");
                                     break;
                                 default:
                                     order.setPaymentMethod("Unknown");
                                     break;
                             }
                             //for testing purpose use success otherwise done by paypal and creditcard
                             order.setSuccess(true);
                             //TODO:send request with retrofit
                         }
                     });
                 }
             }
         }

         return view;
    }

    private void initViews(View view) {
         payment_rg = view.findViewById(R.id.paymentRadio_gp);
         address_textView = view.findViewById(R.id.address_textView);
         phoneNumber_textView = view.findViewById(R.id.phoneNumber_textView);
         items_textView = view.findViewById(R.id.items_textView);
         totalPrice_textView = view.findViewById(R.id.price_textView);
         back_btn = view.findViewById(R.id.back_btn);
         checkout_btn = view.findViewById(R.id.checkout_btn);
    }
}