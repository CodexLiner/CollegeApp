package com.codingergo.myproject.UserManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import MainActivity;
import com.codingergo.myproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    FirebaseFirestore firestore;
    ProgressBar progressBar;
    ImageView imageSelector;
    Spinner Spinner ;
    String [] Year = {"Select Year","First Year","Second Year","Final Year"};
    String StudentYaer , StudentBranch;
//    ProgressDialog progressDialog = new ProgressDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_sign_up);
          name = (EditText)findViewById(R.id.EditName);
          email =(EditText)findViewById(R.id.EditEmail);
          Spinner = findViewById(R.id.EditPass);
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

          tologin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(), MainActivity.class));

              }
          });

        // sign up button
        FirebaseStorage Firestorage = FirebaseStorage.getInstance();
        try {
            Signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    final String loginmaill = email.getText().toString().trim().toLowerCase();
                   // final String loginPass = password.getText().toString().trim();
                    final String loginame = name.getText().toString().trim();
                    final String loginroll = roll.getText().toString().trim();
                    final String loginphone = phone.getText().toString().trim();
                    final String loginaddress = address.getText().toString().trim();

                    if(TextUtils.isEmpty(loginmaill)){
                        email.requestFocus();
                        email.setError("Required");
                        return;
                    }
//                    if(TextUtils.isEmpty(loginPass)){
//                        password.requestFocus();
//                        password.setError("Required");
//                        return;
//                    }
                      if(!Patterns.EMAIL_ADDRESS.matcher(loginmaill).matches()) {
                          email.requestFocus();
                          email.setError("Email Not Valid");
                          return;
                      }

//                    if(loginPass.length() < 6){
//                        password.setError("password shoud be 6 charactor");
//
//                    }
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
                    studentInfo.put("sem",StudentYaer);
                    studentInfo.put("url",null);
                    studentInfo.put("branch",StudentBranch);
                    //access level is Student
                    studentInfo.put("isHod","0");
                    studentInfo.put("isUser","1");
                    df.set(studentInfo);
                    progressBar.setVisibility(View.GONE);
                    Doempty();
                    Snackbar snackbar = Snackbar.make( v ,"Student Added Succesfully", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }catch (Exception exception){
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        SpinnerChooser();
        userDetails();
    }

    private void userDetails() {
        DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                StudentBranch = documentSnapshot.getString("branch");

            }
        });
    }

    public void SpinnerChooser(){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this ,android.R.layout.simple_spinner_item,Year );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Spinner.setAdapter(adapter);
        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position== 0){

                }
                else {
                    StudentYaer = Year[position];
                   //  Toast.makeText(SignUp.this, ""+StudentYaer, Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void Doempty() {
        name.setText("");
        email.setText("");
        roll.setText("");
        address.setText("");
        phone.setText("");
        // password.setText("");
    }

}