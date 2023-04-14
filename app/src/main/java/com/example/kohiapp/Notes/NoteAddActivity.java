package com.example.kohiapp.Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kohiapp.databinding.ActivityNoteAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class NoteAddActivity extends AppCompatActivity {
    ActivityNoteAddBinding binding;
    private String title,context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNoteAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = binding.titleText.getText().toString();
                context = binding.contextText.getText().toString();
                saveNote();

            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteAddActivity.this, NoteActivity.class));
                finish();
            }
        });

    }

    private void saveNote() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving");
        progressDialog.setMessage("your note");
        progressDialog.show();
        String noteID = UUID.randomUUID().toString();
        NotesModel notesModel = new NotesModel(noteID,title,context,firebaseAuth.getUid());
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("notes").document(noteID).set(notesModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        Toast.makeText(NoteAddActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NoteAddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });

    }
}
