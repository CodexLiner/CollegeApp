package com.codingergo.myproject.users;

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

import com.codingergo.myproject.R;
import com.codingergo.myproject.Main.home;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //    public final String Mainurl ="https://cityapp.host/android/apiController.php/";
  EditText TextUser,   TextPass;

  private long backbutton;
  private Toast toast;
 TextView register,ForogotButton;
 ImageView arrow;
 Button Login;

    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ///
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //arrow =findViewById(R.id.rightarr);
        TextPass = (EditText) findViewById(R.id.LoginPassView);
        TextUser = (EditText) findViewById(R.id.RollView);
        Login = findViewById(R.id.SubmitButton);
        ForogotButton = findViewById(R.id.ForogotButton);
        register = (TextView)findViewById(R.id.onHelpButton);
        auth= FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        Sprite threebounce = new Circle();
        progressBar.setIndeterminateDrawable(threebounce);

        //// on register
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
         finish();
         startActivity(new Intent(getApplicationContext(), home.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
         overridePendingTransition(0,0);
         finishAfterTransition();
     }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = TextUser.getText().toString().trim();
                final String password = TextPass.getText().toString().trim();
                if (TextUtils.isEmpty(Username)){
                    TextUser.requestFocus();
                    TextUser.setError("Username Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    TextPass.requestFocus();
                    TextPass.setError("Username Required");
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
                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),home.class));
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                           // Toast.makeText(getApplicationContext(),"Failed"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"Invalid Username Or Password",Toast.LENGTH_LONG).show();
                            TextPass.setText("");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
//            end off onclick
        });

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

