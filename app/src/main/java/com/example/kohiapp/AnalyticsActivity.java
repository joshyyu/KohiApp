package com.example.kohiapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AnalyticsActivity extends AppCompatActivity {

    private BarChart chart;
    private FirebaseFirestore db;
    private CalendarView calendarView;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        db = FirebaseFirestore.getInstance();

        chart = findViewById(R.id.chart);
        calendarView = findViewById(R.id.calendarView);

        configureMenuButton();
        plotData();

        // Set listener for calendar view
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Format selected date
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
            selectedDate = dateFormat.format(calendar.getTime());

            // Refresh chart with filtered data
            plotData();
        });
    }

    private void plotData() {
        // Query Firestore collection for user_timer data
        Query query = db.collection("user_timer")
                .whereEqualTo("userID", FirebaseAuth.getInstance().getUid())
                .orderBy("timestamp", Query.Direction.DESCENDING);

        if (selectedDate != null) {
            // Filter data by selected date
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(new SimpleDateFormat("MM/dd/yy", Locale.US).parse(selectedDate));
                Timestamp startTimestamp = new Timestamp(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Timestamp endTimestamp = new Timestamp(calendar.getTime());
                query = query.whereGreaterThanOrEqualTo("timestamp", startTimestamp)
                        .whereLessThan("timestamp", endTimestamp);
            } catch (Exception e) {
                Log.e(TAG, "Error parsing selected date", e);
            }
        }

        query.limit(4)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Number of documents: " + task.getResult().size());
                        ArrayList<BarEntry> entries = new ArrayList<>();
                        ArrayList<String> labels = new ArrayList<>();
                        int index = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            double elapsedTime = document.getDouble("elapsedTime");
                            Timestamp timestamp = document.getTimestamp("timestamp");

                            // Format timestamp to display as a date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
                            String date = dateFormat.format(timestamp.toDate());

                            // Add entry with index as x-axis value
                            entries.add(new BarEntry(index, (float) elapsedTime));
                            labels.add(date);
                            Log.d(TAG, "elapsedTime: " + elapsedTime + ", date: " + date);
                            index++;
                        }

                        // Create dataset and set options
                        BarDataSet dataSet = new BarDataSet(entries, "User Productivity Data");
                        dataSet.setDrawIcons(false);
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        // Create BarData object and set dataset
                        BarData barData = new BarData(dataSet);

                        // Set x-axis label count and value formatter
                        chart.getXAxis().setLabelCount(labels.size());
                        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

                        // Set chart options
                        chart.setDragEnabled(true);
                        chart.setScaleEnabled(false);
                        chart.getDescription().setEnabled(false);
                        chart.setDrawGridBackground(false); // Remove grid
                        chart.setDrawBorders(true); // Add border
                        chart.setBorderColor(Color.BLACK); // Set border color

                        // Show grid lines only for y-axis
                        chart.getXAxis().setDrawGridLines(false);
                        chart.getAxisLeft().setDrawGridLines(true);
                        chart.getAxisRight().setDrawGridLines(true);

                        // Set data and refresh chart
                        chart.setData(barData);
                        chart.animateY(1000); // Add animation
                        chart.invalidate();
                    } else {
                        // Handle error
                        Log.e(TAG, "Error getting user_timer data", task.getException());
                    }
                });
    }

    private void configureMenuButton() {
        Button button_toMain = findViewById(R.id.button_toMain);

        //listener to when it's clicked
        button_toMain.setOnClickListener(view -> {
            //intent used to connect components
            startActivity(new Intent(AnalyticsActivity.this, MainActivity.class));
            finish();
        });
    }
}