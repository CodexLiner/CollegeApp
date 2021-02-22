package com.codingergo.myproject.EventManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codingergo.myproject.Extras.CeFragment;
import com.codingergo.myproject.R;

public class EventList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.wrapper , new CeFragment())
                .commit();
    }
}