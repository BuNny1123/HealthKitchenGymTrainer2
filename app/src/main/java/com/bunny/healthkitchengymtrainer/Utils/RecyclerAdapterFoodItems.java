package com.bunny.healthkitchengymtrainer.Utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bunny.healthkitchengymtrainer.FoodActivity.FoodItemActivity;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.FoodViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;


public class RecyclerAdapterFoodItems extends RecyclerView.Adapter<FoodViewHolder> {


    ArrayList<Foods> list;
    ArrayList<String> quickPicks;
    Context mContext;
    String id;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference() , getMyRef = database.getReference("GymTrainer");
    public HashMap<String,Integer> foodList = new HashMap<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public RecyclerAdapterFoodItems(ArrayList<Foods> list, Context mContext, String id,ArrayList<String> quickPicks) {
        this.list = list;
        this.mContext = mContext;
        this.id = id;
        this.quickPicks = quickPicks ;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(id != null){
            View view = LayoutInflater.from(mContext).inflate(R.layout.food_item_cardview,parent ,false);
            FoodViewHolder foodViewHolder = new FoodViewHolder(view);
            return foodViewHolder;
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.food_cardview_notrainee,parent ,false);
            FoodViewHolder foodViewHolder = new FoodViewHolder(view);
            return foodViewHolder;
        }

    }

    @SuppressLint("SetTextI18n")
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

                Intent intent =   new Intent(mContext , FoodItemActivity.class);
                intent.putExtra("FoodItem" , (Parcelable) myList);
                mContext.startActivity(intent);
            }
        });

        int price = Integer.parseInt(myList.getPrice());
        int disc = (1 - ((Integer.parseInt(myList.getDiscount())/100)));
        holder.finalPrice.setText("Rs "+(price * disc)+"/-");

        holder.foodDisc.setText("-" +myList.getDiscount() + "%");



        if(id != null) {

            holder.noOfItems.setText("0");
            holder.addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(mContext, "Adding " + myList.getKey() + " " + id, Toast.LENGTH_SHORT).show();
                    if (foodList.containsKey(myList.getKey()))
                        foodList.put(myList.getKey(), foodList.get(myList.getKey()) + 1);
                    else
                        foodList.put(myList.getKey(), 1);{
                        holder.noOfItems.setText(foodList.get(myList.getKey()).toString());
                        holder.relativeLayout.setBackgroundResource(R.color.primary_red);
                    }


                }
            });

            holder.removeCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(mContext, "Removing " + myList.getKey() + " " + id, Toast.LENGTH_SHORT).show();
                    if (foodList.containsKey(myList.getKey()))
                        if (foodList.get(myList.getKey()) > 1) {
                            foodList.put(myList.getKey(), foodList.get(myList.getKey()) - 1);
                            holder.noOfItems.setText(foodList.get(myList.getKey()).toString());
                        } else if (foodList.get(myList.getKey()) == 1) {
                            foodList.remove(myList.getKey());
                            holder.noOfItems.setText("0");
                            holder.relativeLayout.setBackgroundResource(R.color.primary_orange);
                        }
                }
            });

            if (holder.noOfItems.getText().equals("0"))
                holder.relativeLayout.setBackgroundResource(R.color.background_light);
            else
                holder.relativeLayout.setBackgroundResource(R.color.background_dark);

        }else {

            if (quickPicks != null) {
                if (quickPicks.contains(myList.getKey())) {
                    Log.d(TAG, "onBindViewHolder1: " + quickPicks.toString());
                    Log.d(TAG, "onBindViewHolder1: " + myList.getKey());
                    holder.relativeLayout.setBackgroundResource(R.color.primary_red);
                    holder.addToQuickPick.setText("Added to Quick Picks");
                } else {
                    holder.relativeLayout.setBackgroundResource(R.color.primary_orange);
                    holder.addToQuickPick.setText("Quick Pick");

                }

                Log.d(TAG, "onBindViewHolder: " + quickPicks.toString());

                holder.addToQuickPick.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {

                        if (!quickPicks.contains(myList.getKey())) {
                            myRef.child("GymTrainer").child(Objects.requireNonNull(mAuth.getUid())).child("Quick Picks").push().setValue(myList.getKey());
                            holder.relativeLayout.setBackgroundResource(R.color.primary_red);
                            holder.addToQuickPick.setText("Added to Quick Picks");
                        } else {
                            Log.d(TAG, "onClick: Removing");
                            myRef.child("GymTrainer").child(mAuth.getUid()).child("Quick Picks").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (myList.getKey().equals(ds.getValue())) {
                                            ds.getRef().removeValue();
                                            quickPicks.remove(myList.getKey());
                                            holder.relativeLayout.setBackgroundResource(R.color.primary_orange);
                                            holder.addToQuickPick.setText("Quick Pick");

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                });

            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public HashMap<String, Integer> getFoodList() {
        return foodList;
    }
}
