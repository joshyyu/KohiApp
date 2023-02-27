package com.example.kohiapp;

import static com.example.kohiapp.StudyTimerActivity.SHARED_PREFS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SummonActivity extends AppCompatActivity {

    public static int counter;
    public boolean backgroundWhite, backgroundBlue, backgroundMain;
    public static int currentWallpaper;

    Button zsummon, zbgWhite, zbgBlue, zbgMain, zResetTemp;
    TextView zreward_text, zcounter;
    Random r;
    List<String> items = Arrays.asList("white", "main", "blue", "Fail");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summon);

        zbgWhite = findViewById(R.id.bgWhite);
        zbgBlue = findViewById(R.id.bgBlue);
        zbgMain = findViewById(R.id.bgMain);
        zResetTemp = findViewById(R.id.TempReset);

        zsummon = findViewById(R.id.button_toSummonContent);
        zreward_text = findViewById(R.id.textView_SummonContent);
        zcounter = findViewById(R.id.counter_display_text);

        ConstraintLayout zConstraintLayout = findViewById(R.id.summon_activity);

        r = new Random();
        loadData();
        wallpaper();

        if (currentWallpaper == 1) {
            zConstraintLayout.setBackgroundResource(R.drawable.brownlinebg);
        }
        else if (currentWallpaper == 2) {
            zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
            currentWallpaper = 2;
        }
        else if (currentWallpaper == 3){
            zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));
            currentWallpaper = 3;
        }
        else {
            zConstraintLayout.setBackgroundResource(R.drawable.pastelcoffeespill);
        }


        zsummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomitem = r.nextInt(items.size());
                String randomElement = items.get(randomitem);

                System.out.println(currentWallpaper);

                if (counter >= 1) {
                    counter--;
                    zcounter.setText(String.valueOf(counter));
                    zreward_text.setText(randomElement);

                    if (randomElement.equals("white")) {
                        backgroundWhite = false;
                        wallpaper();

                    } else if (randomElement.equals("main")) {
                        backgroundMain = false;
                        wallpaper();

                    } else if (randomElement.equals("blue")) {
                        backgroundBlue = false;
                        wallpaper();
                    } else {
                        zreward_text.setText("Sorry, Try Again");
                    }
                } else {
                    zreward_text.setText("No Points ):");
                }
                saveData();

            }
        });
        configureMenuButton();

        zbgWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zConstraintLayout.setBackgroundResource(R.drawable.brownlinebg);
                currentWallpaper = 1;
                wallpaper();
            }
        });

        zbgBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
                currentWallpaper = 2;
                wallpaper();
            }
        });

        zbgMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));
                currentWallpaper = 3;
                wallpaper();
            }
        });

        zResetTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundMain = true;
                backgroundBlue = true;
                backgroundWhite = true;
                currentWallpaper = 0;
                saveData();
                wallpaper();
            }
        });
    }

    private void wallpaper() {
        if (backgroundWhite) {
            zbgWhite.setVisibility(View.INVISIBLE);
        } else {
            zbgWhite.setVisibility(View.VISIBLE);
        }
        if (backgroundBlue) {
            zbgBlue.setVisibility(View.INVISIBLE);
        } else {
            zbgBlue.setVisibility(View.VISIBLE);
        }
        if (backgroundMain) {
            zbgMain.setVisibility(View.INVISIBLE);
        } else {
            zbgMain.setVisibility(View.VISIBLE);
        }
        saveData();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        counter = sharedPreferences.getInt("counter", 0);
        backgroundWhite = sharedPreferences.getBoolean("whiteBg",true );
        backgroundBlue = sharedPreferences.getBoolean("blueBg",true );
        backgroundMain = sharedPreferences.getBoolean("mainBg",true );
        currentWallpaper = sharedPreferences.getInt("currentWallpaper",0);
        zcounter.setText(String.valueOf(counter));

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String counterValue = zcounter.getText().toString();
        int counterInt = Integer.parseInt(counterValue);

        editor.putInt("currentWallpaper", currentWallpaper);
        editor.apply();

        editor.putInt("counter", counterInt);
        editor.apply();

        editor.putBoolean("whiteBg", backgroundWhite);
        editor.apply();

        editor.putBoolean("blueBg", backgroundBlue);
        editor.apply();

        editor.putBoolean("mainBg", backgroundMain);
        editor.apply();

    }

    private void configureMenuButton() {
        Button button_toMain = (Button) findViewById(R.id.button_toMain);

        //lestiner to when its clicked
        button_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                startActivity(new Intent(SummonActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}