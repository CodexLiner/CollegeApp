package com.codingergo.myproject.noticeBoard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.codingergo.myproject.Cs_Dashboard.DashBoard;
import com.codingergo.myproject.Main.home;
import com.codingergo.myproject.R;

import java.net.URLEncoder;

public class PDFViewer extends AppCompatActivity {
    WebView webView;
    String link="";
    ImageView home ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_viewer);
        webView = (WebView)findViewById(R.id.webview);
        home =(ImageView) findViewById(R.id.webHome);
        webView.getSettings().setJavaScriptEnabled(true);
        String filename = getIntent().getStringExtra("Filename");
        String url = getIntent().getStringExtra("url");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Opening Pdf");
        progressDialog.setMessage("Please Wait");
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
                getWindowManager();
            }
        });

        try {
            link = URLEncoder.encode(url,"UTF-8");
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+link);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Failed To Load", Toast.LENGTH_LONG).show();
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      //  webView.loadUrl("https://docs.google.com/viewer?url="+link);
//        CustomTabsIntent.Builder b = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = b.build();
//        customTabsIntent.launchUrl(this,Uri.parse(url));

      //  reload();
    }
    //thread for reload
//    public void reload() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                Toast.makeText(PDFViewer.this, "Hello", Toast.LENGTH_SHORT).show();
//                reload();
//                webView.loadUrl("https://docs.google.com/viewer?url="+link);
//            }
//        }, 5000);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), com.codingergo.myproject.Main.home.class));
        webView.canGoBack();
        finish();
    }
}