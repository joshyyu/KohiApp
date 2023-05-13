
package com.example.kohiapp;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kohiapp.Gacha.WallpaperModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoadData {
    private ConstraintLayout zConstraintLayout;
    private int currentWallpaper;


    public LoadData(ConstraintLayout zConstraintLayout, int currentWallpaper) {
        this.zConstraintLayout = zConstraintLayout;
        this.currentWallpaper = currentWallpaper;
    }

    public void setConstraintLayout(ConstraintLayout zConstraintLayout) {
        this.zConstraintLayout = zConstraintLayout;
    }

    public void loadWallpaperData() {

        if (currentWallpaper == 1) {
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign1_beige);
            currentWallpaper = 1;
        }
        else if (currentWallpaper == 2) {
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign2_heart);
            currentWallpaper = 2;
        }
        else if (currentWallpaper == 3){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign3_grass);
            currentWallpaper = 3;
        }
        else if (currentWallpaper == 4){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign4_picnic);
            currentWallpaper = 4;
        }
        else if (currentWallpaper == 5){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign5_flowerbloom);
            currentWallpaper = 5;
        }
        else if (currentWallpaper == 6){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign6_pastelpink);
            currentWallpaper = 6;
        }
        else if (currentWallpaper == 7){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign7_picnicbw);
            currentWallpaper = 7;
        }
        else if (currentWallpaper == 8){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign8_picnicpink);
            currentWallpaper = 8;
        }
        else if (currentWallpaper == 9){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign9_flowerverticalbloom);
            currentWallpaper = 9;
        }
        else if (currentWallpaper == 10){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign10_bear);
            currentWallpaper = 10;
        }
        else if (currentWallpaper == 11){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign11_picniccookie);
            currentWallpaper = 11;
        }
        else if (currentWallpaper == 12){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign12_picnicheart);
            currentWallpaper = 12;
        }
        else if (currentWallpaper == 13){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign13_picnicbrown);
            currentWallpaper = 13;
        }
        else if (currentWallpaper == 14){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign14_picnicclothes);
            currentWallpaper = 14;
        }
        else if (currentWallpaper == 15){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign15_pastelspill);
            currentWallpaper = 15;
        }
        else if (currentWallpaper == 16){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign16_leafbrown);
            currentWallpaper = 16;
        }
        else if (currentWallpaper == 17){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign17_pasteldot);
            currentWallpaper = 17;
        }
        else if (currentWallpaper == 18){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign18_spiralpicnic);
            currentWallpaper = 18;
        }
        else if (currentWallpaper == 19){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign19_pastelflower);
            currentWallpaper = 19;
        }
        else if (currentWallpaper == 20){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign20_pastelcoffeespill);
            currentWallpaper = 20;
        }
        else if (currentWallpaper == 21){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign21_bear);
            currentWallpaper = 21;
        }
        else if (currentWallpaper == 22){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign22_pastelcloud);
            currentWallpaper = 22;
        }
        else if (currentWallpaper == 23){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign23_pastelspillborder);
            currentWallpaper = 23;
        }
        else if (currentWallpaper == 24){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign24_pastelspillmix);
            currentWallpaper = 24;
        }
        else if (currentWallpaper == 25){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign25_pastellayers);
            currentWallpaper = 25;
        }
        else if (currentWallpaper == 26){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign26_pastelmixdots);
            currentWallpaper = 26;
        }
        else if (currentWallpaper == 27){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign27_coffeepastelspillwave);
            currentWallpaper = 27;
        }
        else if (currentWallpaper == 28){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign28_pastelflowers);
            currentWallpaper = 28;
        }
        else if (currentWallpaper == 29){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign29pastelcoffeespillsides);
            currentWallpaper = 29;
        }
        else if (currentWallpaper == 30){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign30_picnicpastels);
            currentWallpaper = 30;
        }
        else if (currentWallpaper == 31){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign31_pasteldot);
            currentWallpaper = 31;
        }
        else if (currentWallpaper == 32){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign32_pastelbrownlinebg);
            currentWallpaper = 32;
        }
        else if (currentWallpaper == 33){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign33_coffeemix);
            currentWallpaper = 33;
        }
        else if (currentWallpaper == 34){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign34_darkpastelmix);
            currentWallpaper = 34;
        }
        else if (currentWallpaper == 35){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign35_pastelverticalspill);
            currentWallpaper = 35;
        }
        else if (currentWallpaper == 36){
            zConstraintLayout.setBackgroundResource(R.drawable.bgdesign36_flowerdandolions);
            currentWallpaper = 36;
        }
        else {
            zConstraintLayout.setBackgroundResource(R.color.main);
        }
    }
}
