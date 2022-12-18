package com.example.kohiapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SummonActivity extends AppCompatActivity {

    Button zsummon;
    TextView zreward_text;
    Random r;
    List<String> items = Arrays.asList("test", "cake", "pancake");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summon);

        zsummon = (Button) findViewById(R.id.button_toSummonContent);
        zreward_text = (TextView) findViewById(R.id.textView_SummonContent);

        ConstraintLayout zConstraintLayout = (ConstraintLayout) findViewById(R.id.summon_activity);



        r = new Random();

        zsummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomitem = r.nextInt(items.size());
                String randomElement = items.get(randomitem);
                zreward_text.setText(randomElement);

                System.out.println(randomElement);
                if (randomElement == "test") {
                    zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
                }
                else if (randomElement == "cake"){
                    zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));

                }
                else {
                    zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));

                    }
                }
        });

        configureMenuButton();
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
