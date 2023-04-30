package com.example.kohiapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        ImageView logoView = findViewById(R.id.imageView12);
        ImageView gifView = findViewById(R.id.imageView);

        ObjectAnimator logoAnimator1 = ObjectAnimator.ofFloat(logoView, "translationY", -500f, 0f);
        logoAnimator1.setDuration(1000);
        logoAnimator1.setInterpolator(new BounceInterpolator());

        ObjectAnimator logoAnimator2 = ObjectAnimator.ofFloat(logoView, "alpha", 0f, 1f);
        logoAnimator2.setDuration(1000);

        ObjectAnimator textViewAnimator1 = ObjectAnimator.ofFloat(textView1, "translationY", -500f, 0f);
        textViewAnimator1.setDuration(50);
        textViewAnimator1.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator textViewAnimator2 = ObjectAnimator.ofFloat(textView2, "translationY", -500f, 0f);
        textViewAnimator2.setDuration(90);
        textViewAnimator2.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator textViewAnimator3 = ObjectAnimator.ofFloat(textView3, "translationY", -500f, 0f);
        textViewAnimator3.setDuration(130);
        textViewAnimator3.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator textViewAlphaAnimator1 = ObjectAnimator.ofFloat(textView1, "alpha", 0f, 1f);
        textViewAlphaAnimator1.setDuration(750);

        ObjectAnimator textViewAlphaAnimator2 = ObjectAnimator.ofFloat(textView2, "alpha", 0f, 1f);
        textViewAlphaAnimator2.setDuration(750);

        ObjectAnimator textViewAlphaAnimator3 = ObjectAnimator.ofFloat(textView3, "alpha", 0f, 1f);
        textViewAlphaAnimator3.setDuration(750);

        ObjectAnimator gifFadeInAnimator = ObjectAnimator.ofFloat(gifView, "alpha", 0f, 1f);
        gifFadeInAnimator.setDuration(500);

        ObjectAnimator gifFadeOutAnimator = ObjectAnimator.ofFloat(gifView, "alpha", 1f, 0f);
        gifFadeOutAnimator.setDuration(200);

        ObjectAnimator gifAnimator = ObjectAnimator.ofFloat(gifView, "translationX", -550f, 0f);
        gifAnimator.setDuration(1000);
        gifAnimator.setInterpolator(new DecelerateInterpolator());

        textView1.setAlpha(0f);
        textView2.setAlpha(0f);
        textView3.setAlpha(0f);
        gifView.setAlpha(0f);

        mediaPlayer = MediaPlayer.create(this, R.raw.guitar);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mediaPlayer.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(logoAnimator1).with(logoAnimator2);
        animatorSet.play(textViewAnimator1).with(textViewAlphaAnimator1).after(gifFadeInAnimator);
        animatorSet.play(textViewAnimator2).with(textViewAlphaAnimator2).after(textViewAnimator1);
        animatorSet.play(textViewAnimator3).with(textViewAlphaAnimator3).after(textViewAnimator2);
        animatorSet.play(gifFadeInAnimator).after(logoAnimator2);
        animatorSet.play(gifAnimator).after(gifFadeInAnimator);
        animatorSet.play(gifFadeOutAnimator).after(gifAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }, 800);
            }
        });
        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



}