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
import com.bunny.healthkitchengymtrainer.Model.Category;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.FoodViewHolder;
import com.bunny.healthkitchengymtrainer.ViewHolder.MenuViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class    RecyclerAdapterMenu extends RecyclerView.Adapter<MenuViewHolder> {

    ArrayList<Foods> list;
    Context mContext;
    HashMap<String,Integer> foodList;

    public RecyclerAdapterMenu(ArrayList<Foods> list, Context mContext,HashMap<String,Integer> foodList) {
        this.list = list;
        this.mContext = mContext;
        this.foodList = foodList;
    }



    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_menu_foodnext,parent ,false);
        MenuViewHolder menuViewHolder = new MenuViewHolder(view);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, int position) {

        final Foods myList = list.get(position);
        holder.menu_name.setText(myList.getName().toString());
        Glide.with(mContext)
                .load(myList.getImage())
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.menu_imageView.setImageBitmap(resource);
                    }
                });
//        if(myList != null)
  //          holder.menu_quantity.setText(foodList.get(myList.getKey())+ " Pcs");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
