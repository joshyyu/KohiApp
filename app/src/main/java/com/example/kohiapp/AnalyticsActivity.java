package com.example.kohiapp;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AnalyticsActivity extends AppCompatActivity {

    private LineChart chart;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        db = FirebaseFirestore.getInstance();

        chart = findViewById(R.id.chart);



        configureMenuButton();
        plotData();
    }

    private void plotData() {
        // Query Firestore collection for user_timer data
        db.collection("user_timer")
                .whereEqualTo("userID", FirebaseAuth.getInstance().getUid())
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(4)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Number of documents: " + task.getResult().size());
                        ArrayList<Entry> entries = new ArrayList<>();
                        ArrayList<String> labels = new ArrayList<>();
                        int index = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            double elapsedTime = document.getDouble("elapsedTime");
                            Timestamp timestamp = document.getTimestamp("timestamp");

                            // Format timestamp to display as a date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                            String date = dateFormat.format(timestamp.toDate());

                            // Add entry with index as x-axis value
                            entries.add(new Entry(index, (float) elapsedTime));
                            labels.add(date);
                            Log.d(TAG, "elapsedTime: " + elapsedTime + ", date: " + date);
                            index++;
                        }

                        // Create dataset and set options
                        LineDataSet dataSet = new LineDataSet(entries, "User Timer Data");
                        dataSet.setDrawIcons(false);
                        dataSet.setColor(Color.BLUE);
                        dataSet.setCircleColor(Color.BLUE);

                        // Create LineData object and set dataset
                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(dataSet);
                        LineData lineData = new LineData(dataSets);

                        // Set x-axis label count and value formatter
                        chart.getXAxis().setLabelCount(labels.size());
                        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

                        // Set chart options
                        chart.setDragEnabled(true);
                        chart.setScaleEnabled(false);
                        chart.getDescription().setEnabled(false);
                        chart.setDrawGridBackground(false);

                        // Set data and refresh chart
                        chart.setData(lineData);
                        chart.invalidate();
                    } else {
                        // Handle error
                        Log.e(TAG, "Error getting user_timer data", task.getException());
                    }
                });
    }






    private void configureMenuButton() {
        Button button_toMain = (Button) findViewById(R.id.button_toMain);

        //listener to when it's clicked
        button_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent used to connect components
                startActivity(new Intent(AnalyticsActivity.this, MainActivity.class));
                finish();
            }
        });
    }

}
