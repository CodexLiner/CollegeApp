package com.codingergo.myproject.Assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DrillsExtended extends AppCompatActivity {
    RecyclerView DrillExtended;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    AssignmentAdapter assignmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drills_extended);
        DrillExtended = findViewById(R.id.drill_rec);
        firestore = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        RecyclerVIEW();
    }

    private void RecyclerVIEW() {
        DrillExtended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Query query = firestore.collection("Drills");
        FirestoreRecyclerOptions<AssignmentModel> Drills = new FirestoreRecyclerOptions.Builder<AssignmentModel>()
                .setQuery(query, AssignmentModel.class)
                .build();
        assignmentAdapter = new AssignmentAdapter(Drills);
        DrillExtended.setAdapter(assignmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        assignmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assignmentAdapter.stopListening();
    }
}