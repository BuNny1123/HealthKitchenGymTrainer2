package com.bunny.healthkitchengymtrainer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bunny.healthkitchengymtrainer.Interface.ItemClickListener;
import com.bunny.healthkitchengymtrainer.R;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView foodName, foodDesc, foodPrice,noOfItems,addToQuickPick, finalPrice,foodDisc;
    public ImageView foodImage,addCart,removeCart;
    public String key;
    private ItemClickListener itemClickListener;
    public RelativeLayout relativeLayout;

    public FoodViewHolder(final View itemView) {
        super(itemView);

        foodName = (TextView) itemView.findViewById(R.id.name_food_cardView);
        foodDesc = (TextView) itemView.findViewById(R.id.desc_food_cardView);
        foodPrice = (TextView) itemView.findViewById(R.id.priceBeforeDisc);
        foodImage = (ImageView) itemView.findViewById(R.id.image_food_cardMenu);
        addCart = (ImageView) itemView.findViewById(R.id.addCard_btn);
        removeCart = (ImageView) itemView.findViewById(R.id.removeCard_btn);
        noOfItems = (TextView) itemView.findViewById(R.id.foodCartNoOfItems_text);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.foodItemBuyButton);
        addToQuickPick = (TextView) itemView.findViewById(R.id.addToQuickPick);
        finalPrice = (TextView) itemView.findViewById(R.id.price_food_cardView);
        foodDisc = (TextView) itemView.findViewById(R.id.disc_food_cardView);


        //itemView.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        //itemClickListener.onClick(view, getAdapterPosition() , false);
    }
}
