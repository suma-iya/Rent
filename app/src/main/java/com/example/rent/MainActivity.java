package com.example.rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private final List<MyItems> items = new ArrayList<>();
    private static final String ADMIN_PHONE_NUMBER = "0123456789";  // Replace with your admin's phone number
    private String loggedInUserPhoneNumber;  // Store the logged-in user's phone number

    public RecyclerView recyclerView;





    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        reference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize the adapter here



        // Get the phone number of the logged-in user
        loggedInUserPhoneNumber = getIntent().getStringExtra("PHONE_NUMBER");

        if (loggedInUserPhoneNumber.equals(ADMIN_PHONE_NUMBER)) {
            // Fetch all users' data for admin

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    items.clear();  // Clear the list before adding new data
                    Log.d("FirebaseData", "Fetching all users' data");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String fullName = dataSnapshot.child("name").getValue(String.class);
                        String mobile = dataSnapshot.getKey();  // The phone number is the key
                        String email = dataSnapshot.child("email").exists() ? dataSnapshot.child("email").getValue(String.class) : "N/A";
                        String electricityBill = dataSnapshot.child("electricityBill").exists() ? dataSnapshot.child("electricityBill").getValue(String.class) : "0";
                        String rent = dataSnapshot.child("rent").exists() ? dataSnapshot.child("rent").getValue(String.class) : "0";
                        String total = dataSnapshot.child("total").exists() ? dataSnapshot.child("total").getValue(String.class) : "0";
                        String password = dataSnapshot.child("password").exists() ? dataSnapshot.child("password").getValue(String.class) : "N/A";
                        // Create formatted strings to show user data
                        if (fullName != null && mobile != null) {
                            items.add(new MyItems(fullName, mobile,email, electricityBill, rent,total, password));
                        }
                    }
                    MyAdapter adapter = new MyAdapter(items, MainActivity.this, loggedInUserPhoneNumber);

                    recyclerView.setAdapter(adapter);
                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            // Fetch only the logged-in user's data

            reference.child(loggedInUserPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    items.clear();  // Clear the list before adding new data
                    Log.d("FirebaseData", "Fetching all users' data");
                    String fullName = dataSnapshot.child("name").getValue(String.class);
                    String mobile = dataSnapshot.getKey();
                    String email = dataSnapshot.child("email").exists() ? dataSnapshot.child("email").getValue(String.class) : "N/A";
                    String electricityBill = dataSnapshot.child("electricityBill").exists() ? dataSnapshot.child("electricityBill").getValue(String.class) : "0";
                    String rent = dataSnapshot.child("rent").exists() ? dataSnapshot.child("rent").getValue(String.class) : "0";
                    String total = dataSnapshot.child("total").exists() ? dataSnapshot.child("total").getValue(String.class) : "0";
                    String password = dataSnapshot.child("password").exists() ? dataSnapshot.child("password").getValue(String.class) : "N/A";
                    // Add the user's data to the list
                    if (fullName != null) {
                        items.add(new MyItems(fullName, mobile,email, electricityBill, rent,total,password));
                    }

                    UserAdapter adapter = new UserAdapter(items, MainActivity.this, loggedInUserPhoneNumber);
                    recyclerView.setAdapter(adapter);
                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // Fetch all users' data (for admin)
        // Fetch only the logged-in user's data (for regular users)

}
