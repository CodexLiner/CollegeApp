package com.codingergo.myproject.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.AboutDev.developer;
import com.codingergo.myproject.Cs_Dashboard.DashBoard;
import com.codingergo.myproject.R;
import com.codingergo.myproject.noticeBoard.noticeAdapter;
import com.codingergo.myproject.noticeBoard.noticeModel;
import com.codingergo.myproject.photoGallery.galleryMain;
import com.codingergo.myproject.photoGallery.imageAdapter;
import com.codingergo.myproject.photoGallery.imageModel;
import com.codingergo.myproject.tabLayout.TabManager;
import com.codingergo.myproject.users.Profile;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {
    // globale varialbe
    ImageView imageView;
    RecyclerView recyclerView ;
    RecyclerView galleryrec;
    noticeAdapter adapter;
    imageAdapter iadapter;
    DrawerLayout drawerLayout ;
    private long backbutton;
    TextView welcome , pdf;
    String welcomename;
    private Toast toast;
    Button button;
    TabLayout tableLayout;
    TabItem ce , cs , et , me;
    ViewPager viewPager;
    TabManager tabManager;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    public final  String USER= "Users";
    ShimmerFrameLayout shimmerFrameLayout;

    // on create method starts here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // button = findViewById(R.id.signout);
      shimmerFrameLayout = findViewById(R.id.shimmer);
      imageView = (ImageView)findViewById(R.id.profile_image);
       //  drawerLayout =(DrawerLayout) findViewById(R.id.drawer_tab);
       // tableLayout=(TabLayout)findViewById(R.id.tab_Layout);
       viewPager = (ViewPager)findViewById(R.id.pageholder);
       recyclerView = findViewById(R.id.notice_Rec);
       galleryrec = findViewById(R.id.galleryrec);
       GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
       galleryrec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
       recyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false ));
       welcome = (TextView)findViewById(R.id.welcome_text);
       ce = (TabItem)findViewById(R.id.civiltab);
       cs = (TabItem)findViewById(R.id.cstab);
       me = (TabItem)findViewById(R.id.metab);
       et = (TabItem)findViewById(R.id.ettab);
      // pdf = (TextView)findViewById(R.id.pdf);

//        databaseReference  = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference user = databaseReference.child(USER);
        auth = FirebaseAuth.getInstance();
        DatabaseReference rooref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userref = rooref.child(USER);
       // welcome = findViewById(R.id.welcome_text);
        welcomename = auth.getCurrentUser().getEmail();
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.dash:
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
//nav bar end
        String uri = "https://scontent-bom1-1.cdninstagram.com/v/t51.2885-19/s320x320/117567853_3278764075515229_6349804811514481652_n.jpg?_nc_ht=scontent-bom1-1.cdninstagram.com&_nc_ohc=B_1OXxT4dRkAX_3AbB3&tp=1&oh=4d99dbd66c972cdb060bd7c9920363ca&oe=6030CAD8";
        Glide.with(imageView).load(uri).into(imageView);
//        welcome text

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    if ( snap.child("email").getValue().equals(welcomename)) {

                        String first = snap.child("name").getValue(String.class);
                        String[] splited = first.split("\\s+");
                        welcome.setText("Hello "+splited[0]);
                     //   welcome.setText("Hi "+snap.child("name").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//         pdf.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              startActivity(new Intent(getApplicationContext(), developer.class));
//          }
//      });
/// tab manager
//        tabManager = new TabManager(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tableLayout.getTabCount());
//        viewPager.setAdapter(tabManager);
//          tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//              @Override
//              public void onTabSelected(TabLayout.Tab tab) {
//                  viewPager.setCurrentItem(tab.getPosition());
//              }
//
//              @Override
//              public void onTabUnselected(TabLayout.Tab tab) {
//
//              }
//
//              @Override
//              public void onTabReselected(TabLayout.Tab tab) {
//
//              }
//          });
//          viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayout));

//         imageView.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 startActivity(new Intent(getApplicationContext(), Profile.class));
//             }
//         });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    auth.signOut();
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//        });
//Notice Baord Rec View
        FirebaseRecyclerOptions<noticeModel> options =
                new FirebaseRecyclerOptions.Builder<noticeModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Notifications").orderByChild("date"), noticeModel.class)
                        .build();
        adapter = new noticeAdapter(options);
        recyclerView.setAdapter(adapter);
//        recyclerView.smoothScrollToPosition(adapter.getItemCount());

//  galleryAdapter
        FirebaseRecyclerOptions<imageModel> option =
                new FirebaseRecyclerOptions.Builder<imageModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Gallery").orderByChild("date"), imageModel.class)
                       .build();
        iadapter = new imageAdapter(option);
        galleryrec.setAdapter(iadapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        iadapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
         iadapter.stopListening();
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

}