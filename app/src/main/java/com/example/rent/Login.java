package com.example.rent;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

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

        }
        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }
}