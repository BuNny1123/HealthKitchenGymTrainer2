package com.bunny.healthkitchengymtrainer.YourTraineeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterTrainee;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterYourTrainee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourTraineeActivity extends AppCompatActivity {

    FloatingActionButton addTraineeBtn;
    DatabaseReference myRef ;
    ArrayList<Trainee> traineeArrayList;
    ArrayList<String> traineeEmailArrayList;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;



    private static final String TAG = "YourTraineeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_trainee);

        traineeArrayList = new ArrayList<Trainee>();
        traineeEmailArrayList = new ArrayList<String>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("GymTrainer");
        mAuth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.yourTrainee_recyclerView);


        addTraineeBtn = (FloatingActionButton) findViewById(R.id.add_trainee_floatingBtn);
        addTraineeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourTraineeActivity.this , AddTraineeActivity.class);
                startActivity(intent);
            }
        });

        myRef.child(mAuth.getUid()).child("Trainees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot ds:dataSnapshot.getChildren()){
                  Log.d(TAG, "onDataChange:::::::::::::: "+ ds.getValue());
//                    Toast.makeText(YourTraineeActivity.this, "" + ds.getValue(), Toast.LENGTH_SHORT).show();
                    traineeEmailArrayList.add(ds.getValue().toString());
                }
                //Log.d(TAG, "onDataChange: >>>>>>>>>" + traineeEmailArrayList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef = database.getReference("Trainee");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Trainee trainee = ds.getValue(Trainee.class);
                    Log.d(TAG, "onDataChange:?????????????????? "+ trainee.getKey());
                    if(traineeEmailArrayList.contains(trainee.getKey()))
                        traineeArrayList.add(trainee);
                }
                Log.d(TAG, "onDataChange: " + traineeArrayList.toString());

                RecyclerAdapterYourTrainee recyclerAdapter = new RecyclerAdapterYourTrainee(traineeArrayList, YourTraineeActivity.this);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(YourTraineeActivity.this, 1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
