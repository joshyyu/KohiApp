package com.example.kohiapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class StudyTimerActivity extends AppCompatActivity {

    private TextView xTextViewCountDown, xCounterText;
    private Button xButtonStartPause, xButtonReset;
    private CountDownTimer xCountdownTimer;
    private boolean xTimerRunning;
    private long xTimeLeftInMillis = MainActivity.START_TIME_IN_MILLIS; // use START_TIME_IN_MILLIS from MainActivity
    private ImageView xGifStop;
    private GifImageView xGifStart;
    private ProgressBar progressBar;
    public int counter;
    private FirebaseFirestore db;
    private long elapsedTime;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studytimer);

        configureMenuButton();
        xTextViewCountDown = findViewById(R.id.textView_countdown);
        xButtonStartPause = findViewById(R.id.button_Start_Pause);
        xButtonReset = findViewById(R.id.button_Reset);
        xCounterText = findViewById(R.id.counter_text);
        xGifStop = findViewById(R.id.ImageView_GifStop);
        xGifStart = findViewById(R.id.GifImageView_GifStart);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax((int) MainActivity.START_TIME_IN_MILLIS);
        progressBar.setVisibility(View.VISIBLE); // set the progress bar to visible
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        xButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xTimerRunning) {
                    pauseTimer();
                    xGifStop.setVisibility(View.VISIBLE);
                    xGifStart.setVisibility(View.INVISIBLE);
                } else {
                    xGifStop.setVisibility(View.INVISIBLE);
                    xGifStart.setVisibility(View.VISIBLE);
                    startTimer();
                }
            }
        });

        xButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        loadData();
        updateCountDownText();
    }

    private void startTimer() {
        xCountdownTimer = new CountDownTimer(xTimeLeftInMillis, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                xTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                progressBar.setMax(100);
                int progress = (int) ((MainActivity.START_TIME_IN_MILLIS - millisUntilFinished) * 100 / MainActivity.START_TIME_IN_MILLIS);
                progressBar.setProgress(progress);
                startTime = MainActivity.START_TIME_IN_MILLIS;
            }

            @Override
            public void onFinish() {
                xTimerRunning = false;
                xButtonStartPause.setText("Start");
                xButtonStartPause.setVisibility(View.INVISIBLE);
                xButtonReset.setVisibility(View.VISIBLE);

                xTextViewCountDown.setText("Finished!");
                xGifStop.setVisibility(View.VISIBLE);
                xGifStart.setVisibility(View.INVISIBLE);
                counter = counter + 20;
                updateCounterSet();
                elapsedTime = MainActivity.START_TIME_IN_MILLIS;
                saveData();

            }
        }.start();

        xTimerRunning = true;
        xButtonStartPause.setText("Pause");
        xButtonReset.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE); // set the progress bar to visible
    }


    private void pauseTimer() {
        xCountdownTimer.cancel();
        xTimerRunning = false;
        xButtonStartPause.setText("Start");
        xButtonReset.setVisibility(View.VISIBLE);

    }

    private void resetTimer() {
        xTimeLeftInMillis = MainActivity.START_TIME_IN_MILLIS;
        updateCountDownText();
        xButtonReset.setVisibility(View.INVISIBLE);
        xButtonStartPause.setVisibility(View.VISIBLE);
        progressBar.setProgress((int) MainActivity.START_TIME_IN_MILLIS);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        // convert milliseconds to hours, minutes, and seconds
        int hours = (int) (xTimeLeftInMillis / (1000 * 60 * 60));
        int minutes = (int) (xTimeLeftInMillis / (1000 * 60)) % 60;
        int seconds = (int) (xTimeLeftInMillis / 1000) % 60;

        // add logic to remove hours format when the time left has only minutes
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%2d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        // set the formatted time string to the text view
        xTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // If timer is running, run cancel the countdown timer
        if (xTimerRunning) {
            xCountdownTimer.cancel();
            xTimerRunning = false;
            xButtonStartPause.setText("APP CLOSED!");
            xButtonReset.setVisibility(View.VISIBLE);
            xGifStop.setVisibility(View.VISIBLE);
            xGifStart.setVisibility(View.INVISIBLE);
        } else {
            xTextViewCountDown.setText("APP CLOSED!");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateCounterSet() {
        xCounterText.setText(String.valueOf(counter));
    }

    /*
    private void updateCounterSet(int value) {
        TextView counterView = findViewById(R.id.counter_text);
        counterView.setText(String.valueOf(value));
    }*/

    private void saveData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving");
        progressDialog.setMessage("your data");
        progressDialog.show();
        String userID = firebaseAuth.getUid();

        UserModel userCounter = new UserModel(counter, userID);
        StudyTimerModel studyTimerData = new StudyTimerModel(userID, startTime, elapsedTime);

        db.collection("users_data").document(userID).set(userCounter)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });

        db.collection("user_timer").add(studyTimerData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });

    }


    private void loadData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();
        db.collection("users_data").document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            counter = documentSnapshot.getLong("counter").intValue();
                            updateCounterSet();
                        }
                    }
                });
    }



    private void configureMenuButton() {
        Button button_toMain = (Button) findViewById(R.id.button_toMain);

        //lestiner to when its clicked
        button_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                elapsedTime =  MainActivity.START_TIME_IN_MILLIS - xTimeLeftInMillis ;
                saveData();
                finish();
            }
        });

    }

}