package com.codingergo.myproject.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import MainActivity;
import com.codingergo.myproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText name, email, password, phone, roll , address;
    Button Signup , Button;
    Uri filepath;
    Bitmap bitmap;
    TextView tologin;
    FirebaseAuth auth;
    FirebaseFirestore firestore ;
    ProgressBar progressBar;
    ImageView imageSelector;
//    ProgressDialog progressDialog = new ProgressDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_sign_up);
          name = (EditText)findViewById(R.id.EditName);
          email =(EditText)findViewById(R.id.EditEmail);
          password = (EditText)findViewById(R.id.EditPass);
          phone = (EditText)findViewById(R.id.EditPhone);
          address= (EditText)findViewById(R.id.editAddress);
          roll = (EditText)findViewById(R.id.EditRoll);
          auth = FirebaseAuth.getInstance();
          firestore = FirebaseFirestore.getInstance();
          imageSelector= (ImageView)findViewById(R.id.imageView);
         // Button = findViewById(R.id.button);
          tologin = findViewById(R.id.LoginActbutton);
          Signup = findViewById(R.id.SignUpButton);
//          LocalDateTime myObj = LocalDateTime.now();
          progressBar = findViewById(R.id.progressBar2);
//          progressDialog.setTitle("Uploading");


          ///image selecting proccess
//        imageSelector.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dexter.withActivity(SignUp.this)
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                                Intent intent= new Intent(Intent.ACTION_PICK);
//                                intent.setType("image/*");
//                                startActivityForResult(intent,1);
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                                permissionToken.continuePermissionRequest();
//                            }
//                        }).check();
//            }
//        });
          /// login button
          tologin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(), MainActivity.class));

              }
          });


//       if( auth.getCurrentUser()!=null)
//       {
//           startActivity(new Intent(getApplicationContext(),MainActivity.class));
//           finish();
//        }
        /// image upload proocees
//        progressDialog.show();
//        FirebaseStorage Firestorage = FirebaseStorage.getInstance();
//        final StorageReference uploadering = Firestorage.getReference("image");
//
//        uploadering.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
//        {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                uploadering.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(final Uri uri) {
//
//
//                        final String loginmaill = email.getText().toString().trim();
//                        final String loginPass = password.getText().toString().trim();
//                        final String loginame = name.getText().toString().trim();
//                        final String loginroll = roll.getText().toString().trim();
//                        final String loginphone = phone.getText().toString().trim();
//                        final String loginaddress = address.getText().toString().trim();
//
//                        if(TextUtils.isEmpty((CharSequence) loginmaill)){
//                            email.requestFocus();
//                            email.setError("Required");
//                            return;
//                        }
//                        if(Patterns.EMAIL_ADDRESS.matcher(loginmaill).matches()){
//                            email.requestFocus();
//
//                        }
//                        if(TextUtils.isEmpty(loginPass)){
//                            password.requestFocus();
//                            password.setError("Required");
//                            return;
//                        }
//                        if(loginPass.length() < 6){
//                            password.setError("password shoud be 6 charactor");
//
//                        }
//                        if(TextUtils.isEmpty(loginphone)){
//                            phone.requestFocus();
//                            phone.setError("Required");
//                            return;
//                        }
//                        if(TextUtils.isEmpty( loginroll)){
//                            roll.requestFocus();
//                            roll.setError("Required");
//                            return;
//                        }
//                        if(TextUtils.isEmpty(loginame)){
//                            name.requestFocus();
//                            name.setError("Required");
//                            return;
//                        }
//                        if(TextUtils.isEmpty(loginaddress)){
//                            address.requestFocus();
//                            address.setError("Required");
//                            return;
//                        }
//
//                        auth.createUserWithEmailAndPassword(loginmaill,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(SignUp.this, "Complete Registration", Toast.LENGTH_LONG);
//                                    User user = new User(loginmaill, loginame, loginphone, loginphone , loginaddress , uri.toString());
//                                    FirebaseDatabase.getInstance().getReference("Users")
//                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                Toast.makeText(SignUp.this,"Registartion Successfull",Toast.LENGTH_LONG).show();
//                                                progressBar.setVisibility(View.GONE);
//                                            }
//                                            else{
//                                                Toast.makeText(SignUp.this, "Error" +task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                                                progressBar.setVisibility(View.GONE);
//                                            }
//                                        }
//                                    });
//                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                                    finish();
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                                else {
////                             Toast.makeText(SignUp.this, "Error" +task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                                    progressBar.setVisibility(View.GONE);
//
//                                }
//
//                            }
//                        });
//
//                    }
//                });
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                float per = (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
////                progressDialog.setMessage("Uploaded"+(int)per+"%");
//
//            }
//        });
        // sign up button
        FirebaseStorage Firestorage = FirebaseStorage.getInstance();
         Signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(final View v) {

                     final String loginmaill = email.getText().toString().trim().toLowerCase();
                     final String loginPass = password.getText().toString().trim();
                     final String loginame = name.getText().toString().trim();
                     final String loginroll = roll.getText().toString().trim();
                     final String loginphone = phone.getText().toString().trim();
                     final String loginaddress = address.getText().toString().trim();

                         if(TextUtils.isEmpty(loginmaill)){
                             email.requestFocus();
                             email.setError("Required");
                             return;
                     }
                     if(Patterns.EMAIL_ADDRESS.matcher(loginmaill).matches()){
                         email.requestFocus();

                 }
                     if(TextUtils.isEmpty(loginPass)){
                         password.requestFocus();
                         password.setError("Required");
                         return;
                     }
//                  if(Patterns.EMAIL_ADDRESS.matcher(loginmaill).matches()) {
//                      email.requestFocus();
//                      email.setError("Username Not Valid");
//                      return;
//                  }

                     if(loginPass.length() < 6){
                         password.setError("password shoud be 6 charactor");

                     }
                     if(TextUtils.isEmpty(loginphone)){
                         phone.requestFocus();
                         phone.setError("Required");
                         return;
                     }
                     if(TextUtils.isEmpty( loginroll)){
                         roll.requestFocus();
                         roll.setError("Required");
                         return;
                     }
                     if(TextUtils.isEmpty(loginame)){
                         name.requestFocus();
                         name.setError("Required");
                         return;
                     }
                     if(TextUtils.isEmpty(loginaddress)){
                        address.requestFocus();
                        address.setError("Required");
                        return;
                     }
                     DocumentReference df = firestore.collection("Students").document(loginmaill);
                         Map<String ,Object> studentInfo = new HashMap<>();
                         studentInfo.put("fullname",loginame);
                         studentInfo.put("email", loginmaill);
                         studentInfo.put("roll", loginroll);
                         studentInfo.put("mobile",loginphone);
                         studentInfo.put("address",loginaddress);
                         //access level is Student
                         studentInfo.put("isUser","1");
                         df.set(studentInfo);
                  progressBar.setVisibility(View.GONE);
                  Doempty();
                  Snackbar snackbar = Snackbar.make( v ,"Student Added Succesfully", Snackbar.LENGTH_LONG);
                 snackbar.show();
             }
         });



    }

    private void Doempty() {
        name.setText("");
        email.setText("");
        roll.setText("");
        address.setText("");
        phone.setText("");
        password.setText("");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode== 1 && requestCode ==RESULT_OK){
//            filepath = data.getData();
//            try {
//                InputStream inputStream= getContentResolver().openInputStream(filepath);
//                bitmap = BitmapFactory.decodeStream(inputStream);
//                imageSelector.setImageBitmap(bitmap);
//            }
//            catch (Exception ex){
//                Log.i("msg", "onActivityResult: "+ex);
//
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}