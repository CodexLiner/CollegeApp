package com.codingergo.myproject.Cs_Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codingergo.myproject.Assignment.Assignment;
import com.codingergo.myproject.R;
import com.codingergo.myproject.Main.home;
import com.codingergo.myproject.facultyList.addFaculty;
import com.codingergo.myproject.facultyList.facultyList;
import com.codingergo.myproject.moreButton.moreButton;
import com.codingergo.myproject.noticeBoard.noticeBoard;
import com.codingergo.myproject.photoGallery.fireAdapter;
import com.codingergo.myproject.photoGallery.galleryMain;
import com.codingergo.myproject.photoGallery.imageAdapter;
import com.codingergo.myproject.photoGallery.imageModel;
import com.codingergo.myproject.studentDashboard.studentdashboard;
import com.codingergo.myproject.studentList.studentList;
import com.codingergo.myproject.users.SignUp;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoard extends AppCompatActivity {
imageAdapter iadapter;
com.codingergo.myproject.photoGallery.fireAdapter fireadapter ;
SimpleDateFormat simpleDateFormat , timef;
TextView timeview , todays;
String time , Date;
Button notice , faculty , assign, addStudent , addGallery;
RecyclerView studentsRecycler , galleryRecycler , teachersRecycler;
FirebaseAuth auth;
FirebaseFirestore firestore;
CollectionReference collectionReference;
SharedPreferences sharedPreferences ;
SharedPreferences.Editor editor;
RelativeLayout studentEdit, facultyEdit, noticeEdit, assignEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);
        timeview =(TextView)findViewById(R.id.TimeDash);
        notice =(Button) findViewById(R.id.add_notice_btn);
        faculty=(Button) findViewById(R.id.add_faculty_btn);
        assign=(Button) findViewById(R.id.add_assignment_btn);
        addStudent = findViewById(R.id.addStudentDash);
        addGallery = findViewById(R.id.addGalleryDash);
        todays = (TextView)findViewById(R.id.DateDash);
        timef = new SimpleDateFormat("h:mm ");
        studentEdit = findViewById(R.id.editstudentdashboard);
        facultyEdit = findViewById(R.id.editfacultydashboard);
        noticeEdit = findViewById(R.id.editnoticedashboard);
        assignEdit = findViewById(R.id.editassignmentdashobard);
        simpleDateFormat = new SimpleDateFormat("dd MMM yy");
        time = simpleDateFormat.format(new Date());
        Date = timef.format(new Date());
        timeview.setText(Date);
        todays.setText(time);
        studentsRecycler = findViewById(R.id.adminStudentrec);
        galleryRecycler = findViewById(R.id.adminGalleryrec);
        teachersRecycler = findViewById(R.id.adminTeachersrec);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Gallery");
        sharedPreferences = getSharedPreferences("whole", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        /////nav bar starts here
         BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);
         bottomNavigationView.setSelectedItemId(R.id.dash);
         bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.home:
                     startActivity(new Intent(getApplicationContext(), home.class));
                         overridePendingTransition(0,0);
                     return true;
                    case R.id.dash:
                // checkAccess(auth.getCurrentUser().getUid());
                      return true;
                      case R.id.profile:
                     startActivity(new Intent(getApplicationContext(), moreButton.class));
                     overridePendingTransition(0,0);
                     return true;
                 }
                 return false;
             }
         });
         //// end of nav bar
//        time and date updater

        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                time = simpleDateFormat.format(new Date());
                                Date = timef.format(new Date());
                                timeview.setText(Date);
                                todays.setText(time);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
// notice add listner
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), noticeBoard.class));
            }
        });
// faculty
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addFaculty.class));

            }
        });
// assignment
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Assignment.class));
            }
        });
// add Student
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
 // add GAllery
        addGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), galleryMain.class));
            }
        });
        studentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), studentList.class));
            }
        });
        facultyEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), facultyList.class));
            }
        });
        noticeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),noticeBoard.class));
            }
        });
        assignEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Assignment.class));
            }
        });
// Button ands here
// recyclerView

        galleryRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        studentsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        teachersRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Query query = collectionReference.orderBy("time" , Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<imageModel> options = new FirestoreRecyclerOptions.Builder<imageModel>()
                .setQuery(query, imageModel.class)
                .build();
        fireadapter = new fireAdapter(options);

        FirebaseRecyclerOptions<imageModel> option =
                new FirebaseRecyclerOptions.Builder<imageModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Gallery").orderByChild("desc"), imageModel.class)
                        .build();


        iadapter = new imageAdapter(option);
        galleryRecycler.setAdapter(fireadapter);
       /// teachersRecycler.setAdapter(fireadapter);
       // studentsRecycler.setAdapter(fireadapter);
    }

    private void checkAccess(String uid) {
        DocumentReference df = firestore.collection("Gallery").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            String id = "1";
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                editor.putString("isUser", documentSnapshot.getString("isUSer"));
                editor.commit();
//               if (documentSnapshot.getString("isUser").equals(id)){
//                    startActivity(new Intent(getApplicationContext(), studentdashboard.class));
//                    overridePendingTransition(0,0);
//                    return;
//                }
//                else{
//                    overridePendingTransition(0,0);
//                    return;
//                }

            }
        });
    }

    // ouside of onCreate
    @Override
    protected void onStart() {
        super.onStart();
        iadapter.startListening();
        fireadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iadapter.stopListening();
        fireadapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),home.class));
        super.onBackPressed();
    }
}