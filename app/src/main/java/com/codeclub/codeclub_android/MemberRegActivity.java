package com.codeclub.codeclub_android;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class MemberRegActivity extends AppCompatActivity {
    private EditText etUsername, etPhoneNumber, etEmail, etPassword;
    private Button btnSignup;
    private TextView tvSignUp;
    private Spinner spinDesignation, spinnerYear;
    private String selectedYear = "", selectedDesignation="";
    private ImageButton btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_member_reg);

        // Initializing UI Components
        ImageButton btnBack = findViewById(R.id.btnBack);
        etUsername = findViewById(R.id.etUsername);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinDesignation = findViewById(R.id.spinDesignation);
        spinnerYear = findViewById(R.id.spinnerYear);
        btnSignup = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignup);

        // Initialize Spinner
        setupYearSpinner();
        setupDesignationSpinner();

        // Set click listener to go back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes SignupActivity and goes back to the previous screen
            }
        });


        // Signup Button
        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String phone = etPhoneNumber.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() ) {
                Toast.makeText(MemberRegActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseHelper dbHelper = new DatabaseHelper(MemberRegActivity.this);
                boolean isInserted = dbHelper.registerUser(username, phone, email, password, selectedDesignation, selectedYear);

                if (isInserted) {
                    Toast.makeText(MemberRegActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MemberRegActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MemberRegActivity.this, "Error: User not registered", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Login Text Click
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MemberRegActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }


    // Setup Spinner for Designation Selection
    private void setupDesignationSpinner() {
        List<String> designations = new ArrayList<>();
        designations.add("Select Designation"); // Placeholder
        designations.add("President");
        designations.add("Vice President");
        designations.add("Secretary");
        designations.add("Vice Secretary");
        designations.add("Developer");
        designations.add("Member");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, designations);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinDesignation.setAdapter(adapter);

        // Set item selection listener
        spinDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDesignation = parent.getItemAtPosition(position).toString();
                // You can store this selection for later use
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDesignation = "Select Designation"; // Default value
            }
        });
    }


    // Setup Spinner for Academic Year Selection
    private void setupYearSpinner() {
        List<String> academicYears = new ArrayList<>();
        academicYears.add("Select Year"); // Placeholder
        academicYears.add("1st Year");
        academicYears.add("2nd Year");
        academicYears.add("3rd Year");
        academicYears.add("Final Year");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, academicYears);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerYear.setAdapter(adapter);


        // Set item selection listener
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedYear = "Select Year"; // Default value
            }
        });

    }
}
