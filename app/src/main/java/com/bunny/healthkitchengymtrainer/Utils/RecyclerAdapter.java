package com.bunny.healthkitchengymtrainer.Utils;

import android.content.Context;
import android.content.Intent;
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
import com.bunny.healthkitchengymtrainer.FoodActivity.FoodActivity;
import com.bunny.healthkitchengymtrainer.Model.Category;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.CategoryMenuViewHolder;

import java.util.ArrayList;


public class RecyclerAdapter<M extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<CategoryMenuViewHolder> {

    ArrayList<Category> list;
    Context mContext;

    public RecyclerAdapter(ArrayList<Category> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_cardview_home,parent ,false);
        CategoryMenuViewHolder menuViewHolder = new CategoryMenuViewHolder(view);
        return menuViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryMenuViewHolder holder, int position) {

        holder.progressBar_categoryCard.setVisibility(View.VISIBLE);
        final Category myList = list.get(position);
        holder.txtMenuName.setText(myList.getName());
//        Glide.with(mContext)
//                .load(myList.getImage())
//                .asBitmap()
//                .centerCrop()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        holder.imageView.setImageBitmap(resource);
//                    }
//                });

        final Category clickItem = myList;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(mContext, ""+ clickItem.getName(), Toast.LENGTH_SHORT);
                View view1 = toast.getView();
                view1.setMinimumWidth(50);
                view1.setBackgroundResource(R.drawable.toast_background);
                toast.show();

                Intent intent  = new Intent(mContext , FoodActivity.class);
                intent.putExtra("CategoryName", clickItem.getName());
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        holder.progressBar_categoryCard.setVisibility(View.GONE);

       // holder.progressBar_categoryCard.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return  list.size() ;
    }
}
