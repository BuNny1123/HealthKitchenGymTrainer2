package com.bunny.healthkitchengymtrainer.FoodActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.DietActivity.DietActivity;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.Model.Meal;
import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapter;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterFoodItems;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodActivityNext extends AppCompatActivity {

    //ListView listView;

    private static final String TAG = "FoodActivityNext";
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Foods");
    DatabaseReference getMyRef = database.getReference("Meals");
    DatabaseReference getGetMyRef = database.getReference("Trainee");
    TimePicker timePicker;
    ArrayList<Foods> foodArrayList;
    RecyclerView recyclerView;
    RecyclerAdapterMenu recyclerAdapter;
    Button done;
    String time,day,trainerId,dayFull;
    TextView name,dayTextView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_next);
      //  listView = (ListView) findViewById(R.id.list);

        final HashMap<String,Integer> foodList = (HashMap<String, Integer>) getIntent().getSerializableExtra("FoodList");
        Toast.makeText(this, "" + foodList.toString(), Toast.LENGTH_SHORT).show();
        timePicker = (TimePicker) findViewById(R.id.timeFoodNext);
        foodArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        done = (Button) findViewById(R.id.done);
        day = getIntent().getStringExtra("Day").toString();
        trainerId = getIntent().getStringExtra("TraineeId").toString();
//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
//                Toast.makeText(FoodActivityNext.this, "TIME :" + i+" Hrs "
//                        +i1+" min", Toast.LENGTH_SHORT).show();
//            }
//        });

        dayTextView = (TextView) findViewById(R.id.dayFoodNext);
        switch(Integer.parseInt(day)){
            case 0: dayFull= "Monday";
                    break;
            case 1: dayFull= "Tuesday";
                break;
            case 2: dayFull= "Wednesday";
                break;
            case 3: dayFull= "Thursday";
                break;
            case 4: dayFull= "Friday";
                break;
            case 5: dayFull= "Saturday";
                break;
            case 6: dayFull= "Sunday";
                break;
        }
        dayTextView.setText(dayFull);
        name = (TextView) findViewById(R.id.nameTraineeFoodnext);
        getGetMyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Trainee trainee = new Trainee();

                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    if(ds.getKey().equals(trainerId)){
                        trainee = ds.getValue(Trainee.class);
                    }

                }

                name.setText(trainee.getName());
                Log.d(TAG, "onDataChange: " + trainee.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    //Log.d(TAG, "onDataChange: " + dataSnapshot1.getKey());
                    if(foodList.containsKey(dataSnapshot1.getKey())){

                        foodArrayList.add(dataSnapshot1.getValue(Foods.class));


                    }

                }
                    Log.d(TAG, "onDataChange:////////// " + foodArrayList.toString());

                recyclerAdapter = new RecyclerAdapterMenu(foodArrayList ,
                        FoodActivityNext.this, foodList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FoodActivityNext.this,1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(FoodActivityNext.this, "", Toast.LENGTH_SHORT).show();

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(timePicker.getHour()<10 && timePicker.getMinute()>10)
                    time = "0" + timePicker.getHour() + " : " +timePicker.getMinute() ;
                else if(timePicker.getHour()>10 && timePicker.getMinute()<10)
                    time = timePicker.getHour() + " : 0" +timePicker.getMinute() ;
                else if(timePicker.getHour()<10 && timePicker.getMinute()<10)
                    time = "0" + timePicker.getHour() + " : 0" +timePicker.getMinute() ;
                else
                    time = timePicker.getHour() + " : " +timePicker.getMinute() ;
                Toast.makeText(FoodActivityNext.this, time + " " + day + " " + trainerId,
                        Toast.LENGTH_SHORT).show();

                Meal meal =  new Meal(dayFull , time, foodList);
                getMyRef.child(trainerId).push().setValue(meal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(FoodActivityNext.this , DietActivity.class);
                        Toast.makeText(FoodActivityNext.this, "Done!", Toast.LENGTH_SHORT).show();
                        intent.putExtra("TraineeId" , trainerId);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
