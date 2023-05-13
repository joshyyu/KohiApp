package com.example.kohiapp.Gacha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.kohiapp.LoadData;
import com.example.kohiapp.MainActivity;
import com.example.kohiapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DesignActivity extends AppCompatActivity {

    public boolean ds1, ds2, ds3, ds4, ds5, ds6, ds7, ds8, ds9, ds10,ds11, ds12, ds13, ds14, ds15, ds16,
            ds17, ds18, ds19, ds20 ,ds21, ds22, ds23, ds24, ds25, ds26, ds27, ds28, ds29, ds30, ds31, ds32,
            ds33, ds34, ds35, ds36,dg1, dg2, dg3, dg4, dg5, dg6, dg7, dg8;
    private FirebaseFirestore db;
    public int currentWallpaper,currentGif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        ConstraintLayout vConstraintLayout = findViewById(R.id.design_activity);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        configureMenuButton();
        loadData();
    }

    private void loadData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();
        ConstraintLayout vConstraintLayout = findViewById(R.id.design_activity);

        db.collection("users_wallpaper_data").document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            WallpaperModel wallpaperModel = documentSnapshot.toObject(WallpaperModel.class);
                            currentGif = wallpaperModel.getCurrentGif();
                            currentWallpaper = wallpaperModel.getCurrentWallpaper();
                            ds1 = wallpaperModel.isDs1();
                            ds2 = wallpaperModel.isDs2();
                            ds3 = wallpaperModel.isDs3();
                            ds4 = wallpaperModel.isDs4();
                            ds5 = wallpaperModel.isDs5();
                            ds6 = wallpaperModel.isDs6();
                            ds7 = wallpaperModel.isDs7();
                            ds8 = wallpaperModel.isDs8();
                            ds9 = wallpaperModel.isDs9();
                            ds10 = wallpaperModel.isDs10();
                            ds11 = wallpaperModel.isDs11();
                            ds12 = wallpaperModel.isDs12();
                            ds13 = wallpaperModel.isDs13();
                            ds14 = wallpaperModel.isDs14();
                            ds15 = wallpaperModel.isDs15();
                            ds16 = wallpaperModel.isDs16();
                            ds17 = wallpaperModel.isDs17();
                            ds18 = wallpaperModel.isDs18();
                            ds19 = wallpaperModel.isDs19();
                            ds20 = wallpaperModel.isDs20();
                            ds21 = wallpaperModel.isDs21();
                            ds22 = wallpaperModel.isDs22();
                            ds23 = wallpaperModel.isDs23();
                            ds24 = wallpaperModel.isDs24();
                            ds25 = wallpaperModel.isDs25();
                            ds26 = wallpaperModel.isDs26();
                            ds27 = wallpaperModel.isDs27();
                            ds28 = wallpaperModel.isDs28();
                            ds29 = wallpaperModel.isDs29();
                            ds30 = wallpaperModel.isDs30();
                            ds31 = wallpaperModel.isDs31();
                            ds32 = wallpaperModel.isDs32();
                            ds33 = wallpaperModel.isDs33();
                            ds34 = wallpaperModel.isDs34();
                            ds35 = wallpaperModel.isDs35();
                            ds36 = wallpaperModel.isDs36();
                            dg1 = wallpaperModel.isDg1();
                            dg2 = wallpaperModel.isDg2();
                            dg3 = wallpaperModel.isDg3();
                            dg4 = wallpaperModel.isDg4();
                            dg5 = wallpaperModel.isDg5();
                            dg6 = wallpaperModel.isDg6();
                            dg7 = wallpaperModel.isDg7();
                            dg8 = wallpaperModel.isDg8();
                            wallpaper(); // Call wallpaper() method here

                            LoadData wallpaperManager = new LoadData(vConstraintLayout, currentWallpaper);
                            wallpaperManager.loadWallpaperData();

                        }
                    }
                });
    }

    private void saveData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();
        WallpaperModel wallpaperModel = new WallpaperModel(currentWallpaper,currentGif,ds1, ds2, ds3, ds4, ds5, ds6, ds7, ds8, ds9, ds10,ds11, ds12, ds13, ds14, ds15, ds16,
                ds17, ds18, ds19, ds20 ,ds21, ds22, ds23, ds24, ds25, ds26, ds27, ds28, ds29, ds30, ds31, ds32,
                ds33, ds34, ds35, ds36,dg1, dg2, dg3, dg4, dg5, dg6, dg7, dg8);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users_wallpaper_data").document(userID).set(wallpaperModel);
    }

    private void configureMenuButton() {
        ImageButton back_btn = (ImageButton) findViewById(R.id.back_btn);
        ConstraintLayout vConstraintLayout = findViewById(R.id.design_activity);

        ImageButton btn_dselected = findViewById(R.id.dgselected);
        ImageButton btn_dgDefault = findViewById(R.id.dgdefault);
        ImageButton btn_dsDefault = findViewById(R.id.dsDefault);

        btn_dgDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_dselected.setBackgroundResource(R.drawable.bg_pngdefault);
                currentGif = 0;
                saveData(); // Save the updated currentWallpaper value to Firestore
            }
        });

        btn_dsDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentWallpaper = 0;
                vConstraintLayout.setBackgroundResource(R.color.main);
                saveData(); // Save the updated currentWallpaper value to Firestore
            }
        });


        //lestiner to when its clicked
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //intent used to connect components
                startActivity(new Intent(DesignActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void wallpaper() {
        ConstraintLayout vConstraintLayout = findViewById(R.id.design_activity);
        ImageButton btn_ds1 = findViewById(R.id.ds1);
        ImageButton btn_ds2 = findViewById(R.id.ds2);
        ImageButton btn_ds3 = findViewById(R.id.ds3);
        ImageButton btn_ds4 = findViewById(R.id.ds4);
        ImageButton btn_ds5 = findViewById(R.id.ds5);
        ImageButton btn_ds6 = findViewById(R.id.ds6);
        ImageButton btn_ds7 = findViewById(R.id.ds7);
        ImageButton btn_ds8 = findViewById(R.id.ds8);
        ImageButton btn_ds9 = findViewById(R.id.ds9);
        ImageButton btn_ds10 = findViewById(R.id.ds10);
        ImageButton btn_ds11 = findViewById(R.id.ds11);
        ImageButton btn_ds12 = findViewById(R.id.ds12);
        ImageButton btn_ds13 = findViewById(R.id.ds13);
        ImageButton btn_ds14 = findViewById(R.id.ds14);
        ImageButton btn_ds15 = findViewById(R.id.ds15);
        ImageButton btn_ds16 = findViewById(R.id.ds16);
        ImageButton btn_ds17 = findViewById(R.id.ds17);
        ImageButton btn_ds18 = findViewById(R.id.ds18);
        ImageButton btn_ds19 = findViewById(R.id.ds19);
        ImageButton btn_ds20 = findViewById(R.id.ds20);
        ImageButton btn_ds21 = findViewById(R.id.ds21);
        ImageButton btn_ds22 = findViewById(R.id.ds22);
        ImageButton btn_ds23 = findViewById(R.id.ds23);
        ImageButton btn_ds24 = findViewById(R.id.ds24);
        ImageButton btn_ds25 = findViewById(R.id.ds25);
        ImageButton btn_ds26 = findViewById(R.id.ds26);
        ImageButton btn_ds27 = findViewById(R.id.ds27);
        ImageButton btn_ds28 = findViewById(R.id.ds28);
        ImageButton btn_ds29 = findViewById(R.id.ds29);
        ImageButton btn_ds30 = findViewById(R.id.ds30);
        ImageButton btn_ds31 = findViewById(R.id.ds31);
        ImageButton btn_ds32 = findViewById(R.id.ds32);
        ImageButton btn_ds33 = findViewById(R.id.ds33);
        ImageButton btn_ds34 = findViewById(R.id.ds34);
        ImageButton btn_ds35 = findViewById(R.id.ds35);
        ImageButton btn_ds36 = findViewById(R.id.ds36);

        ImageButton btn_dg1 = findViewById(R.id.dg1);
        ImageButton btn_dg2 = findViewById(R.id.dg2);
        ImageButton btn_dg3 = findViewById(R.id.dg3);
        ImageButton btn_dg4 = findViewById(R.id.dg4);
        ImageButton btn_dg5 = findViewById(R.id.dg5);
        ImageButton btn_dg6 = findViewById(R.id.dg6);
        ImageButton btn_dg7 = findViewById(R.id.dg7);
        ImageButton btn_dg8 = findViewById(R.id.dg8);

        ImageButton btn_dgDefault = findViewById(R.id.dgselected);
        ImageButton btn_gselected = findViewById(R.id.dgselected);

        if (ds1 == true) {
            btn_ds1.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign1_beige));
            btn_ds1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign1_beige);
                    currentWallpaper = 1;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds2 == true) {
            btn_ds2.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign2_heart));
            btn_ds2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign2_heart);
                    currentWallpaper = 2;
                    currentWallpaper = currentWallpaper;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds3 == true) {
            btn_ds3.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign3_grass));
            btn_ds3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign3_grass);
                    currentWallpaper = 3;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }

            });
        } else {
            btn_ds3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds4 == true) {
            btn_ds4.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign4_picnic));
            btn_ds4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign4_picnic);
                    currentWallpaper = 4;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds5 == true) {
            btn_ds5.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign5_flowerbloom));
            btn_ds5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign5_flowerbloom);
                    currentWallpaper = 5;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds6 == true) {
            btn_ds6.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign6_pastelpink));
            btn_ds6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign6_pastelpink);
                    currentWallpaper = 6;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds7 == true) {
            btn_ds7.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign7_picnicbw));
            btn_ds7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign7_picnicbw);
                    currentWallpaper = 7;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds8 == true) {
            btn_ds8.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign8_picnicpink));
            btn_ds8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign8_picnicpink);
                    currentWallpaper = 8;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds9 == true) {
            btn_ds9.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign9_flowerverticalbloom));
            btn_ds9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign9_flowerverticalbloom);
                    currentWallpaper = 9;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds10 == true) {
            btn_ds10.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign10_bear));
            btn_ds10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign10_bear);
                    currentWallpaper = 10;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds11 == true) {
            btn_ds11.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign11_picniccookie));
            btn_ds11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign11_picniccookie);
                    currentWallpaper = 11;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds12 == true) {
            btn_ds12.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign12_picnicheart));
            btn_ds12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign12_picnicheart);
                    currentWallpaper = 12;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds13 == true) {
            btn_ds13.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign13_picnicbrown));
            btn_ds13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign13_picnicbrown);
                    currentWallpaper = 13;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds14 == true) {
            btn_ds14.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign14_picnicclothes));
            btn_ds14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign14_picnicclothes);
                    currentWallpaper = 14;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds15 == true) {
            btn_ds15.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign15_pastelspill));
            btn_ds15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign15_pastelspill);
                    currentWallpaper = 15;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds16 == true) {
            btn_ds16.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign16_leafbrown));
            btn_ds16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign16_leafbrown);
                    currentWallpaper = 16;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds17 == true) {
            btn_ds17.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign17_pasteldot));
            btn_ds17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign17_pasteldot);
                    currentWallpaper = 17;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds18 == true) {
            btn_ds18.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign18_spiralpicnic));
            btn_ds18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign18_spiralpicnic);
                    currentWallpaper = 18;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds19 == true) {
            btn_ds19.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign19_pastelflower));
            btn_ds19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign19_pastelflower);
                    currentWallpaper = 19;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds20 == true) {
            btn_ds20.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign20_pastelcoffeespill));
            btn_ds20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign20_pastelcoffeespill);
                    currentWallpaper = 20;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds21 == true) {
            btn_ds21.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign21_bear));
            btn_ds21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign21_bear);
                    currentWallpaper = 21;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds22 == true) {
            btn_ds22.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign22_pastelcloud));
            btn_ds22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign22_pastelcloud);
                    currentWallpaper = 22;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds23 == true) {
            btn_ds23.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign23_pastelspillborder));
            btn_ds23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign23_pastelspillborder);
                    currentWallpaper = 23;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds24 == true) {
            btn_ds24.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign24_pastelspillmix));
            btn_ds24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign24_pastelspillmix);
                    currentWallpaper = 24;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds25 == true) {
            btn_ds25.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign25_pastellayers));
            btn_ds25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign25_pastellayers);
                    currentWallpaper = 25;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds26 == true) {
            btn_ds26.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign26_pastelmixdots));
            btn_ds26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign26_pastelmixdots);
                    currentWallpaper = 26;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds27 == true) {
            btn_ds27.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign27_coffeepastelspillwave));
            btn_ds27.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign27_coffeepastelspillwave);
                    currentWallpaper = 27;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds27.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds28 == true) {
            btn_ds28.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign28_pastelflowers));
            btn_ds28.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign28_pastelflowers);
                    currentWallpaper = 28;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds28.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds29 == true) {
            btn_ds29.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign29pastelcoffeespillsides));
            btn_ds29.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign29pastelcoffeespillsides);
                    currentWallpaper = 29;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds29.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds30 == true) {
            btn_ds30.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign30_picnicpastels));
            btn_ds30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign30_picnicpastels);
                    currentWallpaper = 30;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds31 == true) {
            btn_ds31.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign31_pasteldot));
            btn_ds31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign31_pasteldot);
                    currentWallpaper = 31;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds32 == true) {
            btn_ds32.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign32_pastelbrownlinebg));
            btn_ds32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign32_pastelbrownlinebg);
                    currentWallpaper = 32;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds33 == true) {
            btn_ds33.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign33_coffeemix));
            btn_ds33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign33_coffeemix);
                    currentWallpaper = 33;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }


        if (ds34 == true) {
            btn_ds34.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign34_darkpastelmix));
            btn_ds34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign34_darkpastelmix);
                    currentWallpaper = 34;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (ds35 == true) {
            btn_ds35.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign35_pastelverticalspill));
            btn_ds35.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign35_pastelverticalspill);
                    currentWallpaper = 35;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds35.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }


        if (ds36 == true) {
            btn_ds36.setBackground(ContextCompat.getDrawable(this, R.drawable.bgdesign36_flowerdandolions));
            btn_ds36.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vConstraintLayout.setBackgroundResource(R.drawable.bgdesign36_flowerdandolions);
                    currentWallpaper = 36;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_ds31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }


        if (dg1== true) {
            btn_dg1.setBackground(ContextCompat.getDrawable(this, R.drawable.bg1_pngbread));
            btn_dg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg1_pngbread);
                    currentGif = 1;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }


        if (dg2 == true) {
            btn_dg2.setBackground(ContextCompat.getDrawable(this, R.drawable.bg2_pngcup));
            btn_dg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg2_pngcup);
                    currentGif = 2;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (dg3 == true) {
            btn_dg3.setBackground(ContextCompat.getDrawable(this, R.drawable.bg3_pngeat));
            btn_dg3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg3_pngeat);
                    currentGif = 3;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }


        if (dg4 == true) {
            btn_dg4.setBackground(ContextCompat.getDrawable(this, R.drawable.bg4_pngdance));
            btn_dg4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg4_pngdance);
                    currentGif = 4;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (dg5 == true) {
            btn_dg5.setBackground(ContextCompat.getDrawable(this, R.drawable.bg5_pngwork));
            btn_dg5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg5_pngwork);
                    currentGif = 5;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (dg6 == true) {
            btn_dg6.setBackground(ContextCompat.getDrawable(this, R.drawable.bg6_pnglaptop));
            btn_dg6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg6_pnglaptop);
                    currentGif = 6;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (dg7 == true) {
            btn_dg7.setBackground(ContextCompat.getDrawable(this, R.drawable.bg7_pngpopcorn));
            btn_dg7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg7_pngpopcorn);
                    currentGif = 7;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (dg8 == true) {
            btn_dg8.setBackground(ContextCompat.getDrawable(this, R.drawable.bg8_pngslap));
            btn_dg8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_gselected.setBackgroundResource(R.drawable.bg8_pngslap);
                    currentWallpaper = 8;
                    saveData(); // Save the updated currentWallpaper value to Firestore
                }
            });
        }else {
            btn_dg8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DesignActivity.this, SummonActivity.class));
                    finish();
                }
            });
        }

        if (currentGif == 1) {
            btn_dgDefault.setBackgroundResource(R.drawable.bg1_pngbread);
        }else if (currentGif == 2 ){
            btn_dgDefault.setBackgroundResource(R.drawable.bg2_pngcup);
        }else if (currentGif == 3 ){
            btn_dgDefault.setBackgroundResource(R.drawable.bg3_pngeat);
        }else if (currentGif == 4 ){
            btn_dgDefault.setBackgroundResource(R.drawable.bg4_pngdance);
        }else if (currentGif == 5 ){
            btn_dgDefault.setBackgroundResource(R.drawable.bg5_pngwork);
        }else if (currentGif == 6 ){
            btn_dgDefault.setBackgroundResource(R.drawable.bg6_pnglaptop);
        }else if (currentGif == 7 ){
            btn_dgDefault.setBackgroundResource(R.drawable.bg7_pngpopcorn);
        }else if (currentGif == 8 ){
            btn_dgDefault.setBackgroundResource(R.drawable.bg8_pngslap);
        }else {
            btn_dgDefault.setBackgroundResource(R.drawable.bg_pngdefault);
        }
    }
}

