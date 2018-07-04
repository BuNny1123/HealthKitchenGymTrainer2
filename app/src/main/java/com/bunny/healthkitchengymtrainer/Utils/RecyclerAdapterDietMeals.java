package com.bunny.healthkitchengymtrainer.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bunny.healthkitchengymtrainer.FoodActivity.FoodActivity;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.Model.Meal;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.MealViewHolder;
import com.bunny.healthkitchengymtrainer.ViewHolder.MenuViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapterDietMeals extends RecyclerView.Adapter<MealViewHolder> {

    ArrayList<Meal> mealArrayList;
    Context mContext;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myReference = firebaseDatabase.getReference();

    private int extendedCardViewFlag = 0;

    public RecyclerAdapterDietMeals(ArrayList<Meal> mealArrayList, Context mContext) {
        this.mealArrayList = mealArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_diet_meal, parent , false);
        MealViewHolder mealViewHolder = new MealViewHolder(view);

        return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MealViewHolder holder, int position) {

        final Meal myList = mealArrayList.get(position);
        Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/digital-7.ttf");
        holder.time.setTypeface(custom_font);
        holder.extendedCardViewDietMeals.setVisibility(View.GONE);

        try {
            if (Integer.parseInt(myList.getDateTime().toString().substring(0, 2)) > 12
                    && Integer.parseInt(myList.getDateTime().toString().substring(5, 7)) > 0) {
                int hours = Integer.parseInt(myList.getDateTime().toString().substring(0, 2)) - 12;
                int minutes = Integer.parseInt(myList.getDateTime().toString().substring(5, 7));
                holder.time.setText(hours + ":" + minutes);
                holder.amPm.setText("PM");
            } else {
                int hours = Integer.parseInt(myList.getDateTime().toString().substring(0, 2));
                int minutes = Integer.parseInt(myList.getDateTime().toString().substring(5, 7));
                holder.time.setText(hours + ":" + minutes);
                holder.amPm.setText("AM");
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        final ArrayList<Foods> foodsArrayList = new ArrayList<>();
        myReference.child("Foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(myList.getFoods().containsKey(ds.getValue(Foods.class).getKey()))
                        foodsArrayList.add(ds.getValue(Foods.class));
                }

                RecyclerAdapterFoodItemsMealDiet recyclerAdapterFoodItemsMealDiet = new RecyclerAdapterFoodItemsMealDiet(foodsArrayList ,mContext);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 1 ,LinearLayoutManager.HORIZONTAL ,false);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_fall_down);
                holder.recyclerView.setLayoutManager(layoutManager);
                holder.recyclerView.setLayoutAnimation(controller);
                holder.recyclerView.setAdapter(recyclerAdapterFoodItemsMealDiet);
                RecyclerAdapterMenu recyclerAdapterMenu = new RecyclerAdapterMenu(foodsArrayList ,mContext , null);
                RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(mContext, 1 );
                final LayoutAnimationController controller1 =
                        AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_fall_down);
                holder.recyclerViewExtended.setLayoutManager(layoutManager1);
                holder.recyclerViewExtended.setLayoutAnimation(controller1);
                holder.recyclerViewExtended.setAdapter(recyclerAdapterMenu);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(extendedCardViewFlag == 0){
                    holder.extendedCardViewDietMeals.setVisibility(View.VISIBLE);



                    extendedCardViewFlag=1;
                }else {
                    holder.extendedCardViewDietMeals.setVisibility(View.GONE);
                    extendedCardViewFlag=0;
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }
}
