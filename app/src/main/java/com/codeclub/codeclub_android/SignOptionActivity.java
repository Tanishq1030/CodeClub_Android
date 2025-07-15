package com.codeclub.codeclub_android;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SignOptionActivity extends AppCompatActivity {
     private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signoption);
        // Initializing UI Components
        ImageButton btnBack = findViewById(R.id.btnBack);

        // Set click listener to go back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes SignupActivity and goes back to the previous screen
            }
        });
    }

    public void onMemberClick(View view) {
        Intent intent = new Intent(this, MemberRegActivity.class);
        startActivity(intent);
    }

    public void onAlumniClick(View view) {
        Intent intent = new Intent(this, AlumniRegActivity.class);
        startActivity(intent);
    }

    public void onStudentClick(View view) {
        Intent intent = new Intent(this, StudentRegActivity.class);
        startActivity(intent);
    }


}


