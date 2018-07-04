package com.bunny.healthkitchengymtrainer.DietActivity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bunny.healthkitchengymtrainer.Model.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetAllMeals extends AppCompatActivity {


    private static final String TAG = "GetAllMeals";
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Meals"); 
    }
    
    public GetAllMeals(final String traineeId){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Meal> mealArrayList = new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.child(traineeId).getChildren()){

                    Meal meal1 =  ds.getValue(Meal.class);
                    mealArrayList.add(meal1);
                }
                Log.d(TAG, "onCreate: " + mealArrayList.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
