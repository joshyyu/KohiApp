package com.example.kohiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kohiapp.databinding.ActivityNoteUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteUpdateActivity extends AppCompatActivity {
    private String  id,title,context;
    ActivityNoteUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNoteUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        context = intent.getStringExtra("context");

        binding.titleText.setText(title);
        binding.contextText.setText(context);

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setTitle("Deleting");
                FirebaseFirestore.getInstance().collection("notes").document(id).delete();
                finish();
            }
        });

        binding.updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=binding.titleText.getText().toString();
                context=binding.contextText.getText().toString();
                updateNote();
            }
        });
    }

    private void updateNote() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating");
        progressDialog.setMessage("your note");
        progressDialog.show();

        NotesModel notesModel = new NotesModel(id,title,context,firebaseAuth.getUid());
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("notes").document(id).set(notesModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        Toast.makeText(NoteUpdateActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NoteUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });

    }

}
