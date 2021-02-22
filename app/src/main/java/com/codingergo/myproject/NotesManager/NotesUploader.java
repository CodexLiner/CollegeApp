package com.codingergo.myproject.NotesManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codingergo.myproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class NotesUploader extends AppCompatActivity {
     Uri uri;
     EditText title , desc ;
     ImageView choose;
     Button addBtn ;
     Spinner spinner;
     String[] spinnerItem = {"Select Year" , "1st Year", "2nd Year" , "3rd Year"};
     FirebaseAuth auth;
     FirebaseFirestore firestore;
     StorageReference storageReference ;
     String branch , faculty ,date , sem;
    ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_uploader);
        title = findViewById(R.id.titleNote);
        desc = findViewById(R.id.descnote);
        addBtn = findViewById(R.id.buttonUpload);
        choose = findViewById(R.id.AddPDF);
        auth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.spinner);
        storageReference = FirebaseStorage.getInstance().getReference("NotesFolder");
        firestore = FirebaseFirestore.getInstance();
           p = new ProgressDialog(this);
           p.setCancelable(false);
        Chooser();
        uploader();
        Userdetails();
        SpinnerSet();


    }

    private void SpinnerSet() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item , spinnerItem);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position==0){

               }
               else {
                   sem = spinnerItem[position].substring(0,1);
                   //Toast.makeText(NotesUploader.this, "sem"+sem, Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Userdetails() {

        DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              branch = documentSnapshot.getString("branch");
              faculty = documentSnapshot.getString("fullname");

            }
        });
    }

    private void uploader() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if (uri!=null)
           {
               final  String subject = title.getText().toString().trim();
               final  String  Desc = desc.getText().toString().trim();
               final  String Year = sem;
               if (TextUtils.isEmpty(subject)){
                   title.requestFocus();
                   title.setError("Required");
                   return;
               }
               if (TextUtils.isEmpty(Desc)){
                   desc.requestFocus();
                   desc.setError("Required");
                   return;
               }
               if (TextUtils.isEmpty(Year)){
                   Toast.makeText(NotesUploader.this, "Select Year", Toast.LENGTH_SHORT).show();
                   return;
               }

               p.setMessage("Adding...");
               p.show();
               StorageReference reference = storageReference.child("notes"+System.currentTimeMillis()+".pdf");
               reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                       while (!uriTask.isComplete());
                       Uri url = uriTask.getResult();
                       DocumentReference df = firestore.collection("notes").document();
                       Map<String , Object> notes = new HashMap<>();
                       notes.put("faculty",faculty);
                       notes.put("branch", branch);
                       notes.put("desc" , Desc);
                       notes.put("subject",subject);
                       notes.put("sem", sem);
                       notes.put("url" , url.toString());
                       df.set(notes);
                       p.dismiss();
                       Snackbar snackbar = Snackbar.make(findViewById(R.id.mainLayout), "Success", Snackbar.LENGTH_LONG);
                       snackbar.show();
                       snackbar.setAction("Ok", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               snackbar.dismiss();
                           }
                       });

                   }
               }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       p.dismiss();
                   }
               });

           }
           else {
               Toast.makeText(NotesUploader.this, "Select File First", Toast.LENGTH_SHORT).show();
           }
          }
        });
    }

    private void Chooser() {
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent , 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && requestCode == 0 && data.getData()!=null){
            uri = data.getData();
            choose.setImageResource(R.drawable.uploadpanso);
           // Toast.makeText(this, "Choosed Yaar", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "File Not Selected ", Toast.LENGTH_SHORT).show();
        }
    }
}