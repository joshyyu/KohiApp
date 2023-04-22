package com.example.kohiapp;

public class WallpaperModel {
    private int currentWallpaper;
    private boolean backgroundWhite;
    private boolean backgroundBlue;
    private boolean backgroundMain;

    public WallpaperModel() {
        // Required empty public constructor
    }

    public WallpaperModel(int currentWallpaper, boolean backgroundWhite, boolean backgroundBlue, boolean backgroundMain) {
        this.currentWallpaper = currentWallpaper;
        this.backgroundWhite = backgroundWhite;
        this.backgroundBlue = backgroundBlue;
        this.backgroundMain = backgroundMain;
    }

    public int getCurrentWallpaper() {
        return currentWallpaper;
    }

    public boolean isBackgroundWhite() {
        return backgroundWhite;
    }

    public boolean isBackgroundBlue() {
        return backgroundBlue;
    }

    public boolean isBackgroundMain() {
        return backgroundMain;
    }
}
