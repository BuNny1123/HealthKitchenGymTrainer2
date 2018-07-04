package com.bunny.healthkitchengymtrainer.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bunny.healthkitchengymtrainer.Model.Category;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterMenu;

import java.util.ArrayList;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView menu_imageView;
    public TextView menu_name,menu_quantity,amount;


    public MenuViewHolder(View itemView) {
        super(itemView);

        menu_imageView = (ImageView) itemView.findViewById(R.id.image_menu_foodNext);
        menu_name = (TextView) itemView.findViewById(R.id.menu_name_foodNext);
        menu_quantity = (TextView) itemView.findViewById(R.id.quantity_menu_foodNext);
        amount = (TextView) itemView.findViewById(R.id.amount_menu_foodNext);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}
