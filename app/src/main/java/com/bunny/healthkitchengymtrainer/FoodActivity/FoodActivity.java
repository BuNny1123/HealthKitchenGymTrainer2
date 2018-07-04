package com.bunny.healthkitchengymtrainer.FoodActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bunny.healthkitchengymtrainer.HomeActivity.HomeActivity;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.SearchActivity.SearchActivity;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterFoodItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.bounce.library.BounceTouchListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.graphics.Typeface.BOLD;


public class FoodActivity extends AppCompatActivity {

    private static final String TAG = "FoodActivity";

    FirebaseDatabase database;
    DatabaseReference myRef, getMyRef;
    ArrayList<Foods> foodsArrayList;
    RecyclerView recyclerView;
    ImageView backArrow;//, homeScreenTopImage;
    Foods foods;
    ArrayList<String> quickPicks;
    public String traineeID;
    TextView next, quickPickTextView;
    RecyclerAdapterFoodItems recyclerAdapter;
    FirebaseAuth mAuth;
    RelativeLayout progressBarLayout;
    TextView searchText;
    ImageView searchImage;

    private int quickPicksFlag = 1;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        searchText = findViewById(R.id.search);
        searchImage = findViewById(R.id.searchBarIcon);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    if (searchText.getText() != null) {

                        progressBarOn();

                        ArrayList<Foods> finalFoodsArrayList = new ArrayList<>();

                        for (Foods foods : foodsArrayList) {
                            if (foods.getName().toLowerCase().contains(searchText.getText()) ||
                                    foods.getDescription().toLowerCase().contains(searchText.getText())) {
                                finalFoodsArrayList.add(foods);
                            }
                        }

                        recyclerAdapter = new RecyclerAdapterFoodItems(finalFoodsArrayList, FoodActivity.this, traineeID, quickPicks);
                    } else {
                        recyclerAdapter = new RecyclerAdapterFoodItems(foodsArrayList, FoodActivity.this, traineeID, quickPicks);

                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FoodActivity.this, 1);
                    final LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(FoodActivity.this, R.anim.layout_animation_fall_down);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setLayoutAnimation(controller);
                    recyclerView.setAdapter(recyclerAdapter);
                    progressBarOff();

                    return true;
                }return false;
                }

        });
//        searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if(!(charSequence == "" ||charSequence ==null)) {
//
//                    progressBarOn();
//                    ArrayList<Foods> finalFoodsArrayList = new ArrayList<>();
//
//                    for (Foods foods : foodsArrayList) {
//                        if (foods.getName().toLowerCase().contains(charSequence) ||
//                                foods.getDescription().toLowerCase().contains(charSequence)) {
//                            finalFoodsArrayList.add(foods);
//                        }
//                    }
//
//                    recyclerAdapter =  new RecyclerAdapterFoodItems(finalFoodsArrayList ,FoodActivity.this ,traineeID , quickPicks);
//                }else{
//                    recyclerAdapter =  new RecyclerAdapterFoodItems(foodsArrayList ,FoodActivity.this ,traineeID , quickPicks);
//
//                }
//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FoodActivity.this, 1);
//                final LayoutAnimationController controller =
//                        AnimationUtils.loadLayoutAnimation(FoodActivity.this, R.anim.layout_animation_fall_down);
//                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setLayoutAnimation(controller);
//                recyclerView.setAdapter(recyclerAdapter);
//                progressBarOff();
//
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        // Write a message to the database
        progressBarLayout = findViewById(R.id.progressBar_Layout);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Foods");
        getMyRef = database.getReference("GymTrainer");
        foodsArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_food_menu);
        backArrow = findViewById(R.id.food_toolbar_backarrow);
        traineeID = "";
        quickPicks = new ArrayList<>();
        traineeID = getIntent().getStringExtra("TraineeIdAddToDiet");
        next =  findViewById(R.id.nextFoodActivity);
        mAuth = FirebaseAuth.getInstance();
        quickPickTextView =  findViewById(R.id.quickPicks);
       // homeScreenTopImage =  findViewById(R.id.homeScreenTopImage);

