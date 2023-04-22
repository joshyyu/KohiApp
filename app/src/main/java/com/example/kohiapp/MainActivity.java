package com.example.kohiapp;

//import static com.example.kohiapp.StudyTimerActivity.SHARED_PREFS;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.AuthResult;
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
            R.drawable.brownlinebg,
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
        Button btnSignOut = findViewById(R.id.btn_sign_out);


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

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                if (user != null && user.isAnonymous()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Are you sure you want to delete your account? Your data will be deleted. Since you are a guest, please register as a user to save your data.")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Delete the user documents from Firestore
                                    db.collection("users_data").document(user.getUid()).delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    db.collection("user_timer").whereEqualTo("userID", user.getUid())
                                                            .get()
                                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onSuccess(QuerySnapshot querySnapshot) {
                                                                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                                                                    for (DocumentSnapshot document : documents) {
                                                                        document.getReference().delete();
                                                                    }
                                                                    db.collection("notes").whereEqualTo("uid", user.getUid())
                                                                            .get()
                                                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(QuerySnapshot querySnapshot) {
                                                                                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                                                                                    for (DocumentSnapshot document : documents) {
                                                                                        document.getReference().delete();
                                                                                    }
                                                                                    db.collection("users_wallpaper_data").document(user.getUid()).delete()
                                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void aVoid) {
                                                                                                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                                                                            finish();
                                                                                                        }
                                                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            Toast.makeText(MainActivity.this, "Failed to delete Firebase user account.", Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            })
                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    Toast.makeText(MainActivity.this, "Failed to delete user wallpaper data.", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(MainActivity.this, "Failed to delete user notes data.", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(MainActivity.this, "Failed to delete user timer data.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(MainActivity.this, "Failed to delete user data.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    // The user is not anonymous, so just sign out
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
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
