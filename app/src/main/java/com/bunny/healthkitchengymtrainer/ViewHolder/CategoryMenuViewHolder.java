package com.bunny.healthkitchengymtrainer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bunny.healthkitchengymtrainer.Interface.ItemClickListener;
import com.bunny.healthkitchengymtrainer.R;

public class CategoryMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;
    public ProgressBar progressBar_categoryCard;


    private ItemClickListener itemClickListener;

    public CategoryMenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);
        progressBar_categoryCard = (ProgressBar) itemView.findViewById(R.id.progressbar_CategoryCardView);
        progressBar_categoryCard.setVisibility(View.VISIBLE);


        itemView.setOnClickListener(this);
    }

    public void ItemClickListner(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition() , false);

    }
}
