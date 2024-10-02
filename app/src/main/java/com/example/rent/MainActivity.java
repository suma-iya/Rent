package com.example.rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    private static final String ADMIN_PHONE_NUMBER = "01537617361";  // Replace with your admin's phone number
    private String loggedInUserPhoneNumber;  // Store the logged-in user's phone number

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        reference = FirebaseDatabase.getInstance().getReference("users");

        // Get the phone number of the logged-in user
        loggedInUserPhoneNumber = getIntent().getStringExtra("PHONE_NUMBER");

        if (loggedInUserPhoneNumber.equals(ADMIN_PHONE_NUMBER)) {
            // If the logged-in user is the admin, fetch all users' data
            fetchAllUsersData();
        } else {
            // If the logged-in user is not the admin, fetch only their data
            fetchSingleUserData(loggedInUserPhoneNumber);
        }
    }

    // Fetch all users' data (for admin)
    private void fetchAllUsersData() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();  // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String fullName = dataSnapshot.child("name").getValue(String.class);
                    String mobile = dataSnapshot.getKey();  // The phone number is the key
                    String email = dataSnapshot.child("email").exists() ? dataSnapshot.child("email").getValue(String.class) : "N/A";
                    Long electricityBill = dataSnapshot.child("electricityBill").exists() ? dataSnapshot.child("electricityBill").getValue(Long.class) : 0L;
                    Long rent = dataSnapshot.child("rent").exists() ? dataSnapshot.child("rent").getValue(Long.class) : 0L;
                    Long total = dataSnapshot.child("total").exists() ? dataSnapshot.child("total").getValue(Long.class) : 0L;

                    // Create a formatted string to show user data
                    String userData = "Name: " + fullName + "\n" +
                            "Phone: " + mobile + "\n" +
                            "Email: " + email + "\n" +
                            "Electricity Bill: " + electricityBill + "\n" +
                            "Rent: " + rent + "\n" +
                            "Total: " + total;

                    // Add to list if fullName and mobile are not null
                    if (fullName != null && mobile != null) {
                        items.add(new MyItems(userData, mobile, email));  // Add each user's data to the list
                    }
                }

                // Set adapter for the RecyclerView
                MyAdapter adapter = new MyAdapter(items, MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fetch only the logged-in user's data (for regular users)
    private void fetchSingleUserData(String phoneNumber) {
        reference.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();  // Clear the list before adding new data
                String fullName = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").exists() ? dataSnapshot.child("email").getValue(String.class) : "N/A";
                Long electricityBill = dataSnapshot.child("electricityBill").exists() ? dataSnapshot.child("electricityBill").getValue(Long.class) : 0L;
                Long rent = dataSnapshot.child("rent").exists() ? dataSnapshot.child("rent").getValue(Long.class) : 0L;
                Long total = dataSnapshot.child("total").exists() ? dataSnapshot.child("total").getValue(Long.class) : 0L;

                // Create a formatted string to show user data
                String userData = "Name: " + fullName + "\n" +
                        "Phone: " + phoneNumber + "\n" +
                        "Email: " + email + "\n" +
                        "Electricity Bill: " + electricityBill + "\n" +
                        "Rent: " + rent + "\n" +
                        "Total: " + total;

                // Add to list if fullName and mobile are not null
                if (fullName != null) {
                    items.add(new MyItems(userData, phoneNumber, email));  // Add the user's data to the list
                }

                // Set adapter for the RecyclerView
                MyAdapter adapter = new MyAdapter(items, MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
