package com.bunny.healthkitchengymtrainer.LoginAndRegister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.Model.UserGym;
import com.bunny.healthkitchengymtrainer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GymRegistration extends AppCompatActivity {
    private static final String TAG = "GymRegistration";

    TextView address_txt, city_txt,name_txt,phoneNo_txt;
    ProgressBar mProgressBar;
    TextView pleaseWait;
    Button register_gym;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_registration);

        address_txt = (TextView) findViewById(R.id.address_input_gym);
        city_txt = (TextView) findViewById(R.id.city_input_gym);
        name_txt =(TextView) findViewById(R.id.name_input_gym);
        phoneNo_txt = (TextView) findViewById(R.id.contactNo_input_gym);

        mProgressBar =(ProgressBar) findViewById(R.id.gym_progressBar);
        pleaseWait = (TextView) findViewById(R.id.pleaseWait_gym);
        mProgressBar.setVisibility(View.GONE);
        pleaseWait.setVisibility(View.GONE);

        register_gym = (Button) findViewById(R.id.register_gym);
        register_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =name_txt.getText().toString().toUpperCase();
                String address =  address_txt.getText().toString();
                String contact= phoneNo_txt.getText().toString();
                String city = city_txt.getText().toString();

                UserGym userGym = new UserGym(name,address,contact,city);
                Log.d(TAG, userGym.toString());
                myRef.child("Gym").child(name).setValue(userGym);

                Toast.makeText(GymRegistration.this, "Registration Successfull !", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
