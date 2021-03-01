package com.codingergo.myproject.Assignment;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Assignment extends AppCompatActivity {
    TextView pdfname ,select , selected;
    Button padupload;
    ImageView chooser ,choosed;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Uri path;
    java.util.Date Date = new Date();
    SimpleDateFormat ft =
            new SimpleDateFormat("dd-MM-yyyy");
    String date = (ft.format(Date));
    String branch ,Fname ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        storageReference = FirebaseStorage.getInstance().getReference("Assignments");
        databaseReference = FirebaseDatabase.getInstance().getReference("Assignments");
        pdfname = (TextView)findViewById(R.id.PDF_name);
        padupload= (Button)findViewById(R.id.pdf_loader);
        chooser = (ImageView)findViewById(R.id.Chooser);
        choosed = (ImageView)findViewById(R.id.choosed);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        select = findViewById(R.id.selectonTap);
        selected = findViewById(R.id.selected);

        UserDetails();


        padupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (path !=null){
                    upload(path);
                }
                else
                    Toast.makeText(getApplicationContext(),"pathh Null",Toast.LENGTH_LONG).show();

            }
        });
        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePDF();
            }
        });
    }

    private void UserDetails() {
        DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               branch = documentSnapshot.getString("branch");
                Fname = documentSnapshot.getString("fullname");
            }
        });
    }

    private void upload(Uri path) {
        final ProgressDialog  progressDialog = new ProgressDialog(this);
       // progressDialog.setTitle("Uploading");
        progressDialog.show();
        StorageReference reference = storageReference.child("Assignment"+System.currentTimeMillis()+".pdf");
        reference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isComplete());
                Uri uri = task.getResult();
//                noticeModel noticeModel = new noticeModel(pdfname.getText().toString() ,uri.toString(), date);
//                databaseReference.child(databaseReference.push().getKey()).setValue(noticeModel);
                DocumentReference df = firestore.collection("Drills").document();
                Map<String,Object> Drill = new HashMap<>();
                Drill.put("subject" , "Subject");
                Drill.put("branch" , branch);
                Drill.put("title" , pdfname.getText().toString());
                Drill.put("url" , uri.toString());
                Drill.put("date" ,date);
                Drill.put("Fname" , Fname);
                df.set(Drill).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Assignment.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Assignment.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });


                pdfname.setText("");
                choosed.setVisibility(View.INVISIBLE);
                chooser.setVisibility(View.VISIBLE);
                select.setVisibility(View.VISIBLE);
                selected.setVisibility(View.INVISIBLE);
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double run = ((100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading..."+(int)run+ " %");

            }
        });

    }

    private void choosePDF() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select FIle"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 &&resultCode == RESULT_OK && data!=null&& data.getData() !=null){
            chooser.setVisibility(View.INVISIBLE);
            choosed.setVisibility(View.VISIBLE);
            selected.setVisibility(View.VISIBLE);
            select.setVisibility(View.INVISIBLE);
            path = data.getData();
        }
        else
           Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
    }
}