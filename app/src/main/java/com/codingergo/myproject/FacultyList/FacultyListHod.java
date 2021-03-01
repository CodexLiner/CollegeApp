package com.codingergo.myproject.FacultyList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codingergo.myproject.R;
import com.codingergo.myproject.StudentList.StudentListAdapter;
import com.codingergo.myproject.StudentList.StudentListModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FacultyListHod extends AppCompatActivity {
    RecyclerView recyclerView;
    FacultyListAdapterForHod adapter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);
        recyclerView = findViewById(R.id.TeacherRecEdit);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Query query = firestore.collection("Users");
        FirestoreRecyclerOptions<FacultyModel> options = new FirestoreRecyclerOptions.Builder<FacultyModel>()
                .setQuery(query ,FacultyModel.class)
                .build();
        adapter = new  FacultyListAdapterForHod(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}