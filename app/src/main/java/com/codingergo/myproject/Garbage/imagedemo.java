package com.codingergo.myproject.Garbage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codingergo.myproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class imagedemo extends AppCompatActivity {
 ImageView imageView;
 Uri filepath;
 Bitmap bitmap;
 FirebaseStorage storage;
 StorageReference databse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagedemo);
        imageView =findViewById(R.id.demoimage);
       storage = FirebaseStorage.getInstance();
       databse = storage.getReference();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(imagedemo.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent= new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,1);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                       permissionToken.continuePermissionRequest();
                    }
                }).check();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode== 1 && requestCode ==RESULT_OK){
            filepath = data.getData();
            imageView.setImageURI(filepath);
            Toast.makeText(getApplicationContext(),"added", Toast.LENGTH_LONG).show();

            try {
                InputStream inputStream= getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                upload();
            }
            catch (Exception ex){
                Log.i("msg", "onActivityResult: "+ex);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

    private void upload() {

        StorageReference riversRef = databse.child("images");

        riversRef.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                        Log.i("succeess", "onSuccess: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                        Log.i("failed", "onFailure: ");
                        // ...
                    }
                });
    }
    }