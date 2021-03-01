package com.codingergo.myproject.StudentList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;

import com.codingergo.myproject.R;
import com.codingergo.myproject.NoticeBoard.fireNoticeAdapter;
import com.codingergo.myproject.NoticeBoard.noticeModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class studentList extends AppCompatActivity {
    RecyclerView studentList;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        studentList = findViewById(R.id.studentList);
        auth= FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
         studentList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        Query query = firestore.collection("Users").orderBy("roll", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<StudentListModel> options = new FirestoreRecyclerOptions.Builder<StudentListModel>()
                .setQuery(query , StudentListModel.class).build();
        adapter = new StudentListAdapter(options);
        studentList.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.Delete(viewHolder.getAdapterPosition());

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(studentList.this, R.color.red))
                        .addActionIcon(R.drawable.ic_home)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(studentList);
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