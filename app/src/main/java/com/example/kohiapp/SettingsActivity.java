package com.example.kohiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kohiapp.Notes.NoteActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        configureMenuButton();
    }

    private void configureMenuButton() {
        ImageButton btn_back = findViewById(R.id.back_btn);
        ImageButton btn_login = findViewById(R.id.btn_sign_out);
        ImageButton btn_signOut = findViewById(R.id.btn_sign_out);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });


        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                if (user != null && user.isAnonymous()) {
                    Dialog dialog = new Dialog(SettingsActivity.this);
                    dialog.setContentView(R.layout.setting_layout);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // prevent the dialog from being dismissed when the user clicks outside of it
                    dialog.setCanceledOnTouchOutside(false);

                    // Get the Yes and No ImageButtons from the dialog layout
                    ImageButton yesButton = dialog.findViewById(R.id.yes_button);
                    ImageButton noButton = dialog.findViewById(R.id.no_button);

                    // Set onClickListeners for the Yes and No ImageButtons

                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Delete the user documents from Firestore
                            db.collection("users_data").document(user.getUid()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            db.collection("user_timer").whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot querySnapshot) {
                                                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                                                            for (DocumentSnapshot document : documents) {
                                                                document.getReference().delete();
                                                            }
                                                            db.collection("notes").whereEqualTo("uid", user.getUid())
                                                                    .get()
                                                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(QuerySnapshot querySnapshot) {
                                                                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                                                                            for (DocumentSnapshot document : documents) {
                                                                                document.getReference().delete();
                                                                            }
                                                                            db.collection("users_wallpaper_data").document(user.getUid()).delete()
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                            db.collection("users_rewards").document(user.getUid()).collection("rewards").get()
                                                                                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(QuerySnapshot querySnapshot) {
                                                                                                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                                                                                                            for (DocumentSnapshot document : documents) {
                                                                                                                document.getReference().delete();
                                                                                                            }
                                                                                                            db.collection("users_rewards").document(user.getUid()).delete()
                                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                                            user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onSuccess(Void aVoid) {
                                                                                                                                    dialog.dismiss();
                                                                                                                                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                                                                                                                                    finish();
                                                                                                                                }
                                                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                @Override
                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                    Toast.makeText(SettingsActivity.this, "Failed to delete Firebase user account.", Toast.LENGTH_SHORT).show();
                                                                                                                                }
                                                                                                                            });
                                                                                                                        }
                                                                                                                    })
                                                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                                                        @Override
                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                            Toast.makeText(SettingsActivity.this, "Failed to delete user rewards data.", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                    });
                                                                                                        }
                                                                                                    })
                                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            Toast.makeText(SettingsActivity.this, "Failed to delete user rewards data.", Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Toast.makeText(SettingsActivity.this, "Failed to delete user wallpaper data.", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(SettingsActivity.this, "Failed to delete user notes data.", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(SettingsActivity.this, "Failed to delete user timer data.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SettingsActivity.this, "Failed to delete user data.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    // Show the custom dialog
                    dialog.show();
                } else {
                    // The user is not anonymous, so just sign out
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }
}