package com.codingergo.myproject.moreButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codingergo.myproject.AboutDev.developer;
import com.codingergo.myproject.BuildConfig;
import com.codingergo.myproject.Cs_Dashboard.DashBoard;
import com.codingergo.myproject.Main.home;
import com.codingergo.myproject.R;
import com.codingergo.myproject.studentDashboard.studentdashboard;
import com.codingergo.myproject.users.Profile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class moreButton extends AppCompatActivity {
    LinearLayout share , profile , about , feedback;
    BottomNavigationView bottomNavigationView;
    String isUSer ;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_button);
        share = findViewById(R.id.share);
        profile = findViewById(R.id.profile);
        about = findViewById(R.id.about);
        feedback= findViewById(R.id.feedback);
        bottomNavigationView = findViewById(R.id.BottomNav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("whole",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        checkUser();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dash:
                        String id = "1";
                        if (isUSer!=null){
                            if (isUSer.equals(id)){
                                startActivity(new Intent(getApplicationContext(), studentdashboard.class));
                                overridePendingTransition(0,0);
                            }
                            else{
                                startActivity(new Intent(getApplicationContext(), DashBoard.class));
                                overridePendingTransition(0,0);

                            }
                        }
                           return true;

                }
                return false;
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), developer.class));
            }
        });
      share.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent();
              intent.setAction(Intent.ACTION_SEND);
              intent.putExtra(Intent.EXTRA_TEXT ,
                      "Hey Student's Download Your College App On PlayStore https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID);
              intent.setType("text/plan");
              startActivity(intent);
          }
      });
      feedback.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(moreButton.this, "on Share Button", Toast.LENGTH_SHORT).show();
          }
      });
      if (sharedPreferences.contains("isUser")){
         isUSer = sharedPreferences.getString("isUser","");
      }
    }

    private void checkUser() {
        DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.getString("isUser")!=null){
                    editor.putString("IsUser",documentSnapshot.getString("isUser"));
                    editor.commit();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(new Intent(getApplicationContext(), home.class)));
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}