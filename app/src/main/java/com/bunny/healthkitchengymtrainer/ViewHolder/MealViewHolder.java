package com.bunny.healthkitchengymtrainer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bunny.healthkitchengymtrainer.R;

public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public RecyclerView recyclerView;
    public TextView descriptionText ,time, amPm;

    public RecyclerView recyclerViewExtended;
    public RelativeLayout extendedCardViewDietMeals;



    public MealViewHolder(View itemView) {
        super(itemView);

        recyclerView = itemView.findViewById(R.id.foodItemsRecyclerViewDietMeal);
        descriptionText = itemView.findViewById(R.id.cardViewDietMealDescription);
        time = itemView.findViewById(R.id.timeMealDietCardView);
        amPm = itemView.findViewById(R.id.amPmMealDietCardView);
        extendedCardViewDietMeals = itemView.findViewById(R.id.extendedCardViewDietMeals);
        recyclerViewExtended = itemView.findViewById(R.id.recyclerViewFootItemMealExtended);


    }

    @Override
    public void onClick(View view) {

    }
}
