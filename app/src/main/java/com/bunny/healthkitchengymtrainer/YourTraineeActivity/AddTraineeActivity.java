package com.bunny.healthkitchengymtrainer.YourTraineeActivity;

import android.os.Build;
import android.os.Bundle;
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

import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterTrainee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddTraineeActivity extends AppCompatActivity {
    private static final String TAG = "GymRegistration";

    TextView searchText;
    TextView emailTrainee;
    ImageView addTrainee_toolbar_backarrow;
    Button register_gym;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseDatabase databaseAdd = FirebaseDatabase.getInstance();
    DatabaseReference myRefAdd = database.getReference("TrainerTraineeRelation");
    RecyclerView recyclerView;

    ArrayList<Trainee> traineeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trainee);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_trainee);
        mAuth = FirebaseAuth.getInstance();
        emailTrainee = (TextView) findViewById(R.id.email_trainee_cardView);
        addTrainee_toolbar_backarrow = findViewById(R.id.addTrainee_toolbar_backarrow);
        addTrainee_toolbar_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        traineeArrayList = new ArrayList<Trainee>();

        searchText = (TextView) findViewById(R.id.search_trainee_text);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Trainee");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                traineeArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Log.d(TAG, "onDataChange: " + ds.getValue());
                    traineeArrayList.add(ds.getValue(Trainee.class));
                    RecyclerAdapterTrainee recyclerAdapter = new RecyclerAdapterTrainee(traineeArrayList, AddTraineeActivity.this);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AddTraineeActivity.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recyclerAdapter);
                }
                Log.d(TAG, "onDataChange: ''''''''''''" + traineeArrayList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//       addTrainee.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               //                String trainer=null, trainee=null;
////                trainer = mAuth.getUid().toString();
////                trainee = emailTrainee.getText().toString();
////
////                TrainerTraineeRelation trainerTraineeRelation = new
////                        TrainerTraineeRelation(trainer, trainee);
////
////                myRefAdd.setValue(trainerTraineeRelation);
//
//           }
//       });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ArrayList<Trainee> tempTraineeArrayList = new ArrayList<Trainee>();
                ArrayList<Trainee> temp1TraineeArrayList = traineeArrayList;

                //Toast.makeText(AddTraineeActivity.this, ""+charSequence, Toast.LENGTH_SHORT).show();
                for (Trainee trainee : temp1TraineeArrayList) {
                    if (trainee.getEmail().contains(charSequence)) {
                        tempTraineeArrayList.add(trainee);
                        // Toast.makeText(AddTraineeActivity.this, ""+ trainee.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d(TAG, "onTextChanged&&&&&&&&&&&&&: " + tempTraineeArrayList.toString());
                if (!charSequence.equals(""))
                    //if(!tempTraineeArrayList.isEmpty())
                    temp1TraineeArrayList = tempTraineeArrayList;
                RecyclerAdapterTrainee recyclerAdapter = new RecyclerAdapterTrainee(temp1TraineeArrayList, AddTraineeActivity.this);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AddTraineeActivity.this, 1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerAdapter);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }
}
