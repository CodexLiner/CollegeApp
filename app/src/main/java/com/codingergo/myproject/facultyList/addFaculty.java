package com.codingergo.myproject.facultyList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codingergo.myproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addFaculty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
 String courseList[] = {"Select Qualification","Msc" , "Bsc" , "Btech"};
 Spinner spinner;
 String tCourse;
 EditText Tname, Temail, Tphone, Taddress;
 Button addButton;
 FirebaseFirestore firestore;
 FirebaseAuth auth;
 String Branch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> s1 = new  ArrayAdapter(this, android.R.layout.simple_spinner_item,courseList );
        s1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(s1);
        spinner.setOnItemSelectedListener(this);
        Tname = findViewById(R.id.EditName);
        Temail = findViewById(R.id.EditEmail);
        Tphone= findViewById(R.id.EditPhone);
        Taddress=findViewById(R.id.editAddress);
        addButton= findViewById(R.id.add_faculty);
        firestore= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Checker();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Tname.getText().toString().trim();
                String email = Temail.getText().toString().trim();
                String phone = Tphone.getText().toString().trim();
                String address = Taddress.getText().toString().trim();
                String degree = tCourse;
                if (TextUtils.isEmpty(name)){
                    Tname.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    Taddress.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    Tphone.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    Temail.requestFocus();
                    Temail.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(degree)){
                    Snackbar snackBar = Snackbar .make(v, "Please Select Qualification", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackBar.show();
                   return;
                }

                DocumentReference df = firestore.collection(Branch+"_Teachers").document();
                Map<String,Object> t = new HashMap<>();
                t.put("name",name);
                t.put("email",email);
                t.put("phone",phone);
                t.put("address",address);
                t.put("degree",degree);
                t.put("hello","hello");
                df.set(t).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar snackBar = Snackbar .make(v, "Success", Snackbar.LENGTH_LONG);
                        snackBar.show();
                        Doempty();

                    }
                });

            }
        });
    }

    private void Checker() {
        DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("branch")!=null)
                {
                    Branch = documentSnapshot.getString("branch").toString().trim();
                   // Toast.makeText(addFaculty.this, "Not Null", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(addFaculty.this, "Error Contact Admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Doempty() {
        Taddress.setText("");
        Tphone.setText("");
        Tname.setText("");
        Temail.setText("");

    }

    private void selected() {
        Toast.makeText(this, " is"+ tCourse
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position==0){
//            Toast.makeText(this, ""
//                    , Toast.LENGTH_SHORT).show();
        }
        else{

            tCourse = courseList[position];
           // selected();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}