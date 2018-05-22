package com.bunny.healthkitchengymtrainer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bunny.healthkitchengymtrainer.Interface.ItemClickListener;
import com.bunny.healthkitchengymtrainer.R;

public class TraineeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public  TextView name,email,phone;
    public Button addTraineeBtn;

    private ItemClickListener itemClickListener;

    public TraineeViewHolder(View itemView) {
        super(itemView);

        name =(TextView) itemView.findViewById(R.id.name_trainee_cardView);
        email = (TextView) itemView.findViewById(R.id.email_trainee_cardView);
        addTraineeBtn = (Button) itemView.findViewById(R.id.add_trainee_cardView_btn);
        //phone = (TextView) itemView.findViewById(R.id.pho)
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition() , false);

    }
}
