package com.example.rent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final List<MyItems> items;
    private final Context context;
    private final String loggedInUserPhoneNumber;

    private static final String ADMIN_PHONE_NUMBER = "01537617361";

    public MyAdapter(List<MyItems> items, Context context, String loggedInUserPhoneNumber) {
        this.items = items;
        this.context = context;
        this.loggedInUserPhoneNumber = loggedInUserPhoneNumber;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        MyItems currentItem = items.get(position);

        // Log item details for debugging
        Log.d("MyAdapter", "Item at position " + position + ": " +
                "Full Name: " + currentItem.getFullName() +
                ", Phone: " + currentItem.getMobile() +
                ", Email: " + currentItem.getEmail() +
                ", Electricity Bill: " + currentItem.getElectricityBill() +
                ", Rent: " + currentItem.getRent() +
                ", Total: " + currentItem.getTotal());

        holder.fullName.setText("Name  :" + currentItem.getFullName());
        holder.mobile.setText("Mobile :" + currentItem.getMobile());
        holder.email.setText("Email :" + currentItem.getEmail());
        holder.electricityBill.setText("Electricity Bill :" + currentItem.getElectricityBill());
        holder.rent.setText("Rent :" + currentItem.getRent());
        holder.total.setText("Due :" + currentItem.getTotal());
        holder.password.setText("Password:" + currentItem.getPassword());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Implement the edit functionality here
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit User Data");
                View view = LayoutInflater.from(context).inflate(R.layout.update_popup, null);
                builder.setView(view);
                Dialog dialog = builder.create();



                EditText fullName = view.findViewById(R.id.txtName);
                EditText mobile = view.findViewById(R.id.txtPhone);
                EditText email = view.findViewById(R.id.txtEmail);
                EditText electricityBill = view.findViewById(R.id.txtElectricityBill);
                EditText rent = view.findViewById(R.id.txtRent);
                EditText total = view.findViewById(R.id.txtTotal);
                EditText password = view.findViewById(R.id.txtPassword);

                Button update = view.findViewById(R.id.update);
                fullName.setText(currentItem.getFullName());
                mobile.setText(currentItem.getMobile());
                email.setText(currentItem.getEmail());
                electricityBill.setText(currentItem.getElectricityBill());
                rent.setText(currentItem.getRent());
                total.setText(currentItem.getTotal());
                password.setText(currentItem.getPassword());

                dialog.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the updated data from the EditText fields
                        String updatedName = fullName.getText().toString();
                        String updatedEmail = email.getText().toString();
                        String updatedElectricityBill = electricityBill.getText().toString();
                        String updatedRent = rent.getText().toString();
                        String updatedTotal = total.getText().toString();
                        String updatedPassword = password.getText().toString();

                        // Prepare the map to update the data in Firebase
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", updatedName);
                        map.put("email", updatedEmail);
                        map.put("electricityBill", updatedElectricityBill);
                        map.put("rent", updatedRent);
                        map.put("total", updatedTotal);
                        map.put("password", updatedPassword);

                        // Update Firebase data
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(currentItem.getMobile())
                                .updateChildren(map)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Update the item in the local list
                                        currentItem.setFullName(updatedName);
                                        currentItem.setEmail(updatedEmail);
                                        currentItem.setElectricityBill(updatedElectricityBill);
                                        currentItem.setRent(updatedRent);
                                        currentItem.setTotal(updatedTotal);
                                        currentItem.setPassword(updatedPassword);

                                        // Notify the adapter about the change
                                        notifyItemChanged(position);

                                        // Dismiss the dialog and show a success message
                                        dialog.dismiss();
                                        Log.d("MyAdapter", "User data updated successfully");
                                        Toast.makeText(context, "User data updated successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("MyAdapter", "Error updating user data: " + task.getException());
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    dialog.dismiss();
                                    Log.e("MyAdapter", "Error updating user data: " + e.getMessage());
                                    Toast.makeText(context, "Error updating user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                });


            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog before deleting the user
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete User Data");
                builder.setMessage("Are you sure you want to delete this user?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    // Delete the user data from Firebase
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(currentItem.getMobile())    // Assuming phone number is the key
                            .removeValue()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Remove the item from the list and notify the adapter
                                    items.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, items.size());  // Notify that the range of items has changed
                                    Log.d("MyAdapter", "User data deleted successfully");
                                    Toast.makeText(context, "User data deleted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("MyAdapter", "Error deleting user data: " + task.getException());
                                }
                            })
                            .addOnFailureListener(e -> {
                                Log.e("MyAdapter", "Error deleting user data: " + e.getMessage());
                                Toast.makeText(context, "Error deleting user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                // Show the dialog
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView fullName, mobile, email, electricityBill, rent, total, password;

        Button btnEdit, btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.userName);
            mobile = itemView.findViewById(R.id.userPhone);
            email = itemView.findViewById(R.id.userEmail);
            electricityBill = itemView.findViewById(R.id.userElectricityBill);
            rent = itemView.findViewById(R.id.userRent);
            total = itemView.findViewById(R.id.userTotal);
            password = itemView.findViewById(R.id.userPassword);

            btnEdit = (Button)itemView.findViewById(R.id.edit);
            btnDelete = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
