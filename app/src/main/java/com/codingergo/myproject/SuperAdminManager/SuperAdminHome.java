package com.codingergo.myproject.SuperAdminManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codingergo.myproject.MailSender.MailFunctions;
import com.codingergo.myproject.R;

public class SuperAdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String emaill = "meenagopal0@gmail.com";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_home);
        MailFunctions mailFunctions = new MailFunctions();
        mailFunctions.MailSender(emaill);
    }
}