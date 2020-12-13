package com.codingergo.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class noticeBoard extends AppCompatActivity {
    TextView pdfname, padupload;
    ImageView chooser;
   StorageReference storageReference;
   DatabaseReference databaseReference;
   Uri path;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("upload");
        pdfname = (TextView)findViewById(R.id.PDF_name);
        padupload= (TextView)findViewById(R.id.pdf_loader);
        chooser = (ImageView)findViewById(R.id.Chooser);

        
        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosepdf();
            }
        });
//  chooser.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//          Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
//              @Override
//              public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                  Intent intent = new Intent();
//                  intent.setType("application/pdf");
//                  intent.setAction(intent.ACTION_GET_CONTENT);
//                  startActivityForResult(Intent.createChooser(intent,"choose"),1);          }
//
//              @Override
//              public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//              }
//
//              @Override
//              public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                  permissionToken.continuePermissionRequest();
//              }
//          }).check();
//      }
//  });
     padupload.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (path!=null)
                 uploadfile(path);
             else
                 Toast.makeText(getApplicationContext(),"Select File",Toast.LENGTH_LONG).show();
         }
     });
    }

    private void choosepdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"select"),101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && requestCode == RESULT_OK)
        {
            path = data.getData();
}
        else
            Toast.makeText(getApplicationContext(),"Request Code Error",Toast.LENGTH_LONG).show();

    }

    private void uploadfile(Uri path) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
       progressDialog.show();
        StorageReference reference = storageReference.child("Docs"+System.currentTimeMillis()+"pdf");
        reference.putFile(path).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url = uri.getResult();
                noticeBoardModel noticeBoardModel = new noticeBoardModel(pdfname.getText().toString(), url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(noticeBoardModel);
                Toast.makeText(getApplicationContext(),"uploaded",Toast.LENGTH_LONG).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double pro =  (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("uploded"+pro+ "%");
                Toast.makeText(getApplicationContext(),"uploading" , Toast.LENGTH_LONG).show();

            }
        });
    }
    //  @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if( requestCode == 101 && requestCode == RESULT_OK){
//            path = data.getData();
//        }
//    }
//    public  void upload( Uri path){
//        final StorageReference sd = storageReference.child("upload/"+System.currentTimeMillis()+",pdf");
//        sd.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                sd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        noticeBoardModel nb = new noticeBoardModel(pdfname.getText().toString() , uri.toString());
//                        databaseReference.child(databaseReference.push().getKey()).setValue(nb);
//                        Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                Toast.makeText(getApplicationContext(),"Uploading",Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//
//    }
}