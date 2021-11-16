package com.example.android.shoppingspace;

import static com.example.android.shoppingspace.GroceryItemActivity.GROCERY_ITEM_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {
    private ArrayList<GroceryItem> items = new ArrayList<>();
    private Context context;

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //instance of viewHolder

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getName());
        holder.itemPrice.setText(String.valueOf(items.get(position).getPrice()) + "$");

        //set image
        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageUrl())
                .into(holder.itemImage);

        //set onClickListener

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context.getApplicationContext(), "you clicked", Toast.LENGTH_SHORT).show();
                //TODO:add action
               Intent intent = new Intent(context, GroceryItemActivity.class);
               intent.putExtra(GROCERY_ITEM_KEY, items.get(position));
               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice;
        private ImageView itemImage;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName_textView);
            itemPrice = itemView.findViewById(R.id.itemPrice_textView);
            itemImage = itemView.findViewById(R.id.itemImage_img);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;

        notifyDataSetChanged();
    }
}
