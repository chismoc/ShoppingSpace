package com.example.android.shoppingspace;

import static com.example.android.shoppingspace.GroceryItemActivity.GROCERY_ITEM_KEY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewDialog extends DialogFragment {
    public interface AddReview {
        void onAddReviewResult(Review review);
    }

    //instatiate AddReview Interface
    private AddReview addReview;
    //initialize views
    private TextView itemName_textView, warning_textView;
    private EditText userName_editText, review_editText;
    private Button addReview_btn;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Create View object and inflate layout
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);

        //initialize UI elements
        initViews(view);

        //alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
           final GroceryItem item = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (null != item) {
                itemName_textView.setText(item.getName());
                addReview_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = userName_editText.getText().toString();
                        String review = review_editText.getText().toString();
                        String date = getCurrentDate();

                        if (userName.equals("") || review.equals("")) {
                            warning_textView.setText("fill all fields");
                            warning_textView.setVisibility(View.VISIBLE);
                        } else {
                            warning_textView.setVisibility(View.GONE);

                            try {
                                addReview = (AddReview) getActivity();
                                addReview.onAddReviewResult(new Review(item.getId(), userName, review, date));
                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        return builder.create();
    }

    //Current date method
    private String getCurrentDate() {
        //instatiate Calender
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    private void initViews(View view) {
        itemName_textView = view.findViewById(R.id.itemName_textView);
        warning_textView = view.findViewById(R.id.warning_textView);
        userName_editText = view.findViewById(R.id.userName_editView);
        review_editText = view.findViewById(R.id.review_editView);
        addReview_btn = view.findViewById(R.id.addReview_btn);
    }
}
