package com.example.kohiapp;

//import static com.example.kohiapp.StudyTimer.StudyTimerActivity.SHARED_PREFS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kohiapp.Gacha.DesignActivity;
import com.example.kohiapp.Gacha.SummonActivity;
import com.example.kohiapp.Gacha.WallpaperModel;
import com.example.kohiapp.Notes.NoteActivity;
import com.example.kohiapp.StudyTimer.StudyTimerActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public int currentWallpaper;
    public static long START_TIME_IN_MILLIS ; // initialize to a default value
    private FirebaseFirestore db;
    private int counter;
    public  static int pointreward;
    TextView zcounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView name = findViewById(R.id.username);
        db = FirebaseFirestore.getInstance();
        zcounter = findViewById(R.id.coffeeAmmount);

        configureMenuButton();
        loadData();

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

    private void loadData() {
        ConstraintLayout yConstraintLayout = findViewById(R.id.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();

        db.collection("users_data").document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            counter = documentSnapshot.getLong("counter").intValue();
                            zcounter.setText(String.valueOf(counter)); // update the TextView with the loaded counter value
                        }
                    }
                });

        db.collection("users_wallpaper_data").document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            WallpaperModel wallpaperModel = documentSnapshot.toObject(WallpaperModel.class);
                            currentWallpaper = wallpaperModel.getCurrentWallpaper();

                            LoadData wallpaperManager = new LoadData(yConstraintLayout, currentWallpaper);
                            wallpaperManager.loadWallpaperData();
                        }
                    }
                });
    }

    private void configureMenuButton() {
        ImageButton button_toDiary = findViewById(R.id.button_toDiary);
        ImageButton button_toSummon = findViewById(R.id.button_toSummon);

        int[] buttonIds = {R.id.btn30min, R.id.btn1hour, R.id.btn90min, R.id.btn2hour, R.id.btn150min, R.id.btn3hour};
        int[] buttonRewards = {1, 3, 7, 15, 31, 62};
        int[] buttontime = {30, 60, 90, 120, 150, 180}; // set desired times in minutes


        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton button_toTimer = findViewById(buttonIds[i]);

            final int reward = buttonRewards[i];
            final int time = buttontime[i];

            button_toTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set the point reward based on the button clicked
                    pointreward = reward;

                    // Calculate the start time for the timer in milliseconds
                    START_TIME_IN_MILLIS = (long) time * 60 * 1000;

                    // Start the study timer activity
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

        ImageButton buttonToDesign = findViewById(R.id.button_toDesign);
        buttonToDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DesignActivity.class));
                finish();
            }
        });

  }
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
