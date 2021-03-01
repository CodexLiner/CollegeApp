package com.codingergo.myproject.NoticeBoard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class noticeBoardExtended extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    fireNoticeAdapter fire;
    noticeModel noticemodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board_extended);
        recyclerView = findViewById(R.id.recyclerNotice);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();



        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        Query query = firestore.collection("Notifications").orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<noticeModel> options = new FirestoreRecyclerOptions.Builder<noticeModel>()
                .setQuery(query , noticeModel.class)
                .build();
        fire = new fireNoticeAdapter(options);
        recyclerView.setAdapter(fire);

    }

    @Override
    protected void onStop() {
        super.onStop();
        fire.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fire.startListening();
    }
}