package com.codingergo.myproject.NotesManager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Toast;

import com.codingergo.myproject.R;

import java.io.File;

public class NotesDownloader extends AppCompatActivity {
WebView webView;
String url , name;
String FileType ;
static String ROOT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_downloader);
        webView= findViewById(R.id.webview);
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
       // MakeDir();
        FileType = MimeTypeMap.getFileExtensionFromUrl(url);
        Log.d("TAG", "onCreate: "+url);
        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.allowScanningByMediaScanner();
            Environment.getExternalStorageDirectory();
            getApplicationContext().getFilesDir().getPath(); //which returns the internal app files directory path
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name+"."+FileType);
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);
            Log.d("TAG", "onCreateR: "+request.toString());
        }catch (Exception e){
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
        finish();

    }

    private void MakeDir() {
        File file = new File(Environment.getExternalStorageDirectory()+"/CodingErgo/Downloads");
        if (!file.mkdirs()){
            file.mkdirs();
            ROOT = file.getAbsolutePath() ;
            Log.d("TAG", "MakeDirFOlder: "+ROOT);
        }
        ROOT = file.getAbsolutePath();
    }
}