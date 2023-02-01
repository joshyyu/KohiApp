package com.example.kohiapp;

import static com.example.kohiapp.StudyTimerActivity.SHARED_PREFS;

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

    Button zsummon;
    TextView zreward_text,zcounter;
    Random r;
    List<String> items = Arrays.asList("test", "cake", "pancake");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summon);

        zsummon =  findViewById(R.id.button_toSummonContent);
        zreward_text =  findViewById(R.id.textView_SummonContent);
        zcounter =  findViewById(R.id.counter_display_text);

        ConstraintLayout zConstraintLayout = (ConstraintLayout) findViewById(R.id.summon_activity);

        r = new Random();

        zsummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomitem = r.nextInt(items.size());

                String randomElement = items.get(randomitem);

                if (counter >= 1) {
                    counter--;
                    zcounter.setText(String.valueOf(counter));
                    zreward_text.setText(randomElement);

                    if (randomElement.equals("test")) {
                        zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    } else if (randomElement.equals("cake")) {
                        zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));

                    } else {
                        zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));

                    }
                }
                else {
                    zreward_text.setText("No Points ):");
                }
                saveData();
            }
        });

        loadData();
        configureMenuButton();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        counter = sharedPreferences.getInt("counter", 0);
        zcounter.setText(String.valueOf(counter));
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String counterValue = zcounter.getText().toString();
        int counterInt = Integer.parseInt(counterValue);
        editor.putInt("counter", counterInt);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    private void configureMenuButton() {
        Button button_toMain = (Button) findViewById(R.id.button_toMain);

        //lestiner to when its clicked
        button_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                finish();
            }
        });
    }
}