package com.codingergo.myproject.PhotoGallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingergo.myproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class galleryMain extends AppCompatActivity {
    ImageView imageView, imageView1;
    EditText imgTitle , imgDesc;
    Button upload;
    Uri file;
    TextView title;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference;
    FirebaseFirestore firestore;
    java.util.Date Date = new Date();
    SimpleDateFormat ft =
            new SimpleDateFormat("yyyy/MM/dd");
    String date = (ft.format(Date));
    Long s = System.currentTimeMillis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);
       // imageView = findViewById(R.id.AddImage);
        upload =(Button)findViewById(R.id.buttonUpload);
        imageView1 = findViewById(R.id.AddImage1);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Gallery");
        databaseReference = FirebaseDatabase.getInstance().getReference("Gallery");
        title = (TextView)findViewById(R.id.titleText);
        imgTitle = (EditText)findViewById(R.id.aboutPhoto);
        imgDesc = (EditText)findViewById(R.id.aboutPhotoDesk);
        firestore = FirebaseFirestore.getInstance();
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploading();
            }
        });

    }

    private void uploading() {
        final String title = imgTitle.getText().toString().trim();
        final String about = imgDesc.getText().toString().trim();

        if (file !=null){
            if(TextUtils.isEmpty(title)){
                imgTitle.setError("Required");
                imgTitle.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(about)){
                imgDesc.setError("Required");
                imgDesc.requestFocus();
                return;
            }

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setCancelable(false);
            StorageReference reference = storageReference.child("Gallery"+System.currentTimeMillis());
            reference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                   while (!uri.isComplete()) ;
                       Uri url = uri.getResult();
                       imageModel imageModel = new imageModel(imgTitle.getText().toString().trim(), url.toString(), imgDesc.getText().toString() , s.toString());
                       DocumentReference df = firestore.collection("Gallery").document();
                       df.set(imageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
//                               Toast.makeText(getApplicationContext(),"Collection Added",Toast.LENGTH_LONG).show();

                           }
                       });

                     //  databaseReference.child(databaseReference.push().getKey()).setValue(imageModel);
                       progressDialog.dismiss();
                       Snackbar snackbar = Snackbar.make(findViewById(R.id.mainLayout), "Success", Snackbar.LENGTH_LONG);
                       snackbar.show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Faild To Uplaod",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading... ");

                }
            });

        }
        else{
            Toast.makeText(getApplicationContext(),"Please Select Image First", Toast.LENGTH_LONG).show();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photos"), 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data !=null && data.getData() !=null){
            file = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),file);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"failed bitmap", Toast.LENGTH_LONG).show();
            }
            imageView1.setImageBitmap(bitmap);
           // title.setText(file.toString());
          //  imageView.setVisibility(View.INVISIBLE);

        }
        else
            Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_LONG).show();

    }
}