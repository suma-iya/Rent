package com.example.rent;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button login = findViewById(R.id.loginBtn);
        final TextView register = findViewById(R.id.registerNowBtn);

        login.setOnClickListener(v -> {
            String phoneText = phone.getText().toString();
            String passwordText = password.getText().toString();
            if (phoneText.isEmpty() || passwordText.isEmpty()) {
                phone.setError("Phone number is required");
                password.setError("Password is required");
            } else {
                reference.child(phoneText).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            String pass = task.getResult().child("password").getValue(String.class);
                            if (pass != null && pass.equals(passwordText)) {
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                                // Pass the phone number to MainActivity using Intent
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("PHONE_NUMBER", phoneText);
                                startActivity(intent);

                            } else {
                                Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        register.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }
}