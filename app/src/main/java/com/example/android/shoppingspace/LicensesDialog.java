package com.example.android.shoppingspace;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;

public class LicensesDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view= getActivity().getLayoutInflater().inflate(R.layout.dialog_licenses, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        TextView licenses_textView = view.findViewById(R.id.licenses_textView);
        Button dismiss_btn = view.findViewById(R.id.dismiss_btn);

        licenses_textView.setText(Utils.getLicenses());
        dismiss_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }
}
