package com.example.kohiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureMenuButton();
    }

    private void configureMenuButton() {
        Button button_toDiary = (Button) findViewById(R.id.button_toDiary);
        Button button_toStudy = (Button) findViewById(R.id.button_toStudy);
        Button button_toSummon = (Button) findViewById(R.id.button_toSummon);
        Button button_toAnalytics = (Button) findViewById(R.id.button_toAnalytics);

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
}