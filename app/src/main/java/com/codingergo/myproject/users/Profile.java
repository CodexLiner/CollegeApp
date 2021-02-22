package com.codingergo.myproject.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codingergo.myproject.Cs_Dashboard.DashBoard;
//import MainActivity;
import com.codingergo.myproject.R;
import com.codingergo.myproject.Main.home;
import com.codingergo.myproject.studentDashboard.studentdashboard;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
  TextView name , email , address , roll ,phone, branch, profileLogout;
  FirebaseDatabase firebaseDatabase;
  DatabaseReference databaseReference;
  String Email;
  FirebaseAuth auth;
  FirebaseFirestore firestore ;
  public final  String USER= "Users";
  ShimmerFrameLayout container;
  SharedPreferences sharedPreferences;
  SharedPreferences.Editor editor;
  String isUser;

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
        sharedPreferences = getSharedPreferences("whole", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        /// end var

        /// get maill
         auth = FirebaseAuth.getInstance();
         firestore  = FirebaseFirestore.getInstance();
         Email = auth.getCurrentUser().getEmail();

        /// data fecthing starts here
       container.startShimmer();
       profileSetter(auth.getCurrentUser().getUid());
       SharedPreferences sp = getSharedPreferences("whole", MODE_PRIVATE);
       if (sp.contains("name")){
           name.setText(sp.getString("pname",""));
           email.setText(sp.getString("email",""));
           roll.setText(sp.getString("roll",""));
           phone.setText(sp.getString("phone",""));
           address.setText(sp.getString("address",""));
           isUser = sp.getString("isUser" ,"");
       }
      else{
           Toast.makeText(getApplicationContext(),"Not Shared", Toast.LENGTH_LONG).show();
       }
//         userref.addValueEventListener(new ValueEventListener() {
//             @Override
//             public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 for (DataSnapshot dataSnapshot : snapshot.getChildren())
//                 {
//                     if (dataSnapshot.child("email").getValue().equals(Email))
//                     {
//                         name.setText(dataSnapshot.child("name").getValue(String.class));
//                         email.setText(Email);
//                         phone.setText(dataSnapshot.child("mobile").getValue(String.class));
//                         address.setText(dataSnapshot.child("address").getValue(String.class));
//                         roll.setText(dataSnapshot.child("roll").getValue(String.class));
////                         Toast.makeText(getApplicationContext(),"mail "+ Email,Toast.LENGTH_LONG).show();
//                         container.stopShimmer();
//                         container.setVisibility(View.GONE);
//
//                     }
//
//
//                 }
//             }
//
//             @Override
//             public void onCancelled(@NonNull DatabaseError error) {
//
//             }
//         });

        /// data fech end
        /////navigation bar starts here
//        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);
//        bottomNavigationView.setSelectedItemId(R.id.profile);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), home.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.dash:
//                      if (isUser!=null){
//                          String id = "1";
//                          if (isUser.equals(id)){
//                              startActivity(new Intent(getApplicationContext(), studentdashboard.class));
//                              overridePendingTransition(0,0);
//                          }
//                          else{
//                              startActivity(new Intent(getApplicationContext(), DashBoard.class));
//                              overridePendingTransition(0,0);
//                          }
//                      }
//                    case R.id.profile:
//                       return true;
//                }
//                return false;
//            }
//        });
        ////nav bar ends here
        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });
    }

    private void checkAccess(String uid) {
        DocumentReference df = firestore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            String id = "1";
            String id2 = "0";
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
             if (documentSnapshot.getString("isUser").equals(id)){

                        startActivity(new Intent(getApplicationContext(), studentdashboard.class));
                        overridePendingTransition(0,0);
                        return;
                    }
                else {
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        overridePendingTransition(0,0);
                        return;
                    }



            }
        });
    }

    private void profileSetter(String uid) {
        DocumentReference df = firestore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getString("fullname"));
                email.setText(documentSnapshot.getString("email"));
                roll.setText(documentSnapshot.getString("roll"));
                phone.setText(documentSnapshot.getString("mobile"));
                address.setText(documentSnapshot.getString("address"));
                editor.putString("pname",documentSnapshot.getString("fullname"));
                editor.putString("email",documentSnapshot.getString("email"));
                editor.putString("roll",documentSnapshot.getString("roll"));
                editor.putString("phone",documentSnapshot.getString("mobile"));
                editor.putString("address",documentSnapshot.getString("address"));
                editor.putString("isUser",documentSnapshot.getString("isUser"));
                editor.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}