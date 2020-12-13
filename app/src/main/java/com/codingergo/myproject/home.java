package com.codingergo.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    RecyclerView recyclerView;
    noticeAdapter adapter;
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
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       ce = (TabItem)findViewById(R.id.civiltab);
       cs = (TabItem)findViewById(R.id.cstab);
       me = (TabItem)findViewById(R.id.metab);
       et = (TabItem)findViewById(R.id.ettab);
       pdf = (TextView)findViewById(R.id.pdf);

//        databaseReference  = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference user = databaseReference.child(USER);
        auth = FirebaseAuth.getInstance();
        DatabaseReference rooref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userref = rooref.child(USER);
        welcome = findViewById(R.id.welcome_text);
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
                        startActivity(new Intent(getApplicationContext(),DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        ////nav bar end
        ///
//        welcome text
       shimmerFrameLayout.setVisibility(View.VISIBLE);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    if ( snap.child("email").getValue().equals(welcomename))
                    {
                        welcome.setText(snap.child("name").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         pdf.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(), noticeBoard.class));
          }
      });
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

         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(),Profile.class));
             }
         });
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
        FirebaseRecyclerOptions<noticeModel> options =
                new FirebaseRecyclerOptions.Builder<noticeModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), noticeModel.class)
                        .build();
        adapter = new noticeAdapter(options);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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