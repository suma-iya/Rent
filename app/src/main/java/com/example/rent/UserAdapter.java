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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private List<MyItems> items;
    private Context context;
    private String loggedInUserPhoneNumber;

    private static final String ADMIN_PHONE_NUMBER = "01537617361";

    public UserAdapter(List<MyItems> items, Context context, String loggedInUserPhoneNumber) {
        this.items = items;
        this.context = context;
        this.loggedInUserPhoneNumber = loggedInUserPhoneNumber;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userrecyclerview_adapter_layout, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        MyItems currentItem = items.get(position);

        // Log item details for debugging
        Log.d("MyAdapter", "Item at position " + position + ": " +
                "Full Name: " + currentItem.getFullName() +
                ", Phone: " + currentItem.getMobile() +
                ", Email: " + currentItem.getEmail() +
                ", Electricity Bill: " + currentItem.getElectricityBill() +
                ", Rent: " + currentItem.getRent() +
                ", Total: " + currentItem.getTotal());

        holder.fullName.setText("Name :" + currentItem.getFullName());
        holder.mobile.setText("Mobile :" + currentItem.getMobile());
        holder.email.setText("Email :" + currentItem.getEmail());
        holder.electricityBill.setText("Electricity Bill :" + currentItem.getElectricityBill());
        holder.rent.setText("Rent :" + currentItem.getRent());
        holder.total.setText("Due :" + currentItem.getTotal());
        holder.password.setText("Password:" + currentItem.getPassword());

        holder.btnEdit.setOnClickListener(v -> {
            // Handle edit button click
            // Implement the edit functionality here
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Edit User Data");
            View view = LayoutInflater.from(context).inflate(R.layout.user_update_popup, null);
            builder.setView(view);
            Dialog dialog = builder.create();

            // Initialize the EditText fields
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText fullName = view.findViewById(R.id.userTxtName);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText mobile = view.findViewById(R.id.userTxtPhone);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText email = view.findViewById(R.id.userTxtEmail);
//            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText electricityBill = view.findViewById(R.id.userTxtElectricityBill);
//            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText rent = view.findViewById(R.id.userTxtRent);
//            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText total = view.findViewById(R.id.userTxtTotal);
//
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText password = view.findViewById(R.id.userTxtPassword);

            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button update = view.findViewById(R.id.userUpdate);
            fullName.setText(currentItem.getFullName());
            mobile.setText(currentItem.getMobile());
            email.setText(currentItem.getEmail());
//            electricityBill.setText(currentItem.getElectricityBill());
//            rent.setText(currentItem.getRent());
//            total.setText(currentItem.getTotal());
            password.setText(currentItem.getPassword());

            dialog.show();

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the updated data from the EditText fields
                    String updatedName = fullName.getText().toString();
                    String updatedEmail = email.getText().toString();
//                    String updatedElectricityBill = electricityBill.getText().toString();
//                    String updatedRent = rent.getText().toString();
//                    String updatedTotal = total.getText().toString();
                    String updatedPassword = password.getText().toString();

                    // Prepare the map to update the data in Firebase
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", updatedName);
                    map.put("email", updatedEmail);
//                    map.put("electricityBill", updatedElectricityBill);
//                    map.put("rent", updatedRent);
//                    map.put("total", updatedTotal);
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
//                                    currentItem.setElectricityBill(updatedElectricityBill);
//                                    currentItem.setRent(updatedRent);
//                                    currentItem.setTotal(updatedTotal);
                                    currentItem.setPassword(updatedPassword);

                                    // Notify the adapter about the change
                                    notifyItemChanged(position);

                                    // Dismiss the dialog and show a success message
                                    dialog.dismiss();
                                    Log.d("UserAdapter", "User data updated successfully");
                                    Toast.makeText(context, "User data updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("UserAdapter", "Error updating user data: " + task.getException());
                                }
                            })
                            .addOnFailureListener(e -> {
                                dialog.dismiss();
                                Log.e("UserAdapter", "Error updating user data: " + e.getMessage());
                                Toast.makeText(context, "Error updating user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            });

        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    static class UserViewHolder extends RecyclerView.ViewHolder{
        private final TextView fullName, mobile, email, electricityBill, rent, total, password;

        Button btnEdit;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.newuserName);
            mobile = itemView.findViewById(R.id.newuserPhone);
            email = itemView.findViewById(R.id.newuserEmail);
            electricityBill = itemView.findViewById(R.id.newuserElectricityBill);
            rent = itemView.findViewById(R.id.newuserRent);
            total = itemView.findViewById(R.id.newuserTotal);
            password = itemView.findViewById(R.id.newuserPassword);

            btnEdit = (Button)itemView.findViewById(R.id.newedit);

        }
    }
}
