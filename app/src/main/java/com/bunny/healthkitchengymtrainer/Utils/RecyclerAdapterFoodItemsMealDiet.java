package com.bunny.healthkitchengymtrainer.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.FoodMealDietViewHolder;

import java.util.ArrayList;

public class RecyclerAdapterFoodItemsMealDiet extends RecyclerView.Adapter<FoodMealDietViewHolder> {

    ArrayList<Foods> foodsArrayList;
    Context mContext;

    public RecyclerAdapterFoodItemsMealDiet(ArrayList<Foods> foodsArrayList, Context mContext) {
        this.foodsArrayList = foodsArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FoodMealDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_food_meal_diet , parent , false);
        FoodMealDietViewHolder foodMealDietViewHolder = new FoodMealDietViewHolder(view);
        return foodMealDietViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodMealDietViewHolder holder, int position) {

        Foods myList = foodsArrayList.get(position);
        Glide.with(mContext)
                .load(myList.getImage())
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.imageView.setImageBitmap(resource);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return foodsArrayList.size();
    }
}
