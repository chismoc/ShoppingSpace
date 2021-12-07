package com.example.android.shoppingspace;

import static com.example.android.shoppingspace.SecondCartFragment.ORDER_KEY;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PaymentResultFragment extends Fragment {
//initiliaze widgets
    private TextView message_textView;
    private Button home_btn;

     public PaymentResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_result, container, false);

        initViews(view);
        //set bundle
        Bundle bundle = getArguments();
        if(null !=bundle){
            String jsonOrder = bundle.getString(ORDER_KEY);
            if(null != jsonOrder){
                Gson gson = new Gson();
                Type type = new TypeToken<Order>(){}.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if(null != order){
                    if(order.isSuccessful()){
                        message_textView.setText("Payment Processed Succesfully\n" +
                                "Your Order is being processed ");
                        //Clear Cart Items is shared preference
                        Utils.clearCartItems(getActivity());
                        //check for item and increase popularity by 1 point
                        for(GroceryItem item: order.getItems()){
                            Utils.increasePopularityPoint(getActivity(), item, 1);
                        }
                    }else {

                        message_textView.setText("Payment failed \n Please try again");
                    }
                }
                }else {
                message_textView.setText("Payment failed \n Please try again");
            }
        }
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;
     }

    private void initViews(View view) {
         message_textView = view.findViewById(R.id.message_textView);
         home_btn = view.findViewById(R.id.home_btn);
    }
}