package com.bunny.healthkitchengymtrainer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bunny.healthkitchengymtrainer.Interface.ItemClickListener;
import com.bunny.healthkitchengymtrainer.R;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView foodName, foodDesc, foodPrice;
    public ImageView foodImage,addCart,removeCart;
    public String key;
    private ItemClickListener itemClickListener;


    public FoodViewHolder(final View itemView) {
        super(itemView);

        foodName = (TextView) itemView.findViewById(R.id.name_food_cardView);
        foodDesc = (TextView) itemView.findViewById(R.id.desc_food_cardView);
        foodPrice = (TextView) itemView.findViewById(R.id.price_food_cardView);
        foodImage = (ImageView) itemView.findViewById(R.id.image_food_cardMenu);
        addCart = (ImageView) itemView.findViewById(R.id.addCard_btn);
        removeCart = (ImageView) itemView.findViewById(R.id.removeCard_btn);

        itemView.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition() , false);
    }
}
