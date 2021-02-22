package com.codingergo.myproject.studentList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.codingergo.myproject.R;
import com.codingergo.myproject.noticeBoard.fireNoticeAdapter;
import com.codingergo.myproject.noticeBoard.noticeModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class studentList extends AppCompatActivity {
    RecyclerView studentList;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    fireNoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        studentList = findViewById(R.id.studentList);
        auth= FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
         studentList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        Query query = firestore.collection("Notifications");
        FirestoreRecyclerOptions<noticeModel> options = new FirestoreRecyclerOptions.Builder<noticeModel>()
                .setQuery(query , noticeModel.class).build();
        adapter = new fireNoticeAdapter(options);
        studentList.setAdapter(adapter);
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