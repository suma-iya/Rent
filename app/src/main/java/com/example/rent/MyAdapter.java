package com.example.rent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final List<MyItems> items;
    private final Context context;

    // Constructor to initialize the adapter with the list of items and context
    public MyAdapter(List<MyItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item and pass it to the ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        MyItems currentItem = items.get(position);

        // Log item details for debugging
        Log.d("MyAdapter", "Item at position " + position + ": " + currentItem.getFullName());

        holder.fullName.setText(currentItem.getFullName());
        holder.mobile.setText(currentItem.getMobile());
        holder.email.setText(currentItem.getEmail());
    }


    @Override
    public int getItemCount() {
        return items.size(); // Return the size of the item list
    }

    // ViewHolder class to hold references to the views for each item
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView fullName, mobile, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextViews by finding them in the inflated view
            fullName = itemView.findViewById(R.id.userName);
            mobile = itemView.findViewById(R.id.userPhone);
            email = itemView.findViewById(R.id.userEmail);

        }
    }
}
