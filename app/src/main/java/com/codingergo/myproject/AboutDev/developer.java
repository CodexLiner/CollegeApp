package com.codingergo.myproject.AboutDev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.R;

public class developer extends AppCompatActivity {
 ImageView instagram ,whatsapp , facebook , email , devprofile;
 Bitmap bitmap;
 String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        instagram = findViewById(R.id.instagram);
        whatsapp = findViewById(R.id.whatsapp);
        facebook = findViewById(R.id.facebook);
        email = findViewById(R.id.sendmail);
        devprofile = findViewById(R.id.devprofile);
        uri = "https://famouspeople.wiki/wp-content/uploads/2020/04/arijit-singh.jpg";
        Glide.with(devprofile.getContext()).load(uri).into(devprofile);

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/meenagopal24/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.instagram.android");
                startActivity(intent);

            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri =  Uri.parse("https://api.whatsapp.com/send?phone=919399846909&text=Hi+I%27m+Here+From+Poly+Harsud+Let+Guide+Me+Please+%F0%9F%98%8A%F0%9F%98%8A" );
              Intent intent = new Intent(Intent.ACTION_VIEW);
              intent.setType("text/plan");
                intent.setData(Uri.parse(String.valueOf(uri)));
              intent.setPackage("com.whatsapp");

                startActivity(intent);

            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/meenagopal24");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(uri)));
                startActivity(intent);

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "meenagopal24@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Poly Harsud");
                    intent.putExtra(Intent.EXTRA_TEXT, "Subject");
                    startActivity(intent);
                } catch(Exception e) {
                    Toast.makeText(developer.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
    }
}