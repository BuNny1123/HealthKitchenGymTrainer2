package com.bunny.healthkitchengymtrainer.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.Model.Category;
import com.bunny.healthkitchengymtrainer.Model.GymTrainer;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapter;
import com.bunny.healthkitchengymtrainer.YourTraineeActivity.YourTraineeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    private FirebaseAuth mAuth;
    String userId;
    TextView nav_name,nav_phoneNo,nav_email;
    NavigationView navigationView = null;
    Toolbar toolbar;
    ArrayList<Category> categoryList;


    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.toString().equals("Sign Out")){
                    mAuth.signOut();
                    finish();
//                    Intent intent = new Intent(HomeActivity.this, LoginScreenActivity.class);
//                    startActivity(intent);
                    Toast.makeText(HomeActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();


                }
                return false;
            }
        });


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        categoryList = new ArrayList<Category>();
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);



        //init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("category");
        //category.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                showDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void updateList(DataSnapshot dataSnapshot) {
        Category tempCategory;
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Log.d(TAG, "updateList: " + ds);
                tempCategory = ds.getValue(Category.class);
                Log.d(TAG, "updateList:?????????????????????? " + tempCategory.toString());
                categoryList.add(tempCategory);
            }

          Log.d(TAG, "updateList: " + categoryList.toString());
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(categoryList, HomeActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HomeActivity.this,1,LinearLayoutManager.HORIZONTAL, false);
        recycler_menu.setLayoutManager(layoutManager);
        recycler_menu.setItemAnimator(new DefaultItemAnimator());
        recycler_menu.setAdapter(recyclerAdapter);

    }


    private void showDatabase(DataSnapshot dataSnapshot) {

        userId = mAuth.getUid().toString();

        GymTrainer gymTrainer = new GymTrainer();
        nav_email = (TextView) findViewById(R.id.nav_toolbar_email);
        nav_name = (TextView) findViewById(R.id.nav_toolbar_name);
        nav_phoneNo = (TextView) findViewById(R.id.nav_toolbar_phoneNo);

        for( DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals("GymTrainer")){
                Log.d(TAG, "showDatabase: "+ds);
                try{ gymTrainer = ds.child(userId).getValue(GymTrainer.class);

                    Log.d(TAG, "Data?????????????????????: "+ gymTrainer.toString());
                    Log.d(TAG, "showDatabase: " + gymTrainer.getEmailID());

                    nav_name.setText(gymTrainer.getName());
                    nav_phoneNo.setText(gymTrainer.getPhoneNo());
                    nav_email.setText(gymTrainer.getEmailID());

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //setup trainees fragment initially
//            TraineesFragment fragment = new TraineesFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=
//                    getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container_home,fragment);
//            fragmentTransaction.commit();

        } else if (id == R.id.nav_trainee) {
            Intent intent = new Intent(HomeActivity.this, YourTraineeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_kitchen){
//            Intent intent = new Intent(HomeActivity.this,CartActivity.class );
//            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
