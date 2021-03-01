package com.codingergo.myproject.NoticeBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingergo.myproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class noticeBoard extends AppCompatActivity {
    TextView pdfname ,select , selected;
    Button padupload;
    ImageView chooser ,choosed;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    Uri path;
    Date Date = new Date();
    SimpleDateFormat ft =
            new SimpleDateFormat("dd-MM-yyyy");
   String date = (ft.format(Date));;
   Long s = System.currentTimeMillis();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        storageReference = FirebaseStorage.getInstance().getReference("Notification");
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications");
        pdfname = (TextView)findViewById(R.id.PDF_name);
        padupload= (Button)findViewById(R.id.pdf_loader);
        chooser = (ImageView)findViewById(R.id.Chooser);
        choosed = (ImageView)findViewById(R.id.choosed);
        select = findViewById(R.id.selectonTap);
        selected = findViewById(R.id.selected);
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Notifications");


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
////                  Intent intent = new Intent();
////                  intent.setType("application/pdf");
////                  intent.setAction(intent.ACTION_GET_CONTENT);
////                  startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 101);
//                  Intent intent = new Intent();
//
//                  intent.setType("application/pdf");
//
//                  intent.setAction(Intent.ACTION_GET_CONTENT);
//
//                  startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 101);
//
//              }
//
//              @Override
//              public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//                  Toast.makeText(getApplicationContext(), "eroor p",Toast.LENGTH_LONG).show();
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
        choosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosepdf();
            }
        });
     padupload.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (path!=null)
                 uploadfile(path);
             else
                 Toast.makeText(getApplicationContext(),"Select File",Toast.LENGTH_SHORT).show();
         }
     });
    }

    private void choosepdf() {
        Intent intent = new Intent();

        intent.setType("application/pdf");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            chooser.setVisibility(View.INVISIBLE);
            choosed.setVisibility(View.VISIBLE);
            select.setVisibility(View.INVISIBLE);
            selected.setVisibility(View.VISIBLE);

            path = data.getData();
        }
        else
            Toast.makeText(getApplicationContext(),"File Not Selected",Toast.LENGTH_LONG).show();

    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            path = data.getData();
//        }
//        else
//            Toast.makeText(getApplicationContext(),"Request Code Error",Toast.LENGTH_LONG).show();
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//    }

    private void uploadfile(Uri path) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        StorageReference reference = storageReference.child("NOTICE"+System.currentTimeMillis()+".pdf");
        reference.putFile(path).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url = uri.getResult();
                noticeBoardModel noticeBoardModel = new noticeBoardModel( url.toString(),pdfname.getText().toString(), s.toString());
                DocumentReference df = firestore.collection("Notifications").document();
                df.set(noticeBoardModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(noticeBoard.this, "Collection Added", Toast.LENGTH_SHORT).show();

                    }
                });
//                databaseReference.child(databaseReference.push().getKey()).setValue(noticeBoardModel);
//                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double pro =  (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploded "+(int)pro+ " %");
//                Toast.makeText(getApplicationContext(),"Uploading" , Toast.LENGTH_LONG).show();

            }
        });
    }

}