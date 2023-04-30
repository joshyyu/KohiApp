package com.example.kohiapp;

import static org.junit.Assert.assertEquals;

import com.example.kohiapp.StudyTimer.StudyTimerModel;
import com.google.firebase.Timestamp;

import org.junit.Test;

public class StudyTimerModelTest {

    /**
     * Should set the timestamp to the given value
     */
    @Test
    public void setTimestampToGivenValue() {
        StudyTimerModel studyTimerModel = new StudyTimerModel();
        Timestamp expectedTimestamp = new Timestamp(123456789, 0);

        studyTimerModel.setTimestamp(expectedTimestamp);
        Timestamp actualTimestamp = studyTimerModel.getTimestamp();

        assertEquals(expectedTimestamp, actualTimestamp);
    }

    /**
     * Should set the elapsed time correctly
     */
    @Test
    public void setElapsedTime() {
        StudyTimerModel studyTimerModel = new StudyTimerModel();

        long elapsedTime = 5000;
        studyTimerModel.setElapsedTime(elapsedTime);

        assertEquals(elapsedTime, studyTimerModel.getElapsedTime());
    }
}