//        Glide.with(this)
//                .load(R.drawable.explore)
//                .asBitmap()
//                .centerCrop()
//                .placeholder(R.drawable.explore)
//                .into(homeScreenTopImage);

//        ScrollView scrollView = findViewById(R.id.contentFoodScrollView);
//        BounceTouchListener bounceTouchListener = new BounceTouchListener(scrollView);
//        bounceTouchListener.setOnTranslateListener(new BounceTouchListener.OnTranslateListener() {
//            @Override
//            public void onTranslate(float translation) {
//                if (translation > 0) {
//                    float scale = ((2 * translation) / homeScreenTopImage.getMeasuredHeight()) + 1;
//                    homeScreenTopImage.setScaleX(scale);
//                    homeScreenTopImage.setScaleY(scale);
//                }
//            }
//        });
//        scrollView.setOnTouchListener(bounceTouchListener);

        getMyRef.child(Objects.requireNonNull(mAuth.getUid())).child("Quick Picks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    progressBarOn();
                    quickPicks.add(Objects.requireNonNull(ds.getValue()).toString());
                    progressBarOff();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (traineeID == null) {
            next.setVisibility(View.GONE);
           // homeScreenTopImage.setVisibility(View.VISIBLE);
        } else {
            //homeScreenTopImage.setVisibility(View.GONE);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!recyclerAdapter.getFoodList().isEmpty()) {
                        Toast.makeText(FoodActivity.this, recyclerAdapter.getFoodList().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FoodActivity.this, FoodActivityNext.class);
                        intent.putExtra("FoodList", recyclerAdapter.getFoodList());
                        intent.putExtra("Day", getIntent().getStringExtra("DayMenu"));
                        intent.putExtra("TraineeId", getIntent().getStringExtra("TraineeId"));
                        startActivity(intent);
                    }
                    else {
                        Toast toast = Toast.makeText(FoodActivity.this, "Please  select some Food Items First", Toast.LENGTH_SHORT);
                        View view1 = toast.getView();
                        view1.setBackgroundResource(R.color.background_dark);
                        view1.setMinimumWidth(50);
                        toast.show();
                    }

                }
            });
        }

        quickPickTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                if (quickPicksFlag == 1) {

                    quickPickTextView.setTextSize(18.0f);
                    //quickPickTextView.setTextColor(R.color.blue);
                    quickPickTextView.setTypeface(null, BOLD);

                    ArrayList<Foods> tempFoodsArrayList = new ArrayList<>();
                    for (Foods foods : foodsArrayList) {
                        if (quickPicks.contains(foods.getKey())) {
                            tempFoodsArrayList.add(foods);
                        }
                    }

                    recyclerAdapter = new RecyclerAdapterFoodItems(tempFoodsArrayList, FoodActivity.this, traineeID, quickPicks);
                    quickPicksFlag = 0;
                } else {

                    quickPickTextView.setTextSize(16.0f);
                    //quickPickTextView.setTextColor(R.color.white);
                    quickPickTextView.setTypeface(null, Typeface.NORMAL);


                    recyclerAdapter = new RecyclerAdapterFoodItems(foodsArrayList, FoodActivity.this, traineeID, quickPicks);
                    quickPicksFlag = 1;
                }

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FoodActivity.this, 1);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(FoodActivity.this, R.anim.layout_animation_fall_down);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.setAdapter(recyclerAdapter);


            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBarOn();
                udpateFoodList(dataSnapshot);
                progressBarOff();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(FoodActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();

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

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            foods = ds.getValue(Foods.class);
            if (!(getIntent().getStringExtra("CategoryName") == null)) {
                if (foods.getMenuCategory().equals(getIntent().getStringExtra("CategoryName")))
                    foodsArrayList.add(foods);
            } else
                foodsArrayList.add(foods);
        }

        Log.d(TAG, "udpateFoodList: " + quickPicks.toString());
        recyclerAdapter = new RecyclerAdapterFoodItems(foodsArrayList, FoodActivity.this, traineeID,quickPicks);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FoodActivity.this, 1);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(recyclerAdapter);

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
}
