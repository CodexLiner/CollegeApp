package com.codingergo.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Profile extends AppCompatActivity {
  TextView name , email , address , roll ,phone, branch, profileLogout;
  FirebaseDatabase firebaseDatabase;
  DatabaseReference databaseReference;
  String Email;
  FirebaseAuth auth;
  public final  String USER= "Users";
  ShimmerFrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ///databse connection

          DatabaseReference rooref = FirebaseDatabase.getInstance().getReference();
          DatabaseReference userref = rooref.child(USER);

        ///close database section
        super.onCreate(savedInstanceState);
        /// variables
        setContentView(R.layout.activity_profile);
        profileLogout = findViewById(R.id.profileLogout);
        name = findViewById(R.id.profilename);
        email = findViewById(R.id.profilemail);
        address = findViewById(R.id.profilecity);
        roll = findViewById(R.id.profileroll);
        branch = findViewById(R.id.profilebranch);
        phone = findViewById(R.id.profilephone);
        container = (ShimmerFrameLayout)findViewById(R.id.shimmer);
        /// end var

        /// get maill
        auth = FirebaseAuth.getInstance();
         Email = auth.getCurrentUser().getEmail();

        /// data fecthing starts here
       container.startShimmer();
         userref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot dataSnapshot : snapshot.getChildren())
                 {
                     if (dataSnapshot.child("email").getValue().equals(Email))
                     {
                         name.setText(dataSnapshot.child("name").getValue(String.class));
                         email.setText(Email);
                         phone.setText(dataSnapshot.child("mobile").getValue(String.class));
                         address.setText(dataSnapshot.child("address").getValue(String.class));
                         roll.setText(dataSnapshot.child("roll").getValue(String.class));
//                         Toast.makeText(getApplicationContext(),"mail "+ Email,Toast.LENGTH_LONG).show();
                         container.stopShimmer();
                         container.setVisibility(View.GONE);

                     }


                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        /// data fech end
        /////navigation bar starts here
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dash:
                       startActivity(new Intent(getApplicationContext(),DashBoard.class));
                       overridePendingTransition(0,0);
                       return true;
                    case R.id.profile:
                       return true;
                }
                return false;
            }
        });
        ////nav bar ends here
        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });
    }
}