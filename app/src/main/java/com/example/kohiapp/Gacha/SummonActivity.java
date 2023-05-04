package com.example.kohiapp.Gacha;

//import static com.example.kohiapp.StudyTimer.StudyTimerActivity.SHARED_PREFS;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.Glide;
import com.example.kohiapp.MainActivity;
import com.example.kohiapp.R;
import com.example.kohiapp.StudyTimer.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class SummonActivity extends AppCompatActivity {

    private int counter;
    private boolean ds1, ds2, ds3, ds4, ds5, ds6, ds7, ds8, ds9, ds10,ds11, ds12, ds13, ds14, ds15, ds16,
            ds17, ds18, ds19, ds20 ,ds21, ds22, ds23, ds24, ds25, ds26, ds27, ds28, ds29, ds30, ds31, ds32,
            ds33, ds34, ds35, ds36,dg1, dg2, dg3, dg4, dg5, dg6, dg7, dg8;
    public int currentWallpaper,currentGif;
    private FirebaseFirestore db;
    TextView zreward_text, zcounter,zsummon_text;
    ImageView zsummon_png;

    private Gacha gacha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summon);
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        zcounter = findViewById(R.id.counter_display_text);
        zreward_text = findViewById(R.id.reward_text);
        zsummon_text = findViewById(R.id.txt_summon);
        zsummon_png = findViewById(R.id.png_reward);



        ImageButton zsummon = (ImageButton) findViewById(R.id.btn_summon);

        String userId = firebaseAuth.getUid();

        gacha = new Gacha(userId);

        loadData();
        configureMenuButton();
    }

        private void saveData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();
        UserModel studyTimerData = new UserModel(counter, userID);
        WallpaperModel wallpaperModel = new WallpaperModel(currentWallpaper,currentGif, ds1, ds2, ds3, ds4, ds5, ds6, ds7, ds8, ds9, ds10,ds11, ds12, ds13, ds14, ds15, ds16,
                ds17, ds18, ds19, ds20 ,ds21, ds22, ds23, ds24, ds25, ds26, ds27, ds28, ds29, ds30, ds31, ds32,
                ds33, ds34, ds35, ds36,dg1, dg2, dg3, dg4, dg5, dg6, dg7, dg8);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users_data").document(userID).set(studyTimerData);
        firebaseFirestore.collection("users_wallpaper_data").document(userID).set(wallpaperModel);
    }

    private void loadData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();
        TextView name = findViewById(R.id.username_txt);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Set the user's full name if available, or UID if not
        if (user != null) {
            if (user.isAnonymous()) {
                name.setText(user.getUid());
            } else {
                if (user.getDisplayName() != null) {
                    name.setText(user.getDisplayName());
                } else {
                    name.setText(user.getEmail());
                }
            }
        }

        db.collection("users_data").document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            counter = documentSnapshot.getLong("counter").intValue();
                            zcounter.setText(String.valueOf(counter)); // update the TextView with the loaded counter value
                        }
                    }
                });

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
                        }
                    }
                });
    }

    private void configureMenuButton() {
        ImageButton back_btn = (ImageButton) findViewById(R.id.back_btn);

        //lestiner to when its clicked
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save before leaving
                saveData();
                //intent used to connect components
                startActivity(new Intent(SummonActivity.this, MainActivity.class));
                finish();
            }
        });

        ImageButton zsummon = (ImageButton) findViewById(R.id.btn_summon);
        zsummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(currentWallpaper);
                if (counter >= 1) {

                    counter--;
                    zcounter.setText(String.valueOf(counter));

                    // create a new dialog
                    Dialog dialog = new Dialog(SummonActivity.this);
                    dialog.setContentView(R.layout.gacha_layout);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // prevent the dialog from being dismissed when the user clicks outside of it
                    dialog.setCanceledOnTouchOutside(false);

                    //Randomise a sound too

                    int[] soundIds = {R.raw.gachafantasy, R.raw.gachabc, R.raw.gachapiano,R.raw.happy};
                    int randomIndexSound = new Random().nextInt(soundIds.length);
                    int selectedSoundId = soundIds[randomIndexSound];

                    // play the sound
                    MediaPlayer mediaPlayer = MediaPlayer.create(SummonActivity.this, selectedSoundId);
                    mediaPlayer.start();

                    //Randomise the gacha gif to make it different every time
                    int[] gifIds = {R.drawable.gacharoll, R.drawable.gacharoll2};
                    int randomIndexGif = new Random().nextInt(gifIds.length);
                    int selectedGifId = gifIds[randomIndexGif];

                    // load the animation GIF into the GifImageView in the dialog
                    GifImageView animationView = dialog.findViewById(R.id.animation_view);
                    Glide.with(SummonActivity.this).asGif().load(selectedGifId).listener(new RequestListener<GifDrawable>() {
                        private boolean hasPlayedOnce = false;

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        //using glide to set the gif to play only once
                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            resource.setLoopCount(1); // set repeat count to 1
                            resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                                @Override
                                public void onAnimationEnd(Drawable drawable) {
                                    super.onAnimationEnd(drawable);
                                    if (!hasPlayedOnce) {
                                        hasPlayedOnce = true;
                                        // dismiss the dialog after a delay
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                reward();
                                            }
                                        }, 5);
                                    } else {
                                        dialog.dismiss();
                                        reward();
                                    }
                                }
                            });
                            return false;
                        }
                    }).into(animationView);

                    // show the dialog
                    dialog.show();


                }
                else {
                    zreward_text.setText("No Points ):");
                }

            }
        });

    }

    public void reward(){

        ImageButton zsummon = (ImageButton) findViewById(R.id.btn_summon);

        int randomNumber = (int) (Math.random() * 10) + 1;
        if (randomNumber <= 4) {

            gacha.play();
            String rewardGift = gacha.getRewardGift();

            if (rewardGift.equals("ds1")) {
                ds1 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign1_beige);
            } else if (rewardGift.equals("ds2")) {
                ds2 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign2_heart);
            } else if (rewardGift.equals("ds3")) {
                ds3 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign3_grass);
            } else if (rewardGift.equals("ds4")) {
                ds4 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign4_picnic);
            } else if (rewardGift.equals("ds5")) {
                ds5 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign5_flowerbloom);
            } else if (rewardGift.equals("ds6")) {
                ds6 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign6_pastelpink);
            } else if (rewardGift.equals("ds7")) {
                ds7 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign7_picnicbw);
            } else if (rewardGift.equals("ds8")) {
                ds8 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign8_picnicpink);
            } else if (rewardGift.equals("ds9")) {
                ds9 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign9_flowerverticalbloom);
            } else if (rewardGift.equals("ds10")) {
                ds10 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign10_bear);
            } else if (rewardGift.equals("ds11")) {
                ds11 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign11_picniccookie);
            } else if (rewardGift.equals("ds12")) {
                ds12 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign12_picnicheart);
            } else if (rewardGift.equals("ds13")) {
                ds13 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign13_picnicbrown);
            } else if (rewardGift.equals("ds14")) {
                ds14 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign14_picnicclothes);
            } else if (rewardGift.equals("ds15")) {
                ds15 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign15_pastelspill);
            } else if (rewardGift.equals("ds16")) {
                ds16 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign16_leafbrown);
            } else if (rewardGift.equals("ds17")) {
                ds17 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign17_pasteldot);
            } else if (rewardGift.equals("ds18")) {
                ds18 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign18_spiralpicnic);
            } else if (rewardGift.equals("ds19")) {
                ds19 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign19_pastelflower);
            } else if (rewardGift.equals("ds20")) {
                ds20 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign20_pastelcoffeespill);
            } else if (rewardGift.equals("ds21")) {
                ds21 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign21_bear);
            } else if (rewardGift.equals("ds22")) {
                ds22 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign22_pastelcloud);
            } else if (rewardGift.equals("ds23")) {
                ds23 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign23_pastelspillborder);
            } else if (rewardGift.equals("ds24")) {
                ds24 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign24_pastelspillmix);
            } else if (rewardGift.equals("ds25")) {
                ds25 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign25_pastellayers);
            } else if (rewardGift.equals("ds26")) {
                ds26 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign26_pastelmixdots);
            } else if (rewardGift.equals("ds27")) {
                ds27 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign27_coffeepastelspillwave);
            } else if (rewardGift.equals("ds28")) {
                ds28 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign28_pastelflowers);
            } else if (rewardGift.equals("ds29")) {
                ds29 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign29pastelcoffeespillsides);
            } else if (rewardGift.equals("ds30")) {
                ds30 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign30_picnicpastels);
            } else if (rewardGift.equals("ds31")) {
                ds31 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign31_pasteldot);
            } else if (rewardGift.equals("ds32")) {
                ds32 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign32_pastelbrownlinebg);
            } else if (rewardGift.equals("ds33")) {
                ds33 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign33_coffeemix);
            } else if (rewardGift.equals("ds34")) {
                ds34 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign34_darkpastelmix);
            } else if (rewardGift.equals("ds35")) {
                ds35 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign35_pastelverticalspill);
            } else if (rewardGift.equals("ds36")) {
                ds36 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bgdesign36_flowerdandolions);
            }else if (rewardGift.equals("dg1")) {
                dg1 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg1_pngbread);
            } else if (rewardGift.equals("dg2")) {
                dg2 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg2_pngcup);
            } else if (rewardGift.equals("dg3")) {
                dg3 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg3_pngeat);
            } else if (rewardGift.equals("dg4")) {
                dg4 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg4_pngdance);
            } else if (rewardGift.equals("dg5")) {
                dg5 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg5_pngwork);
            } else if (rewardGift.equals("dg6")) {
                dg6 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg6_pnglaptop);
            } else if (rewardGift.equals("dg7")) {
                dg7 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg7_pngpopcorn);
            } else if (rewardGift.equals("dg8")) {
                ds8 = true;
                zreward_text.setText("You Won! Check the design page ");
                zsummon_png.setImageResource(R.drawable.bg8_pngslap);
            }
            else {
                zreward_text.setText("No More rewards");
                zsummon.setVisibility(View.GONE);
                zsummon_text.setVisibility(View.GONE);
                zsummon_png.setVisibility(View.GONE);
            }
            System.out.println(rewardGift);
        }else {
            zreward_text.setText("Try Again ):");
        } saveData();

    }

    @Override
    protected void onStop() {
        super.onStop();

        saveData();
    }


}

