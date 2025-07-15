package com.codeclub.codeclub_android;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.codeclub.codeclub_android.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;



public class DashboardActivity extends AppCompatActivity {
    private TextView txtLogo;
    private TextView main_about;
    private ImageSlider imageSlider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Make sure this is your correct XML file

        txtLogo = findViewById(R.id.txt_logo);
        startTypewriterAnimation();
        sliderImage();
        main_about = findViewById(R.id.main_about);
        main_about.findViewById(R.id.main_about).setOnClickListener(view1 -> {
            about();
        });
    }

    // Method to show the About dialog
    private void about() {
        final Dialog dialog = new Dialog(this); // Use 'this' if in Activity, getContext() if in Fragment
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCancelable(true); // Optional: dismiss on outside touch

        ImageView btnClose = dialog.findViewById(R.id.dialocback);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // Close the dialog when close button is clicked
            }
        });

        dialog.show();
    }


    private void startTypewriterAnimation() {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        final Handler handler = new Handler();
        final String fullText = "Coding Crafters";
        final int delay = 100; // typing speed
        final int[] index = {0};

        Runnable typeWriter = new Runnable() {
            @Override
            public void run() {
                if (index[0] < fullText.length()) {
                    char c = fullText.charAt(index[0]);

                    try {
                        spannableStringBuilder.append(c);

                        if (index[0] >= 7) { // "Crafters" part
                            spannableStringBuilder.setSpan(
                                    new ForegroundColorSpan(getResources().getColor(R.color.azure_radiance)),
                                    spannableStringBuilder.length() - 1,
                                    spannableStringBuilder.length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                            spannableStringBuilder.setSpan(
                                    new StyleSpan(Typeface.NORMAL),
                                    spannableStringBuilder.length() - 1,
                                    spannableStringBuilder.length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                        } else {
                            spannableStringBuilder.setSpan(
                                    new StyleSpan(Typeface.BOLD),
                                    spannableStringBuilder.length() - 1,
                                    spannableStringBuilder.length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                        }

                        txtLogo.setText(spannableStringBuilder);
                    } catch (Exception e) {
                        Log.v("anim", "" + e);
                    }

                    index[0]++;
                    handler.postDelayed(this, delay);
                }
            }
        };

        handler.post(typeWriter);
    }

    private void sliderImage() {
        ImageSlider imageSlider = findViewById(R.id.intro_img_slider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.college, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.secretary_desk, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.campus_director, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.principal, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

}
