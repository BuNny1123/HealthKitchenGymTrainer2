package com.bunny.healthkitchengymtrainer.LoginAndRegister;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.HomeActivity.HomeActivity;
import com.bunny.healthkitchengymtrainer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreenActivity extends AppCompatActivity {

    TextView email_input;
    TextView password_input;
    ProgressBar mProgressBar;
    Button login_btn;
    Button reg_btn;
    private FirebaseAuth mAuth;
    CheckBox showPassword;

    private static final String TAG = "LoginScreenActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        login_btn = (Button) findViewById(R.id.login_button);


        email_input = (TextView) findViewById(R.id.input_email);
        password_input = (TextView) findViewById(R.id.input_password);
        mProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        reg_btn = (Button) findViewById(R.id.register_btn);
        showPassword = (CheckBox) findViewById(R.id.showPassword);
        mAuth = FirebaseAuth.getInstance();
        mProgressBar.setVisibility(View.GONE);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(showPassword.isChecked())
                   password_input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
               else
                   password_input.setTransformationMethod(PasswordTransformationMethod.getInstance());
           }
       });

        login_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String email = email_input.getText().toString();
                 String password = password_input.getText().toString();

                 if (!(email.isEmpty() || password.isEmpty())) {

                    try{
                     mAuth.signInWithEmailAndPassword(email, password)
                             .addOnCompleteListener(LoginScreenActivity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         // Sign in success, update UI with the signed-in user's information
                                         Log.d(TAG, "signInWithEmail:success");
                                         FirebaseUser user = mAuth.getCurrentUser();

                                         Toast toast = Toast.makeText(LoginScreenActivity.this, "Logged in as "+ user.getEmail(), Toast.LENGTH_SHORT);
                                         View view = toast.getView();
                                         view.setBackgroundResource(R.drawable.toast_background);
                                         toast.show();

                                         Intent intent = new Intent(LoginScreenActivity.this, HomeActivity.class);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                         startActivity(intent);


                                     } else {
                                         // If sign in fails, display a message to the user.
                                         Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        // Toast.makeText(LoginScreenActivity.this, "Authentication failed.",
                                          //       Toast.LENGTH_SHORT).show();
                                         AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginScreenActivity.this);
                                         mBuilder.setMessage("Wrong EmailID or Password ");
                                         mBuilder.setTitle("Error Message");
                                         mBuilder.setPositiveButton("OK" , null);
                                         mBuilder.setCancelable(true);
                                         mBuilder.create().show();
                                     }

                                 }
                             });

                 }catch (Exception e){
                        e.printStackTrace();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginScreenActivity.this);
                        mBuilder.setMessage("Some Error Occured! Please try Again");
                        mBuilder.setTitle("Error Message");
                        mBuilder.setPositiveButton("OK" , null);
                        mBuilder.setCancelable(true);
                        mBuilder.create().show();

                    }
                 }else{
                     AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginScreenActivity.this);
                     mBuilder.setMessage("EmailID or Password cannot be left Blank");
                     mBuilder.setTitle("Error Message");
                     mBuilder.setPositiveButton("OK" , null);
                     mBuilder.setCancelable(true);
                     mBuilder.create().show();
                 }
             }
         }
        );


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreenActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this, "Logged in as " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Sign in Please", Toast.LENGTH_SHORT).show();
        }
    }


}
