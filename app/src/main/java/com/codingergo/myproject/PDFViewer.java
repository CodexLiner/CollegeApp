package com.codingergo.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URLEncoder;

public class PDFViewer extends AppCompatActivity {
    WebView webView;
    String link="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_viewer);
        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        String filename = getIntent().getStringExtra("filename");
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
            }
        });

        try {
            link = URLEncoder.encode(url,"UTF-8");
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+link);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Failed To Load", Toast.LENGTH_LONG).show();
        }
        webView.loadUrl("https://docs.google.com/viewer?url="+link);
      //  reload();
    }
    //thread for reload
    public void reload() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Toast.makeText(PDFViewer.this, "Hello", Toast.LENGTH_SHORT).show();
                reload();
                webView.loadUrl("https://docs.google.com/viewer?url="+link);
            }
        }, 5000);
    }

}