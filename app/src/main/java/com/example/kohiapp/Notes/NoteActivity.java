package com.example.kohiapp.Notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kohiapp.Gacha.WallpaperModel;
import com.example.kohiapp.LoadData;
import com.example.kohiapp.MainActivity;
import com.example.kohiapp.R;
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
    public int currentWallpaper;
    private NoteAdapter noteAdapter;
    private List<NotesModel> notesModelList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();

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

        binding.buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this, MainActivity.class));
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
    protected void onResume() {
        super.onResume();

        getDataData();
    }

    private void getDataData() {
        ConstraintLayout yConstraintLayout = findViewById(R.id.notes_activity);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();


        db.collection("notes")
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
        db.collection("users_wallpaper_data").document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            WallpaperModel wallpaperModel = documentSnapshot.toObject(WallpaperModel.class);
                            currentWallpaper = wallpaperModel.getCurrentWallpaper();

                            LoadData wallpaperManager = new LoadData(yConstraintLayout, currentWallpaper);
                            wallpaperManager.loadWallpaperData();
                        }
                    }
                });


    }
}