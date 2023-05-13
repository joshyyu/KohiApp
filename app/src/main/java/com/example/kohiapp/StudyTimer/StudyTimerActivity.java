package com.example.kohiapp.StudyTimer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kohiapp.Gacha.SummonActivity;
import com.example.kohiapp.Gacha.WallpaperModel;
import com.example.kohiapp.LoadData;
import com.example.kohiapp.MainActivity;
import com.example.kohiapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class StudyTimerActivity extends AppCompatActivity {

    private TextView xTextViewCountDown, xCounterText,xButtonStartPauseText,xButtonResetText, xAppClosedText;
    private ImageButton xButtonStartPause, xButtonReset;
    private CountDownTimer xCountdownTimer;
    private boolean xTimerRunning;
    private long xTimeLeftInMillis = MainActivity.START_TIME_IN_MILLIS; // use START_TIME_IN_MILLIS from MainActivity
    private ImageView xGifStop;
    private GifImageView xGifStart;
    private ProgressBar progressBar;
    public int currentWallpaper, currentGif;
    public int counter, pointreward = MainActivity.pointreward;
    private FirebaseFirestore db;
    private long elapsedTime;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studytimer);

        configureMenuButton();

        xAppClosedText = findViewById(R.id.app_closedtxt);
        xTextViewCountDown = findViewById(R.id.textView_countdown);
        xButtonStartPause = findViewById(R.id.button_Start_Pause);
        xButtonReset = findViewById(R.id.button_Reset);
        xButtonStartPauseText = findViewById(R.id.text_Start_Pause);
        xButtonResetText = findViewById(R.id.text_Reset);
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
                xAppClosedText.setVisibility(View.INVISIBLE);
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
        xAppClosedText.setVisibility(View.INVISIBLE);
        loadData();
        updateCountDownText();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView name = findViewById(R.id.txt_username);

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

    private void startTimer() {
        xAppClosedText.setVisibility(View.INVISIBLE);
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
                xButtonStartPauseText.setText("Start");
                xButtonStartPause.setVisibility(View.INVISIBLE);
                xButtonStartPauseText.setVisibility(View.INVISIBLE);
                xButtonReset.setVisibility(View.VISIBLE);
                xButtonResetText.setVisibility(View.VISIBLE);

                xTextViewCountDown.setText("Finished!");
                xGifStop.setVisibility(View.VISIBLE);
                xGifStart.setVisibility(View.INVISIBLE);
                counter = counter + pointreward;
                updateCounterSet();
                elapsedTime = MainActivity.START_TIME_IN_MILLIS;
                saveData();
                // create a new dialog
                Dialog dialog = new Dialog(StudyTimerActivity.this);
                dialog.setContentView(R.layout.finshed_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // prevent the dialog from being dismissed when the user clicks outside of it
                dialog.setCanceledOnTouchOutside(false);

                // create a MediaPlayer object and load the sound file

                MediaPlayer mediaPlayer = MediaPlayer.create(StudyTimerActivity.this, R.raw.finshed);

                // set a listener to play the sound when the dialog is shown
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                    }
                });
                ImageButton finshbtn = dialog.findViewById(R.id.btn_main_done);
                finshbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        finish();
                    }
                });
                // show the dialog
                dialog.show();
            }
        }.start();

        xTimerRunning = true;
        xButtonStartPauseText.setText("Pause");
        xButtonReset.setVisibility(View.INVISIBLE);
        xButtonResetText.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE); // set the progress bar to visible
    }


    private void pauseTimer() {
        xAppClosedText.setVisibility(View.INVISIBLE);
        xCountdownTimer.cancel();
        xTimerRunning = false;
        xButtonStartPauseText.setText("Start");
        xButtonReset.setVisibility(View.VISIBLE);
        xButtonResetText.setVisibility(View.VISIBLE);

    }

    private void resetTimer() {
        xTimeLeftInMillis = MainActivity.START_TIME_IN_MILLIS;
        updateCountDownText();
        xButtonReset.setVisibility(View.INVISIBLE);
        xButtonResetText.setVisibility(View.INVISIBLE);
        xButtonStartPause.setVisibility(View.VISIBLE);
        xButtonStartPauseText.setVisibility(View.VISIBLE);
        progressBar.setProgress(0); // reset progress bar to 0
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

            xAppClosedText.setVisibility(View.VISIBLE);
            xButtonReset.setVisibility(View.VISIBLE);
            xButtonResetText.setVisibility(View.VISIBLE);
            xButtonStartPauseText.setVisibility(View.INVISIBLE);
            xButtonStartPause.setVisibility(View.INVISIBLE);
            xGifStop.setVisibility(View.VISIBLE);
            xGifStart.setVisibility(View.INVISIBLE);
        } else {
            xAppClosedText.setVisibility(View.VISIBLE);
            xButtonStartPauseText.setVisibility(View.INVISIBLE);
            xButtonStartPause.setVisibility(View.INVISIBLE);
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
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Saving");
//        progressDialog.setMessage("your data");
//        progressDialog.show();
        String userID = firebaseAuth.getUid();
        UserModel userCounter = new UserModel(counter, userID);
        StudyTimerModel studyTimerData = new StudyTimerModel(userID, startTime, elapsedTime);
        db.collection("users_data").document(userID).set(userCounter)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
//                        progressDialog.cancel();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.cancel();
                    }
                });
        db.collection("user_timer").add(studyTimerData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
//                        progressDialog.cancel();
                    }
    })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.cancel();
                    }
                });
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
                            updateCounterSet();
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
                            currentGif = wallpaperModel.getCurrentGif();
                            LoadData wallpaperManager = new LoadData(yConstraintLayout, currentWallpaper);
                            wallpaperManager.loadWallpaperData();

                            if (currentGif == 1){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg1_gifbread);
                                imgbtngif.setImageResource(R.drawable.bg1_pngbread);
                            }else if (currentGif == 2){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg2_gifcup);
                                imgbtngif.setImageResource(R.drawable.bg2_pngcup);
                            }else if (currentGif == 3){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg3_gifeat);
                                imgbtngif.setImageResource(R.drawable.bg3_pngeat);
                            }else if (currentGif == 4){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg4_gifdance);
                                imgbtngif.setImageResource(R.drawable.bg4_pngdance);
                            }else if (currentGif == 5){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg5_gifwork);
                                imgbtngif.setImageResource(R.drawable.bg5_pngwork);
                            }else if (currentGif == 6){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg6_giflaptop);
                                imgbtngif.setImageResource(R.drawable.bg6_giflaptop);
                            }else if (currentGif == 7){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg7_gifpopcorn);
                                imgbtngif.setImageResource(R.drawable.bg7_pngpopcorn);
                            }else if (currentGif == 8){
                                GifImageView imggif = findViewById(R.id.GifImageView_GifStart);
                                ImageView imgbtngif = findViewById(R.id.ImageView_GifStop);
                                imggif.setImageResource(R.drawable.bg8_gifslap);
                                imgbtngif.setImageResource(R.drawable.bg8_pngslap);
                            }

                            else {
                                System.out.println("default");
                            }
                        }
                    }
                });
    }

    private void configureMenuButton() {
        ImageButton button_toMain = (ImageButton) findViewById(R.id.button_toMain);

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