//code testing


//    private void saveData() {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        String userID = firebaseAuth.getUid();
//        UserModel studyTimerData = new UserModel(counter, userID);
//        WallpaperModel wallpaperModel = new WallpaperModel(currentWallpaper, ds1, ds2, ds3);
//        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseFirestore.collection("users_data").document(userID).set(studyTimerData);
//        firebaseFirestore.collection("users_wallpaper_data").document(userID).set(wallpaperModel);
//    }
//
//
//    private void loadData() {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        String userID = firebaseAuth.getUid();
//        ConstraintLayout zConstraintLayout = findViewById(R.id.summon_activity);
//
//
//        db.collection("users_data").document(userID)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            counter = documentSnapshot.getLong("counter").intValue();
//                            zcounter.setText(String.valueOf(counter)); // update the TextView with the loaded counter value
//                        }
//                    }
//                });

//        db.collection("users_wallpaper_data").document(userID)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            WallpaperModel wallpaperModel = documentSnapshot.toObject(WallpaperModel.class);
//                            currentWallpaper = wallpaperModel.getCurrentWallpaper();
//                            backgroundWhite = wallpaperModel.isBackgroundWhite();
//                            backgroundBlue = wallpaperModel.isBackgroundBlue();
//                            backgroundMain = wallpaperModel.isBackgroundMain();
//
//                            if (currentWallpaper == 1) {
//                                zConstraintLayout.setBackgroundResource(R.drawable.pastelbrownlinebg);
//                            }
//                            else if (currentWallpaper == 2) {
//                                zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
//                                currentWallpaper = 2;
//                            }
//                            else if (currentWallpaper == 3){
//                                zConstraintLayout.setBackgroundColor(getResources().getColor(R.color.main));
//                                currentWallpaper = 3;
//                            }
//                            else {
//                                zConstraintLayout.setBackgroundResource(R.drawable.pastelcoffeespill);
//                            }
//                        }
//                    }
//                });
//    }

//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        counter = sharedPreferences.getInt("counter", 0);
//        backgroundWhite = sharedPreferences.getBoolean("whiteBg",true );
//        backgroundBlue = sharedPreferences.getBoolean("blueBg",true );
//        backgroundMain = sharedPreferences.getBoolean("mainBg",true );
//        currentWallpaper = sharedPreferences.getInt("currentWallpaper",0);
//        zcounter.setText(String.valueOf(counter));
//
//    }

//    public void saveData() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        String counterValue = zcounter.getText().toString();
//        int counterInt = Integer.parseInt(counterValue);
//
//        editor.putInt("currentWallpaper", currentWallpaper);
//        editor.apply();
//
//        editor.putInt("counter", counterInt);
//        editor.apply();
//
//        editor.putBoolean("whiteBg", backgroundWhite);
//        editor.apply();
//
//        editor.putBoolean("blueBg", backgroundBlue);
//        editor.apply();
//
//        editor.putBoolean("mainBg", backgroundMain);
//        editor.apply();
//
//    }