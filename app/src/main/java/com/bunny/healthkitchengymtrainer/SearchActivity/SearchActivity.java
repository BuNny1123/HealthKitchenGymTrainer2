package com.bunny.healthkitchengymtrainer.SearchActivity;

import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.Model.Meal;
import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.PagerAdapterDiet;
import com.bunny.healthkitchengymtrainer.Utils.PagerAdapterSearch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SearchActivity extends AppCompatActivity
    implements FoodSearchFragment.OnFragmentInteractionListener,
    TraineeSearchFragment.OnFragmentInteractionListener{


    TabLayout tabLayout;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef,getMyRef;
    ViewPager viewPager;
    PagerAdapterSearch adapter;
    TextView searchText;
    ArrayList<Foods> foodsArrayList;
    ArrayList<String> traineeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Meals");
        myRef.keepSynced(true);
        searchText = (TextView) findViewById(R.id.search);
        searchText.setText(getIntent().getStringExtra("SearchText"));
        final ArrayList<Foods> finalFoodsArrayList = new ArrayList<>();



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Foods");
        foodsArrayList = new ArrayList<>();
        traineeArrayList = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                    foodsArrayList.add(ds.getValue(Foods.class));
                Log.d(TAG, "onCreateView: " + foodsArrayList.toString());

                for (Foods foods : foodsArrayList) {
                    if (foods.getName().toLowerCase().contains(getIntent().getStringExtra("SearchText"))
                            || foods.getDescription().contains(getIntent().getStringExtra("SearchText"))) {
                        finalFoodsArrayList.add(foods);

                    }
                }
                adapter = new PagerAdapterSearch(getSupportFragmentManager() , 1 , finalFoodsArrayList , traineeArrayList);
                viewPager.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getMyRef = database.getReference();
        getMyRef.child("GymTrainer").child(mAuth.getUid()).child("Trainees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                    traineeArrayList.add(ds.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, ""+getIntent().getStringExtra("SearchText"), Toast.LENGTH_SHORT).show();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_search);
        tabLayout.addTab(tabLayout.newTab().setText("Food"));
        tabLayout.addTab(tabLayout.newTab().setText("Trainee"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager_search);



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                ArrayList<Foods> finalFoodsArrayList = new ArrayList<>();
//
//                for(Foods foods:foodsArrayList){
//                    if( foods.getName().toLowerCase().contains(charSequence) || foods.getDescription().contains(searchText.getText())){
//                        finalFoodsArrayList.add(foods);
//                    }
//                }
//
//                adapter = new PagerAdapterSearch(getSupportFragmentManager() , 1 , finalFoodsArrayList , traineeArrayList);
//                viewPager.setAdapter(adapter);
////                adapter.notifyDataSetChanged();
//
//               // Toast.makeText(SearchActivity.this, "" + finalFoodsArrayList.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
