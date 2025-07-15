package com.codeclub.codeclub_android;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Check if user is already logged in (AUTO LOGIN)
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Redirect to Dashboard without asking for login
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // Prevent back navigation to login screen
            return; // Stop further execution
        }

        // Initializing UI Components
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignup = findViewById(R.id.tvSignup);


        // Login Button Click
        btnLogin.setOnClickListener(v -> {
            // Get user input
            String enteredEmail = etEmail.getText().toString().trim();
            String enteredPassword = etPassword.getText().toString().trim();

            if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Check credentials from the database
                DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);
                Cursor cursor = dbHelper.getUser(enteredEmail);

                if (cursor.moveToFirst()) {
                    String savedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    if (enteredPassword.equals(savedPassword)) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Save login state
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("userEmail", enteredEmail);
                        editor.apply();

                        // Redirect to DashboardActivity
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "No account found. Please sign up first.", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });


        // Forgot Password Click
        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Forgot Password clicked", Toast.LENGTH_SHORT).show();
            // Navigate to ForgotPasswordActivity (if implemented)
            // Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            // startActivity(intent);
        });

        // Signup Text Click
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignOptionActivity.class);
            startActivity(intent);
        });

    }
}
