package com.example.kohiapp;

//import static com.example.kohiapp.StudyTimerActivity.SHARED_PREFS;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kohiapp.Notes.NoteActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int currentWallpaper;
    public static long START_TIME_IN_MILLIS = 30 * 60 * 1000; // initialize to a default value

    private static final int[] WALLPAPER_RESOURCES = {
            R.drawable.pastelcoffeespill,
            R.drawable.pastelbrownlinebg,
            R.color.teal_200,
            R.color.main,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout yConstraintLayout = findViewById(R.id.activity_main);
        TextView name = findViewById(R.id.username);

        configureMenuButton();
//        loadData();

        int wallpaperIndex = Math.max(0, Math.min(currentWallpaper, WALLPAPER_RESOURCES.length - 1));
        yConstraintLayout.setBackgroundResource(WALLPAPER_RESOURCES[wallpaperIndex]);

        // Retrieve the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Set the user's full name if available, or UID if not
        if (user != null) {
            if (user.isAnonymous()) {
                name.setText(user.getUid());
            } else {
                if (user.getDisplayName() != null) {
                    name.setText(user.getDisplayName());
                } else {
                    name.setText(user.getEmail());
                }
            }
        }
    }


    private void configureMenuButton() {
        ImageButton button_toDiary = findViewById(R.id.button_toDiary);
        ImageButton button_toSummon = findViewById(R.id.button_toSummon);


        int[] buttonIds = {
                R.id.btn30min, R.id.btn1hour, R.id.btn90min,
                R.id.btn2hour, R.id.btn150min, R.id.btn3hour
        };
        long[] buttonTimes = {1, 60, 90, 120, 150, 180};

        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton button_toTimer = findViewById(buttonIds[i]);
            final long time = buttonTimes[i];

            button_toTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    START_TIME_IN_MILLIS = time * 20 * 100;
                    startActivity(new Intent(MainActivity.this, StudyTimerActivity.class));
                }
            });
        }

        button_toDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
                finish();
            }
        });

        button_toSummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SummonActivity.class));
                finish();
            }
        });

        ImageButton button_toAnalytics = findViewById(R.id.button_toAnalytics);
        button_toAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AnalyticsActivity.class));
                finish();
            }
        });

        ImageButton buttonToSettings = findViewById(R.id.button_toSettings);
        buttonToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                finish();
            }
        });

  }

//    public void saveData() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("currentWallpaper", currentWallpaper);
//        editor.apply();
//    }
//
//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        currentWallpaper = sharedPreferences.getInt("currentWallpaper", 0);
//    }
}
