package com.bunny.healthkitchengymtrainer.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.FoodActivity.FoodActivity;
import com.bunny.healthkitchengymtrainer.LoginAndRegister.LoginScreenActivity;
import com.bunny.healthkitchengymtrainer.Model.Category;
import com.bunny.healthkitchengymtrainer.Model.GymTrainer;
import com.bunny.healthkitchengymtrainer.MyProfileActivity;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.SearchActivity.SearchActivity;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapter;
import com.bunny.healthkitchengymtrainer.YourTraineeActivity.YourTraineeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.bounce.library.BounceTouchListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    private FirebaseAuth mAuth;
    String userId;
    TextView nav_name,nav_phoneNo,nav_email;
    NavigationView navigationView = null;
    Toolbar toolbar;
    ArrayList<Category> categoryList;
    RelativeLayout progressBarLayout;
    LinearLayout homeContent;
    FloatingActionButton fab;
    RecyclerAdapter<RecyclerView.ViewHolder> recyclerAdapter;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    FirebaseDatabase database;
    DatabaseReference category,myRef;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    ImageView headerImage, myProfile;
    TextView searchBar;
    TextView searchText;
    ImageView searchImage;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.fadein , R.anim.fadeout);

        callSearch();

        searchBar = findViewById(R.id.search);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            //Toast.makeText(this, "Logged in as " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {

            loadWidgets();
            loadWidgetsOnClickListernes();
            initFirebase();
            intiRecyclerView();

            ScrollView scrollView = (ScrollView) findViewById(R.id.contentHomeScrollView);
            BounceTouchListener bounceTouchListener = new BounceTouchListener(scrollView);
            bounceTouchListener.setOnTranslateListener(new BounceTouchListener.OnTranslateListener() {
                @Override
                public void onTranslate(float translation) {
                    if (translation > 0) {
                        float scale = ((2 * translation) / headerImage.getMeasuredHeight()) + 1;
                        headerImage.setScaleX(scale);
                        headerImage.setScaleY(scale);
                    }
                }
            });
            //scrollView.setSmoothScrollingEnabled(true);
            scrollView.setOnTouchListener(bounceTouchListener);




            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            category.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressBarOn();
                    updateList(dataSnapshot);
                    progressBarOff();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(HomeActivity.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();

                }

            });


            //Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressBarOn();
                    showDatabase(dataSnapshot);
                    progressBarOff();


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                    Toast.makeText(HomeActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void intiRecyclerView() {
        recyclerAdapter = new RecyclerAdapter<RecyclerView.ViewHolder>(categoryList, HomeActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HomeActivity.this,1);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recycler_menu.setLayoutManager(layoutManager);
        recycler_menu.setLayoutAnimation(controller);
        recycler_menu.setAdapter(recyclerAdapter);

    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        category = database.getReference("category");
        //category.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
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

        recyclerAdapter.notifyDataSetChanged();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDatabase(DataSnapshot dataSnapshot) {

        if(mAuth!= null)
            userId = Objects.requireNonNull(mAuth.getUid());

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

                    String name = gymTrainer.getName();
                    nav_name.setText(name.substring(0,1).toUpperCase() + name.substring(1,name.length()));
                    nav_phoneNo.setText(gymTrainer.getPhoneNo());
                    nav_email.setText(gymTrainer.getEmailID());

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }


    }


    public void loadWidgets(){


        progressBarLayout = (RelativeLayout) findViewById(R.id.progressBar_Layout);
        headerImage = (ImageView) findViewById(R.id.homeScreenTopImage);
        myProfile = (ImageView) findViewById(R.id.myProfileHome);

        //Navigation
//        homeContent = (LinearLayout) findViewById(R.id.homeContentLayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        categoryList = new ArrayList<Category>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //RecylerViewInit
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);



    }

    public void loadWidgetsOnClickListernes(){

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, MyProfileActivity.class);
                startActivity(i);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, YourTraineeActivity.class);
                startActivity(intent);
            }
        });

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.toString().equals("Sign Out")) {
//                    progressBarOn();
//                    mAuth.signOut();
//
//                    Intent intent = new Intent(HomeActivity.this, LoginScreenActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    //Toast.makeText(HomeActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
//
//                }
//                return false;
//            }
//        });



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
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_trainee) {
            Intent intent = new Intent(this, YourTraineeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_kitchen){
            Intent intent = new Intent(this,FoodActivity.class );
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void progressBarOn(){

        progressBarLayout.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    public void progressBarOff(){

        progressBarLayout.setVisibility(View.GONE);
//        homeContent.setFocusable(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }


    private void callSearch() {
        searchText = findViewById(R.id.search);
        searchImage = findViewById(R.id.searchBarIcon);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this , SearchActivity.class);
                intent.putExtra("SearchText" , searchText.getText().toString());
                startActivity(intent);

            }
        });
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    Intent intent = new Intent(HomeActivity.this , SearchActivity.class);
                    intent.putExtra("SearchText" , textView.getText().toString());
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });

    }


}
