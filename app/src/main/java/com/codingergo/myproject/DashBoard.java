package com.codingergo.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoard extends AppCompatActivity {

SimpleDateFormat simpleDateFormat , timef;
TextView timeview , todays;
String time , Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        timeview =(TextView)findViewById(R.id.TimeDash);
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
                     startActivity(new Intent(getApplicationContext(),home.class));
                         overridePendingTransition(0,0);
                     return true;
                     case R.id.dash:
                     return true;
                     case R.id.profile:
                     startActivity(new Intent(getApplicationContext(),Profile.class));
                     overridePendingTransition(0,0);
                     return true;
                 }
                 return false;
             }
         });
         //// end of nav bar
//        time and date updater

        Thread thread = new Thread() {
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
    }
}