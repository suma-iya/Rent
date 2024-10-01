package com.example.rent;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

public class Register extends AppCompatActivity {

    // Corrected DatabaseReference
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users"); // Set a valid node like 'users'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText phone = findViewById(R.id.regphone);
        final EditText password = findViewById(R.id.regpassword);
        final EditText name = findViewById(R.id.fullName);
        final Button registerBtn = findViewById(R.id.regBtn);
        final TextView login = findViewById(R.id.loginNowBtn);
        final EditText email = findViewById(R.id.email);

        registerBtn.setOnClickListener(v -> {
            String phoneText = phone.getText().toString();
            String passwordText = password.getText().toString();
            String nameText = name.getText().toString();
            String emailText = email.getText().toString();

            if (phoneText.isEmpty() || passwordText.isEmpty() || nameText.isEmpty()) {
                phone.setError("Phone number is required");
                password.setError("Password is required");
                name.setError("Name is required");
            } else {
                reference.child(phoneText).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(Register.this, "User already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            reference.child(phoneText).child("name").setValue(nameText);
                            reference.child(phoneText).child("password").setValue(passwordText);
                            reference.child(phoneText).child("email").setValue(emailText);
                            Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Register.this, "Registration failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        login.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Login.class));
        });
    }
}
