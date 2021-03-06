package com.codingergo.myproject.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.Demo.pusher;
import com.codingergo.myproject.TeachersDashboard.DashBoard;
import com.codingergo.myproject.R;
import com.codingergo.myproject.MoreButton.moreButton;
import com.codingergo.myproject.NoticeBoard.fireNoticeAdapter;
import com.codingergo.myproject.NoticeBoard.noticeAdapter;
import com.codingergo.myproject.NoticeBoard.noticeBoardExtended;
import com.codingergo.myproject.NoticeBoard.noticeModel;
import com.codingergo.myproject.PhotoGallery.GalleryExtended;
import com.codingergo.myproject.PhotoGallery.fireAdapter;
import com.codingergo.myproject.PhotoGallery.imageAdapter;
import com.codingergo.myproject.PhotoGallery.imageModel;
import com.codingergo.myproject.StudentDashboard.studentdashboard;
import com.codingergo.myproject.TabLayout.TabManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.File;

public class home extends AppCompatActivity {
    // globale varialbe
    ImageView imageView;
    RecyclerView recyclerView ;
    RecyclerView galleryrec;
    noticeAdapter adapter;
    fireNoticeAdapter firenoticeadapter;
    imageAdapter iadapter;
    fireAdapter fireadapter;
    DrawerLayout drawerLayout ;
    private long backbutton;
    TextView welcome , galleryAll , notificationall;
    String welcomename;
    private Toast toast;
    Button button;
    TabLayout tableLayout;
    TabItem ce , cs , et , me;
    ViewPager viewPager;
    TabManager tabManager;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    public final  String USER= "Users";
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    String isUser;

    // on create method starts here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_home);
           shimmerFrameLayout = findViewById(R.id.shimmer);
           imageView = (ImageView)findViewById(R.id.profile_image);
           viewPager = (ViewPager)findViewById(R.id.pageholder);
           recyclerView = findViewById(R.id.notice_Rec);
           galleryrec = findViewById(R.id.galleryrec);
           galleryAll = findViewById(R.id.galleryall);
           notificationall = findViewById(R.id.notificationall);
           welcome = (TextView)findViewById(R.id.welcome_text);
           ce = (TabItem)findViewById(R.id.civiltab);
           cs = (TabItem)findViewById(R.id.cstab);
           me = (TabItem)findViewById(R.id.metab);
           et = (TabItem)findViewById(R.id.ettab);
           sharedPreferences = getSharedPreferences("whole",MODE_PRIVATE);
           editor = sharedPreferences.edit();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        DatabaseReference rooref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userref = rooref.child(USER);
        welcomename = auth.getCurrentUser().getEmail();
        UserDetails();
        RecyclerAD();
        Buttons();
        BottomNav();
        PermissionManager();
        CreateFolder();
        temp();

//nav bar end
        String uri = sharedPreferences.getString("url" , null);
        Glide.with(imageView).load(uri).placeholder(R.drawable.profile).into(imageView);
        editor.putString("name", "Shared Prefs");
        SharedPreferences sp = getSharedPreferences("whole", MODE_PRIVATE);
        if (sp.contains("name")){
            welcome.setText(sp.getString("name", ""));
            isUser = sp.getString("isUser","nullaaa");

        }
//        else{
//            while (!sp.contains("name")){
//                ProgressDialog p = new ProgressDialog(this);
//                p.show();
//            }
//        }
        Log.d("TAG", "onSuccess: id @ "+isUser);
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                UserDetails();
                                if (sp.contains("name")){
                                    welcome.setText(sp.getString("name", ""));
                                    isUser = sp.getString("isUser","nullaaa");
                                    Log.d("TAG", "onSuccess: id @ "+isUser);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
              UserDetails();

            }
        });

    }

    private void CreateFolder() {
        File file = new File(Environment.getExternalStorageDirectory(), "/CodingErgo/Download");
        if (!file.exists()){
            if (file.mkdirs()){
                Log.d("TAG", "CreatedFolder:TWO ");
                // Toast.makeText(this, "Created Already", Toast.LENGTH_SHORT).show();
            }
            Log.d("TAG", "CreateFolder: "+file);
        }
    }

    private void PermissionManager() {
        if (ActivityCompat.checkSelfPermission(home.this , Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Log.d("TAG", "PermissionManager: Granteed ");
            CreateFolder();
        }
        else {
            ActivityCompat.requestPermissions(home.this ,new  String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && (grantResults.length >0 ) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // Toast.makeText(this, "Permission Granteed", Toast.LENGTH_SHORT).show();
            CreateFolder();
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void temp() {

        LinearLayout l ;
        l = findViewById(R.id.choose);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), pusher.class));
            }
        });
