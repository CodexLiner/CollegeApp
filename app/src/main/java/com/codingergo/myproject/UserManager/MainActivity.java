    package com.codingergo.myproject.UserManager;

    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.codingergo.myproject.MailSender.MailFunctions;
    import com.codingergo.myproject.TeachersDashboard.DashBoard;
    import com.codingergo.myproject.R;
    import com.codingergo.myproject.Main.home;
    import com.codingergo.myproject.StudentDashboard.studentdashboard;
    import com.github.ybq.android.spinkit.sprite.Sprite;
    import com.github.ybq.android.spinkit.style.Circle;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.HashMap;
    import java.util.Map;

    public class MainActivity extends AppCompatActivity {
        //    public final String Mainurl ="https://cityapp.host/android/apiController.php/";
      EditText TextUser,   TextPass;
      private long backbutton;
      private Toast toast;
      TextView register,ForogotButton;
      ImageView arrow;
      Button Login;
      FirebaseFirestore firestore;
      String name , email , roll , address , mobile ,isUser , IsHod , url ,sem ,branch;
      FirebaseAuth auth;
      ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //arrow =findViewById(R.id.rightarr);
            TextPass = (EditText) findViewById(R.id.LoginPassView);
            TextUser = (EditText) findViewById(R.id.RollView);
            Login = findViewById(R.id.SubmitButton);
            ForogotButton = findViewById(R.id.ForogotButton);
            register = (TextView)findViewById(R.id.onHelpButton);
            auth= FirebaseAuth.getInstance();
            firestore = FirebaseFirestore.getInstance();
            progressBar = findViewById(R.id.progressBar);
            Sprite threebounce = new Circle();
            progressBar.setIndeterminateDrawable(threebounce);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SignUp.class));


                }
            });
            ForogotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), forgotPass.class));
                }
            });
         if (auth.getCurrentUser()!= null){

             startActivity(new Intent(getApplicationContext(), home.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
             overridePendingTransition(0,0);
             finish();
         }
            Login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    Login.setEnabled(false);

                    final String Username = TextUser.getText().toString().trim().toLowerCase();
                    final String password = TextPass.getText().toString().trim();
                    if (TextUtils.isEmpty(Username)){
                        TextUser.requestFocus();
                        TextUser.setError("Username Required");
                        Login.setEnabled(true);
                        return;
                    }
                    if (TextUtils.isEmpty(password)){
                        TextPass.requestFocus();
                        TextPass.setError("Password Required");
                        Login.setEnabled(true);
                        return;
                    }
    //                if(Patterns.EMAIL_ADDRESS.matcher(Username).matches()){
    //                    TextUser.requestFocus();
    //                    TextUser.setError("Username Not Valid");
    //                    return;
    //
    //                }
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(Username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_LONG).show();
                           //     checkAccess(auth.getCurrentUser().getUid());
                                   startActivity(new Intent(getApplicationContext(),home.class));
                                MailFunctions mailFunctions = new MailFunctions();
                                mailFunctions.MailSender(Username);
                                return;
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                Login.setEnabled(true);
                                return;
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            auth.createUserWithEmailAndPassword(Username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isComplete()){
                                        DocumentReference df = firestore.collection("Students").document(Username);
                                        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.getString("email")!=null){
                                                    email = documentSnapshot.getString("email");
                                                    mobile = documentSnapshot.getString("mobile");
                                                    roll = documentSnapshot.getString("roll");
                                                    address = documentSnapshot.getString("address");
                                                    name = documentSnapshot.getString("fullname");
                                                    isUser = documentSnapshot.getString("isUser");
                                                    IsHod = documentSnapshot.getString("isHod");
                                                    url = documentSnapshot.getString("url");
                                                    sem = documentSnapshot.getString("sem");
                                                    branch = documentSnapshot.getString("branch");

                                                    DocumentReference df = firestore.collection("Students").document(Username);
                                                    df.delete();
                                                }
                                                else{
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    Toast.makeText(getApplicationContext(),"Email is"+Username,Toast.LENGTH_LONG).show();
                                                    FirebaseUser cu = auth.getCurrentUser();
                                                    cu.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                         //   Toast.makeText(getApplicationContext(),"Removing Success", Toast.LENGTH_LONG).show();
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            TextUser.setText("");
                                                            TextPass.setText("");
                                                            Login.setEnabled(true);

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                          // Toast.makeText(getApplicationContext(),"Removing Fail", Toast.LENGTH_LONG).show();
                                                           progressBar.setVisibility(View.INVISIBLE);

                                                        }
                                                    });
                                                }
                                             }
                                        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (email!=null){
                                                    FirebaseUser user = auth.getCurrentUser();
                                                    DocumentReference documentReference = firestore.collection("Users").document(user.getUid());
                                                    Map<String ,Object> studentInfo = new HashMap<>();
                                                    studentInfo.put("fullname",name);
                                                    studentInfo.put("email", email);
                                                    studentInfo.put("roll", roll);
                                                    studentInfo.put("mobile",mobile);
                                                    studentInfo.put("address",address);
                                                    studentInfo.put("isHod",IsHod);
                                                    studentInfo.put("url",url);
                                                    studentInfo.put("branch",branch);
                                                    studentInfo.put("sem",sem);
                                                    //access level is Student
                                                    studentInfo.put("isUser",isUser);
                                                    documentReference.set(studentInfo);
                                                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(getApplicationContext(),home.class));
                                                    overridePendingTransition(0,0);
                                                    MailFunctions mailFunctions = new MailFunctions();
                                                    mailFunctions.MailSender(Username);
                                                }
                                             }
                                        });
                                    }

                                }
                            });

                        }
                    });
                }
    //            end off onclick
            });

        }

        private void checkAccess(String uid) {
            DocumentReference df = firestore.collection("Students").document(uid);
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isStudent") !=null){
                        startActivity(new Intent(getApplicationContext(), studentdashboard.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        finish();;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onStop() {
            super.onStop();
        }


        @Override
        public void onBackPressed() {

            if(backbutton +2000 >System.currentTimeMillis()) {
                toast.cancel();
                super.onBackPressed();
                finishAffinity();
            }
            else{
             toast =   Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT);
             toast.show();
            }
            backbutton= System.currentTimeMillis();
        }

    }

