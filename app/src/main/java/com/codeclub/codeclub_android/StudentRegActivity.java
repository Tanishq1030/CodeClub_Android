package com.codeclub.codeclub_android;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

public class StudentRegActivity extends AppCompatActivity {
    private EditText etUsername, etPhoneNumber, etEmail, etPassword, etCollegeName;
    private Button btnSignup;
    private TextView tvSignUp;
    private Spinner spinnerYear;
    private String selectedYear = "";
    private ImageButton btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_reg);

        // Initializing UI Components
        ImageButton btnBack = findViewById(R.id.btnBack);
        etUsername = findViewById(R.id.etUsername);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etCollegeName = findViewById(R.id.etCollegeName);
        spinnerYear = findViewById(R.id.spinnerYear);
        btnSignup = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignup);

        // Initialize Spinner
        setupYearSpinner();

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
            String college = etCollegeName.getText().toString().trim();

            if (username.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || college.isEmpty() || selectedYear.isEmpty()) {
                Toast.makeText(StudentRegActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Save user details in SQLite Database
                DatabaseHelper dbHelper = new DatabaseHelper(StudentRegActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", username);
                values.put("phone", phone);
                values.put("email", email);
                values.put("password", password);
                values.put("college", college);
                values.put("year", selectedYear);

                long result = db.insert("users", null, values);
                db.close();

                if (result == -1) {
                    Toast.makeText(StudentRegActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentRegActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentRegActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

        // Login Text Click
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(StudentRegActivity.this, LoginActivity.class);
            startActivity(intent);
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
