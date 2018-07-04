package com.bunny.healthkitchengymtrainer.LoginAndRegister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.Model.GymTrainer;
import com.bunny.healthkitchengymtrainer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    TextView email_txt, password_txt, name_txt, phoneNo_txt;
    ProgressBar mProgressBar;
    TextView pleaseWait;
    Button register;
    ImageView addGym, showPasswordReg;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference();
    ArrayList<String> mStrings;
    int flag = 0, showPasswordFlag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProgressBar = (ProgressBar) findViewById(R.id.register_progressBar);
        pleaseWait = (TextView) findViewById(R.id.pleaseWait_register);
        email_txt = (TextView) findViewById(R.id.email_input_reg);
        password_txt = (TextView) findViewById(R.id.password_input_reg);
        register = (Button) findViewById(R.id.register);
        name_txt = (TextView) findViewById(R.id.name_input_reg);
        phoneNo_txt = (TextView) findViewById(R.id.phoneNo_input_reg);
        mAuth = FirebaseAuth.getInstance();
        mStrings = new ArrayList<String>();
        mProgressBar.setVisibility(View.GONE);
        pleaseWait.setVisibility(View.GONE);
        showPasswordReg = (ImageView) findViewById(R.id.showPasswordReg);

        showPasswordReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPasswordFlag == 0) {
                    password_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPasswordReg.setImageResource(R.drawable.ic_show_password_black);
                    showPasswordFlag = 1;

                } else {
                    password_txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPasswordReg.setImageResource(R.drawable.ic_show_password);
                    showPasswordFlag = 0;
                }
            }
        });

        // getGymIDs();
        // Log.d(TAG, "onCreate: "+ mStrings.toString());


        mStrings.add("Gym_1");
        mStrings.add("Gym_2");
        mStrings.add("Gym_3");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, mStrings);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        addGym = (ImageView) findViewById(R.id.addGym);
        addGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, GymRegistration.class);
                startActivity(intent);

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            final String email = email_txt.getText().toString();
                                            final String password = password_txt.getText().toString();
                                            final String name = name_txt.getText().toString();
                                            final String phoneNo = phoneNo_txt.getText().toString();
                                            mProgressBar.setVisibility(View.VISIBLE);
                                            pleaseWait.setVisibility(View.GONE);

                                            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNo.isEmpty()) {
                                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                                mBuilder.setMessage("Please fill all the fields!");
                                                mBuilder.setTitle("No field can be left empty");
                                                mBuilder.setPositiveButton("OK", null);
                                                mBuilder.setCancelable(true);
                                                mBuilder.create().show();
                                                mProgressBar.setVisibility(View.GONE);

                                            } else {


                                                    mAuth.createUserWithEmailAndPassword(email, password)
                                                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                        if (task.isSuccessful()) {
                                                                            // Sign in success, update UI with the signed-in user's information
                                                                            Log.d(TAG, "createUserWithEmail:success");
                                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                                            user.sendEmailVerification();



                                                                            //addUserDetails();
                                                                            GymTrainer userGymTrainer = new GymTrainer(email, password, phoneNo, name);
                                                                            myRef.child("GymTrainer").child(mAuth.getUid()).setValue(userGymTrainer);


                                                                            Toast.makeText(RegisterActivity.this, "Verification Code sent to your Email ID",
                                                                                    Toast.LENGTH_SHORT).show();
                                                                            mAuth.signOut();

                                                                            Intent intent = new Intent(RegisterActivity.this, LoginScreenActivity.class);
                                                                            startActivity(intent);


                                                                        } else {
                                                                            try {
                                                                                throw task.getException();

                                                                            }catch (FirebaseAuthUserCollisionException e){
                                                                                Toast.makeText(RegisterActivity.this, "Email Already Exists" , Toast.LENGTH_SHORT).show();
                                                                            }catch (FirebaseAuthWeakPasswordException e){
                                                                                Toast.makeText(RegisterActivity.this, ""+ e.getReason(), Toast.LENGTH_SHORT).show();
                                                                            }catch (FirebaseAuthInvalidCredentialsException e){
                                                                                Toast.makeText(RegisterActivity.this, "Wrong Email ID" , Toast.LENGTH_SHORT).show();
                                                                            }catch (Exception e){
                                                                                Toast.makeText(RegisterActivity.this, "Some error occurred\nPlease try Again " , Toast.LENGTH_SHORT).show();
                                                                            }
                                                                            // If sign in fails, display a message to the user.
                                                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                                                            //Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                                              //      Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        // ...
                                                                        mProgressBar.setVisibility(View.GONE);

                                                                    }

                                                                });


                                            }

                                        }
                                    }


        );


    }
}
