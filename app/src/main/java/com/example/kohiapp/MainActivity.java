package com.example.kohiapp;

import static com.example.kohiapp.StudyTimerActivity.SHARED_PREFS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    public static int currentWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout yConstraintLayout = findViewById(R.id.activity_main);

        configureMenuButton();
        loadData();

        if (currentWallpaper == 1) {
            yConstraintLayout.setBackgroundResource(R.drawable.brownlinebg);
        }
        else if (currentWallpaper == 2)
        {
            yConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
        }
        else if (currentWallpaper == 3) {
            yConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));
        }

        else {
            yConstraintLayout.setBackgroundResource(R.drawable.pastelcoffeespill);
        }

    }

    private void configureMenuButton() {
        ImageButton button_toDiary = (ImageButton) findViewById(R.id.button_toDiary);
        ImageButton button_toStudy = (ImageButton) findViewById(R.id.btn30min);
        ImageButton button_toSummon = (ImageButton) findViewById(R.id.button_toSummon);
        ImageButton button_toAnalytics = (ImageButton) findViewById(R.id.button_toAnalytics);

        //lestiner to when its clicked
        button_toDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                startActivity(new Intent(MainActivity.this, DiaryActivity.class));
            }
        });

        button_toStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                startActivity(new Intent(MainActivity.this, StudyTimerActivity.class));
            }
        });

        button_toSummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                startActivity(new Intent(MainActivity.this, SummonActivity.class));
                finish();
            }
        });

        button_toAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                startActivity(new Intent(MainActivity.this, AnalyticsActivity.class));
            }
        });

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        currentWallpaper = sharedPreferences.getInt("currentWallpaper",0);
    }



}