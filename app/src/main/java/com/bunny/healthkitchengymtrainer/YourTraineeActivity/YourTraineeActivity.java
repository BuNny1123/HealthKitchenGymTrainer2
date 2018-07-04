package com.bunny.healthkitchengymtrainer.YourTraineeActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    DatabaseReference myRef ,getMyRef ;
    ArrayList<Trainee> traineeArrayList;
    ArrayList<String> traineeEmailArrayList;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    ImageView backButton;
    TextView search_trainee;



    private static final String TAG = "YourTraineeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_trainee);

        traineeArrayList = new ArrayList<Trainee>();
        traineeEmailArrayList = new ArrayList<String>();
        backButton = (ImageView) findViewById(R.id.yourTrainee_toolbar_backarrow);
        search_trainee = (TextView) findViewById(R.id.search);

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search_trainee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ArrayList<Trainee> tempTraineeArrayList = new ArrayList<Trainee>();
                ArrayList<Trainee> temp1TraineeArrayList = traineeArrayList;

                for (Trainee trainee : temp1TraineeArrayList) {
                    if (trainee.getEmail().contains(charSequence)) {
                        tempTraineeArrayList.add(trainee);
                    }
                }
                Log.d(TAG, "onTextChanged&&&&&&&&&&&&&: " + tempTraineeArrayList.toString());
                if (!charSequence.equals(""))
                    //if(!tempTraineeArrayList.isEmpty())
                    temp1TraineeArrayList = tempTraineeArrayList;
                RecyclerAdapterYourTrainee recyclerAdapter = new RecyclerAdapterYourTrainee(temp1TraineeArrayList, YourTraineeActivity.this);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(YourTraineeActivity.this, 1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        myRef.child(mAuth.getUid()).child("Trainees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                traineeEmailArrayList.clear();

                for (DataSnapshot ds:dataSnapshot.getChildren()){
//                    Toast.makeText(YourTraineeActivity.this, "" + ds.getValue(), Toast.LENGTH_SHORT).show();
                    traineeEmailArrayList.add(ds.getValue().toString());
                }

                getMyRef = database.getReference("Trainee");
                getMyRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        traineeArrayList.clear();

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
                //Log.d(TAG, "onDataChange: >>>>>>>>>" + traineeEmailArrayList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
