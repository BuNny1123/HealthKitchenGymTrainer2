package com.bunny.healthkitchengymtrainer.DietActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.HomeActivity.FoodActivity;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.PagerAdapterDiet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DietActivity extends AppCompatActivity
    implements MondayDietFragment.OnFragmentInteractionListener,
        TuesdayDietFragment.OnFragmentInteractionListener,
        WednesdayFragment.OnFragmentInteractionListener,
        ThursdayDietFragment.OnFragmentInteractionListener,
        FridayDietFragment.OnFragmentInteractionListener,
        SaturdayDietFragment.OnFragmentInteractionListener,
        SundayDietFragment.OnFragmentInteractionListener {


    TabLayout tabLayout;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String traineeId;
    FloatingActionButton addToDiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_diet);
        addToDiet = (FloatingActionButton) findViewById(R.id.addToDiet);

        tabLayout.addTab(tabLayout.newTab().setText("Mon"));
        tabLayout.addTab(tabLayout.newTab().setText("Tues"));
        tabLayout.addTab(tabLayout.newTab().setText("Wed"));
        tabLayout.addTab(tabLayout.newTab().setText("Thur"));
        tabLayout.addTab(tabLayout.newTab().setText("Fri"));
        tabLayout.addTab(tabLayout.newTab().setText("Sat"));
        tabLayout.addTab(tabLayout.newTab().setText("Sun"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_diet);
        PagerAdapter adapter = new PagerAdapterDiet(getSupportFragmentManager(), 7);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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


        traineeId = getIntent().getStringExtra("TraineeId");
        Toast.makeText(this, "" + traineeId, Toast.LENGTH_SHORT).show();

        addToDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DietActivity.this, "Adding", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DietActivity.this , FoodActivity.class);
                intent.putExtra("TraineeIdAddToDiet", traineeId);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
