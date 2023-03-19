package com.example.kohiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kohiapp.databinding.ActivityNoteBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class NoteActivity extends AppCompatActivity {
    ActivityNoteBinding binding;
    private NoteAdapter noteAdapter;
    private List<NotesModel> notesModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesModelList=new ArrayList<>();

        noteAdapter = new NoteAdapter(this);
        binding.notesRecycler.setAdapter(noteAdapter);
        binding.notesRecycler.setLayoutManager(new LinearLayoutManager(this));

        binding.addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this, NoteAddActivity.class));
            }
        });
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.length()>0){
                    filter(text);
                }else  {
                    noteAdapter.filterList(notesModelList);
                }
            }
        });

    }

    private void filter(String text) {
        List<NotesModel> adapterList = noteAdapter.getList();
        List<NotesModel>notesModelList = new ArrayList<>();
        for (int i=0;i<adapterList.size();i++){
            NotesModel notesModel=adapterList.get(i);
            if (notesModel.getTitle().toLowerCase().contains(text.toLowerCase())||notesModel.getContext().toLowerCase().contains(text)){
                notesModelList.add(notesModel);
            }
        }
        noteAdapter.filterList(notesModelList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Checking User");
        progressDialog.setMessage("in process");
        progressDialog.show();

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()== null){
            firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressDialog.cancel();
                    Toast.makeText(NoteActivity.this, "It worked", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(NoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });

        }else if (firebaseAuth.getCurrentUser()!= null) {
            firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressDialog.cancel();
                    Toast.makeText(NoteActivity.this, "Logged in Annomysly", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(NoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();



        getDataData();
    }

    private void getDataData() {
        FirebaseFirestore.getInstance().collection("notes")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        noteAdapter.clear();
                        List<DocumentSnapshot> dlist = queryDocumentSnapshots.getDocuments();
                        for (int i=0;i<dlist.size();i++){
                            DocumentSnapshot documentSnapshot = dlist.get(i);
                            NotesModel notesModel = documentSnapshot.toObject(NotesModel.class);
                            notesModelList.add(notesModel);
                            noteAdapter.add(notesModel);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}