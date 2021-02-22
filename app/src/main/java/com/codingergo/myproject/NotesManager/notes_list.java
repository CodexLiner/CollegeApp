package com.codingergo.myproject.NotesManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class notes_list extends AppCompatActivity {
    RecyclerView noteList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        noteList = findViewById(R.id.notes_rec);
        firestore =FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Recyclerview();
    }

    private void Recyclerview() {
       Query query = firestore.collection("notes");
        noteList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false));
        FirestoreRecyclerOptions<NotesModel> Notes = new FirestoreRecyclerOptions.Builder<NotesModel>()
                .setQuery(query,NotesModel.class)
                .build();
        notesAdapter = new NotesAdapter(Notes);
        noteList.setAdapter(notesAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        notesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notesAdapter.stopListening();
    }
}