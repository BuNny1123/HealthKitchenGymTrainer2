package com.bunny.healthkitchengymtrainer.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bunny.healthkitchengymtrainer.HomeActivity.FoodActivity;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.FoodViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class RecyclerAdapterFoodItems extends RecyclerView.Adapter<FoodViewHolder> {


    ArrayList<Foods> list;
    Context mContext;
    String id;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Trainee");



    public RecyclerAdapterFoodItems(ArrayList<Foods> list, Context mContext, String id) {
        this.list = list;
        this.mContext = mContext;
        this.id = id;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.food_item_cardview,parent ,false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder holder, int position) {

        final Foods myList = list.get(position);
        holder.foodName.setText(myList.getName());
        holder.foodDesc.setText(myList.getDescription());
        holder.foodPrice.setText("Rs "+myList.getPrice());
        holder.key = myList.getKey();
        Glide.with(mContext)
                .load(myList.getImage())
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.foodImage.setImageBitmap(resource);
                    }
                });
        final Foods clickItem = myList;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(mContext, ""+ clickItem.getKey(), Toast.LENGTH_SHORT);
                View view1 = toast.getView();
                view1.setMinimumWidth(50);
                view1.setBackgroundResource(R.drawable.toast_background);
                toast.show();
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "Adding "+myList.getKey()+ " "+ id, Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
