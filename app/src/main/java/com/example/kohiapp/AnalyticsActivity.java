package com.example.kohiapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.kohiapp.Gacha.WallpaperModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AnalyticsActivity extends AppCompatActivity {

    public int currentWallpaper;
    private BarChart chart;
    private LineChart linechart;
    private FirebaseFirestore db;
    private CalendarView calendarView;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        db = FirebaseFirestore.getInstance();

        chart = findViewById(R.id.chart);
        linechart = findViewById(R.id.linechart);
        calendarView = findViewById(R.id.calendarView);

        configureMenuButton();
        plotData();
        plotLineChart();
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

    private void plotLineChart() {
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
                        ArrayList<Entry> entries = new ArrayList<>();
                        ArrayList<String> labels = new ArrayList<>();
                        int index = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            double elapsedTime = document.getDouble("elapsedTime");
                            Timestamp timestamp = document.getTimestamp("timestamp");
                            double elapsedTimeMinutes = elapsedTime / 60000;

                            // Format timestamp to display as a date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
                            String date = dateFormat.format(timestamp.toDate());

                            // Add entry with index as x-axis value
                            entries.add(new Entry(index, (float) elapsedTimeMinutes));
                            labels.add(date);
                            Log.d(TAG, "elapsedTime: " + elapsedTimeMinutes + ", date: " + date);
                            index++;
                        }

                        // Create dataset and set options
                        LineDataSet dataSet = new LineDataSet(entries, "User Productivity Data");
                        dataSet.setDrawIcons(false);
                        int[] colors = new int[] {
                                ContextCompat.getColor(this, R.color.purple_500),
                                ContextCompat.getColor(this, R.color.burnt_orange),
                                ContextCompat.getColor(this, R.color.dark_blue),
                                ContextCompat.getColor(this, R.color.olive_green)
                        };
                        dataSet.setColors(colors);

                        // Create LineData object and set dataset
                        LineData lineData = new LineData(dataSet);

                        // Set x-axis label count and value formatter
                        linechart.getXAxis().setLabelCount(labels.size());
                        linechart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

                        // Set chart options
                        linechart.setDragEnabled(true);
                        linechart.setScaleEnabled(false);
                        linechart.getDescription().setEnabled(false);
                        linechart.setDrawGridBackground(false); // Remove grid
                        linechart.setDrawBorders(true); // Add border
                        linechart.setBorderColor(Color.BLACK); // Set border color

                        // Show grid lines only for y-axis
                        linechart.getXAxis().setDrawGridLines(false);
                        linechart.getAxisLeft().setDrawGridLines(true);
                        linechart.getAxisRight().setDrawGridLines(true);

                        // Set data and refresh chart
                        linechart.setData(lineData);
                        linechart.animateY(1000); // Add animation
                        linechart.invalidate();
                    } else {
                        // Handle error
                        Log.e(TAG, "Error getting user_timer data", task.getException());
                    }
                });
    }



    private void plotData() {
        ConstraintLayout yConstraintLayout = findViewById(R.id.analytics_activity);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();


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
                            double elapsedTimeMinutes = elapsedTime / 60000;


                            // Format timestamp to display as a date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
                            String date = dateFormat.format(timestamp.toDate());

                            // Add entry with index as x-axis value
                            entries.add(new BarEntry(index, (float) elapsedTimeMinutes));
                            labels.add(date);
                            Log.d(TAG, "elapsedTime: " + elapsedTimeMinutes + ", date: " + date);
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
        ImageButton button_toMain = findViewById(R.id.btn_main);

        //listener to when it's clicked
        button_toMain.setOnClickListener(view -> {
            //intent used to connect components
            startActivity(new Intent(AnalyticsActivity.this, MainActivity.class));
            finish();
        });
    }
}