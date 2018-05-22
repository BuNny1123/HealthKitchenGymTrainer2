package com.bunny.healthkitchengymtrainer.HomeActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterFoodItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FoodActivity extends AppCompatActivity {

    private static final String TAG = "FoodActivity";

    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Foods> foodsArrayList;
    RecyclerView recyclerView;
    ImageView backArrow;
    Foods foods;
    ArrayList<String> keys;
    public String traineeID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // Write a message to the database
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("Foods");
         foodsArrayList = new ArrayList<Foods>();
         recyclerView = (RecyclerView) findViewById(R.id.recycler_food_menu);
         backArrow = (ImageView) findViewById(R.id.food_toolbar_backarrow);
         traineeID ="";
         traineeID = getIntent().getStringExtra("TraineeIdAddToDiet");


//         addCart.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//             }
//         });





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                udpateFoodList(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void udpateFoodList(DataSnapshot dataSnapshot) {


        for( DataSnapshot ds:dataSnapshot.getChildren()){

            //Log.d(TAG, "udpateFoodList: //////////////////" +ds.getValue());
            foods = ds.getValue(Foods.class);
            Log.d(TAG, "udpateFoodList: "+ ds.getKey());

            //Log.d(TAG, "udpateFoodList: " + getIntent().getStringExtra("CategoryName"));
           // Log.d(TAG, "udpateFoodList:>>>>>>>>>> " + foods.toString());
            if(!(getIntent().getStringExtra("CategoryName") ==null)){

                if(foods.getMenuCategory().equals( getIntent().getStringExtra("CategoryName")))
                    foodsArrayList.add(foods);
            }else
                foodsArrayList.add(foods);
            }

        //Log.d(TAG, "udpateFoodList: !!!!!!!!!!!!!!!!!!!!!!" + foodsArrayList.toString());
        RecyclerAdapterFoodItems recyclerAdapter = new RecyclerAdapterFoodItems(foodsArrayList, FoodActivity.this,traineeID);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FoodActivity.this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);

    }
}
