package com.example.kohiapp;

import com.google.firebase.Timestamp;

public class StudyTimerModel {
    private String userID;
    private long startTime;
    private long elapsedTime;
    private Timestamp timestamp;

    public StudyTimerModel() {
        // Required empty public constructor
    }

    public StudyTimerModel(String userID, long startTime, long elapsedTime) {
        this.userID = userID;
        this.startTime = startTime;
        this.elapsedTime = elapsedTime;
        this.timestamp = Timestamp.now();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}