//        File file = new File(Environment.getExternalStorageDirectory()+"/CodeErgo/Download");
//        if (!file.mkdirs()){
//            file.mkdirs();
//        }
//        String fname = file.getAbsolutePath() + File.separator ;
//        Log.d("TAG", "temp: "+fname);
    }

    private void BottomNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.dash:
                        if (isUser!=null){
                            String id = "1";
//                            String doc = "1";
                            if (isUser.equals(id)){
                                startActivity(new Intent(getApplicationContext(), studentdashboard.class));
                                overridePendingTransition(0,0);

                            }
                            else {
                                startActivity(new Intent(getApplicationContext(), DashBoard.class));
                                overridePendingTransition(0,0);
//
                            }
                        }
                        overridePendingTransition(0,0);
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

    private void Buttons() {
        galleryAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GalleryExtended.class));
            }
        });
        notificationall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), noticeBoardExtended.class));
            }
        });

    }

    private void RecyclerAD() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        galleryrec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false ));

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        Query query2 = firestore.collection("Notifications").orderBy("date" , Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<noticeModel> noticeRec = new  FirestoreRecyclerOptions.Builder<noticeModel>()
                .setQuery(query2, noticeModel.class)
                .build();
        firenoticeadapter = new fireNoticeAdapter(noticeRec);
        recyclerView.setAdapter(firenoticeadapter);

        Query query = firestore.collection("Gallery");
        FirestoreRecyclerOptions<imageModel> options3 = new  FirestoreRecyclerOptions.Builder <imageModel>()
                .setQuery(query, imageModel.class)
                .build();
        fireadapter = new fireAdapter(options3);
        galleryrec.setAdapter(fireadapter);

    }

    private void UserDetails() {
        DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String First = documentSnapshot.getString("fullname");
                String url = documentSnapshot.getString("url");
                Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.profile).into(imageView);
                String[] splited = First.split("\\s+");
                welcome.setText("Hello "+splited[0]);
                editor.putString("name" ,"Hello "+splited[0]);
                editor.putString("isUser",documentSnapshot.getString("isUser"));
                editor.putString("url" , url);
                editor.commit();
                isUser = sharedPreferences.getString("isUser", "null found");

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        firenoticeadapter.startListening();
        fireadapter.startListening();
        UserDetails();
    }
    @Override
    public void onStop() {
        super.onStop();
        firenoticeadapter.stopListening();
        fireadapter.stopListening();
       // shimmerFrameLayout.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onBackPressed() {
           if (backbutton + 2000 > System.currentTimeMillis()) {
            Log.d("CDA", "onBackPressed Called");
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
            toast.cancel();
            super.onBackPressed();
        } else {
            toast = Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            toast.show();
        }

        backbutton = System.currentTimeMillis();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView textView = findViewById(R.id.welcome_text);
        CharSequence cr = textView.getText();
        outState.putCharSequence("welcomename",cr);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CharSequence charSequence = savedInstanceState.getCharSequence("welcomename");
        TextView textView = findViewById(R.id.welcome_text);
        textView.setText(charSequence);
    }
   public void addData(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("wt",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "Shared Prefs");
        editor.commit();


     }
     public void getData(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("wt", MODE_PRIVATE);
        if (sharedPreferences.contains("name")){
            welcome.setText(sharedPreferences.getString("name", ""));

        }
        else{
            Toast.makeText(getApplicationContext(),"Shared Not found",Toast.LENGTH_LONG).show();
        }
     }

}