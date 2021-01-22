package com.codingergo.myproject.Cs_Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingergo.myproject.Assignment.Assignment;
import com.codingergo.myproject.R;
import com.codingergo.myproject.Main.home;
import com.codingergo.myproject.noticeBoard.noticeBoard;
import com.codingergo.myproject.photoGallery.galleryMain;
import com.codingergo.myproject.users.Profile;
import com.codingergo.myproject.users.SignUp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoard extends AppCompatActivity {

SimpleDateFormat simpleDateFormat , timef;
TextView timeview , todays;
String time , Date;
Button notice , faculty , assign, addStudent , addGallery;

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
        simpleDateFormat = new SimpleDateFormat("dd MMM yy");
        time = simpleDateFormat.format(new Date());
        Date = timef.format(new Date());
        timeview.setText(Date);
        todays.setText(time);

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
                     return true;
                     case R.id.profile:
                     startActivity(new Intent(getApplicationContext(), Profile.class));
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
                startActivity(new Intent(getApplicationContext(),DashBoard.class));
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
// Button ands here
    }
}