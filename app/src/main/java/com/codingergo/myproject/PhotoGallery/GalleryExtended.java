package com.codingergo.myproject.PhotoGallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GalleryExtended extends AppCompatActivity {
    RecyclerView recyclerView;
    fireAdapter fireadapter;
    imageAdapter img;
    imageModel imagemodel;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_extended);
        recyclerView = findViewById(R.id.galleryview);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView.setLayoutManager(new GridLayoutManager(this,4,GridLayoutManager.VERTICAL,false));
        Query query = firestore.collection("Gallery");
        FirestoreRecyclerOptions<imageModel> options = new FirestoreRecyclerOptions.Builder<imageModel>()
                .setQuery(query , imageModel.class)
                .build();
        img = new imageAdapter(options);
        recyclerView.setAdapter(img);

    }

    @Override
    protected void onStart() {
        super.onStart();
        img.startListening();
       // fireadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        img.stopListening();
       // fireadapter.startListening();
    }
}