//        } else {
//            zbgWhite.setVisibility(View.VISIBLE);
//        }
//        if (ds2) {
//            zbgBlue.setVisibility(View.INVISIBLE);
//        } else {
//            zbgBlue.setVisibility(View.VISIBLE);
//        }
//        if (ds3) {
//            zbgMain.setVisibility(View.INVISIBLE);
//        } else {
//            zbgMain.setVisibility(View.VISIBLE);
//        }

//    zbgWhite = findViewById(R.id.bgWhite);
//        zbgBlue = findViewById(R.id.bgBlue);
//        zbgMain = findViewById(R.id.bgMain);
//        zResetTemp = findViewById(R.id.TempReset);

//        ConstraintLayout zConstraintLayout = findViewById(R.id.summon_activity);
//
//        if (currentWallpaper == 1) {
//            zConstraintLayout.setBackgroundResource(R.drawable.pastelbrownlinebg);
//        }
//        else if (currentWallpaper == 2) {
//            zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
//            currentWallpaper = 2;
//        }
//        else if (currentWallpaper == 3){
//            zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));
//            currentWallpaper = 3;
//        }
//        else {
//            zConstraintLayout.setBackgroundResource(R.drawable.pastelcoffeespill);
//        }

//        zbgWhite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zConstraintLayout.setBackgroundResource(R.drawable.pastelbrownlinebg);
//                currentWallpaper = 1;
//                wallpaper();
//            }
//        });
//
//        zbgBlue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
//                currentWallpaper = 2;
//                wallpaper();
//            }
//        });
//
//        zbgMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));
//                currentWallpaper = 3;
//                wallpaper();
//            }
//        });
//
//        zResetTemp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zConstraintLayout.setBackgroundResource(R.drawable.pastelcoffeespill);
//                backgroundMain = true;
//                backgroundBlue = true;
//                backgroundWhite = true;
//                currentWallpaper = 0;
//                saveData();
//                wallpaper();
//            }
//        });
