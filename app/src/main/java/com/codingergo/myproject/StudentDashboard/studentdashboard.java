package com.codingergo.myproject.StudentDashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.AnnouncmentManager.AdapterClass;
import com.codingergo.myproject.AnnouncmentManager.ModelClass;
import com.codingergo.myproject.Assignment.AssignmentAdapter;
import com.codingergo.myproject.Assignment.AssignmentModel;
import com.codingergo.myproject.Assignment.DrillsExtended;
import com.codingergo.myproject.EventManager.EventList;
import com.codingergo.myproject.Extras.CeFragment;
import com.codingergo.myproject.Main.home;
import com.codingergo.myproject.NotesManager.notes_list;
import com.codingergo.myproject.R;
import com.codingergo.myproject.RecyclerFragments.AssignmentFragment;
import com.codingergo.myproject.FacultyList.FacultyListAdapter;
import com.codingergo.myproject.FacultyList.FacultyModel;
import com.codingergo.myproject.MoreButton.moreButton;
import com.codingergo.myproject.NoticeBoard.fireNoticeAdapter;
import com.codingergo.myproject.NoticeBoard.noticeModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class studentdashboard extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    fireNoticeAdapter mfireadpater;
    AssignmentAdapter assignmentAdapter;
    AdapterClass adapterClass;
    noticeModel model;
    FacultyListAdapter facultyListAdapter;
    RecyclerView recyclerView , Trecycler ,ChatRec;
    Query collectionReference;
    LinearLayout StudentNotes, StudentDrill , StudentEvents , StudentHead, StudentHOD;
    TextView HeadName , HodName;
    ImageView HodImage , HeadImage;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_studentdashboard);
    firestore = FirebaseFirestore.getInstance();
    auth = FirebaseAuth.getInstance();
    StudentDrill = findViewById(R.id.StudentDrills);
    StudentEvents = findViewById(R.id.StudentEvents);
    StudentNotes = findViewById(R.id.StudentNotes);
    recyclerView = findViewById(R.id.studentAssign);
    ChatRec = findViewById(R.id.ChatRec);
    StudentHead = findViewById(R.id.StudentHead);
    StudentHOD = findViewById(R.id.StudentHOD);
    Trecycler = findViewById(R.id.TlistRec);
    HeadName = findViewById(R.id.Headname);
    HodName = findViewById(R.id.HodName);
    HodImage = findViewById(R.id.HodImage);
    HeadImage = findViewById(R.id.HeadImage);
//    progressBar = findViewById(R.id.progressShaped);
    collectionReference = firestore.collection("Drills");

    BottomNavBar();
    RecyclerViews();
    ButtonClicker();
    WebViewDrill();
    HodandHead();
//  FragmentManagers();
//   ProgresShow();

    }

    private void FragmentManagers() {
      getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.DashboardAssignFrag, new AssignmentFragment()).commit();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.DashboardAssignFrag , new CeFragment())
                .commit();
    }

    private void HodandHead() {


        StudentHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(studentdashboard.this);
                dialog.setContentView(R.layout.dialog_design_line);
                dialog.setTitle(R.string.cs_title);
                dialog.setCancelable(false);
                dialog.show();
                Button done = dialog.findViewById(R.id.diloagButton);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        StudentHOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(studentdashboard.this);
                dialog.setContentView(R.layout.dialog_design_line);
                dialog.setCancelable(false);
                dialog.show();
                Button done = dialog.findViewById(R.id.diloagButton);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        //HOD
        Glide.with(HodImage).load("https://cdn.fastly.picmonkey.com/contentful/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=800&q=70")
                .into(HodImage);
        HodName.setText("V Banse");
        //HEAD
        Glide.with(HeadImage).load("https://monteluke.com.au/wp-content/gallery/linkedin-profile-pictures/1.jpg")
                .into(HeadImage);
        HeadName.setText("R Sisodiya");
    }

    private void WebViewDrill() {
        String URL;
        URL = getIntent().getStringExtra("URL");
        Log.d("TAG", "WebViewDrill: " +URL);
        if (URL!=null){
            Uri.parse(URL);
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this , Uri.parse(URL));

        }

    }

    private void ProgresShow() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              progressDialog.hide();
            }
        },500);
    }

    private void ButtonClicker() {
        StudentNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), notes_list.class));
            }
        });
        StudentEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventList.class));
            }
        });
        StudentDrill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), DrillsExtended.class));
            }
        });

    }

    private void RecyclerViews() {

        // Assignment Recycler View
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

       Query query = collectionReference.orderBy("date" , Query.Direction.DESCENDING);
       FirestoreRecyclerOptions<AssignmentModel> options =
               new FirestoreRecyclerOptions.Builder<AssignmentModel>()
                        .setQuery(query,AssignmentModel.class).build();

       assignmentAdapter = new AssignmentAdapter(options);
       recyclerView.setAdapter(assignmentAdapter);

       // Teacher List View Recycler Adpater
        Trecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
       Query Tquery = firestore.collection("Users");
       FirestoreRecyclerOptions<FacultyModel> Toptiions = new FirestoreRecyclerOptions.Builder<FacultyModel>()
               .setQuery(Tquery,FacultyModel.class)
               .build();
       facultyListAdapter = new FacultyListAdapter(Toptiions);
       Trecycler.setAdapter(facultyListAdapter);
       //Chat RecyclerView
        ChatRec.setLayoutManager(new LinearLayoutManager(this , RecyclerView.HORIZONTAL , false));
        Query query2 = firestore.collection("announcement");
        FirestoreRecyclerOptions<ModelClass> options1 = new FirestoreRecyclerOptions.Builder<ModelClass>()
                .setQuery(query2 , ModelClass.class)
                .build();
       adapterClass = new AdapterClass(options1);
       ChatRec.setAdapter(adapterClass);


    }

    private void BottomNavBar() {
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
                     checkAccess(auth.getCurrentUser().getUid());
                       return true;
                   case R.id.profile:
                       startActivity(new Intent(getApplicationContext(), moreButton.class));
                       overridePendingTransition(0,0);
                       return true;
               }
                return false;
            }
        });
    }

    private void checkAccess(String uid) {
        DocumentReference df = firestore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            String id = "1";
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("isUser").equals(id)){
                    overridePendingTransition(0,0);
                   return;
                }
                else {
                    overridePendingTransition(0,0);
                    return;
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
         startActivity(new Intent(getApplicationContext(), home.class));
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assignmentAdapter.stopListening();
        facultyListAdapter.stopListening();
        adapterClass.stopListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        assignmentAdapter.startListening();
        facultyListAdapter.startListening();
        adapterClass.startListening();
    }
}