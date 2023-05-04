package com.example.kohiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kohiapp.Notes.NoteActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField, mPasswordField;
    private Button mLoginButton;
    private TextView mSignupButton,mGuestButton,mSignuptxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if user is already logged in
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login_button);
        mSignupButton = findViewById(R.id.signup);
        mGuestButton = findViewById(R.id.login_as_guest);
        mSignuptxt = findViewById(R.id.signIn_txt);


        mLoginButton.setOnClickListener(v -> loginUser());

        mSignupButton.setOnClickListener(v -> {
            // navigate to signup activity
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        mSignuptxt.setOnClickListener(v -> {
            // navigate to signup activity
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        mGuestButton.setOnClickListener(v -> {
            // Sign in user anonymously
            FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Navigate to main activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to sign in anonymously.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


    private void loginUser() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Email is required.");
            mEmailField.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Password is required.");
            mPasswordField.requestFocus();
            return;
        }

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Logging in User");
        progressDialog.setMessage("in process");
        progressDialog.show();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void continueAsGuest() {
        // Anonymous login
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in as Guest");
        progressDialog.setMessage("in process");
        progressDialog.show();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity.this, "Logged in Anonymously", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressDialog.cancel();
            Toast.makeText(LoginActivity.this, "Already logged in", Toast.LENGTH_SHORT).show();
        }
    }

}



//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setTitle("Checking User");
//        progressDialog.setMessage("in process");
//        progressDialog.show();
//
//        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
//
//        if (firebaseAuth.getCurrentUser()== null){
//            firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                @Override
//                public void onSuccess(AuthResult authResult) {
//                    progressDialog.cancel();
//                    Toast.makeText(LoginActivity.this, "It worked", Toast.LENGTH_SHORT).show();
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressDialog.cancel();
//                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
//
//        }else if (firebaseAuth.getCurrentUser()!= null) {
//            firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                @Override
//                public void onSuccess(AuthResult authResult) {
//                    progressDialog.cancel();
//                    Toast.makeText(LoginActivity.this, "Logged in Annomysly", Toast.LENGTH_SHORT).show();
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressDialog.cancel();
//                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
//        }
//
//    }
//
//
//
//}