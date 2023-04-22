package com.example.kohiapp;

public class UserModel {
    private int counter;
    private String uid;

    public UserModel() {
        // Required empty public constructor
    }

    public UserModel(int counter, String uid) {
        this.counter = counter;
        this.uid = uid;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
