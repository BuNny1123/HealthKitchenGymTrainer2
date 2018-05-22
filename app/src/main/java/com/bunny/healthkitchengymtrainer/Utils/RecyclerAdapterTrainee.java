package com.bunny.healthkitchengymtrainer.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.Model.TrainerTraineeRelation;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.FoodViewHolder;
import com.bunny.healthkitchengymtrainer.ViewHolder.TraineeViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapterTrainee extends RecyclerView.Adapter<TraineeViewHolder> {


    ArrayList<Trainee> list;
    Context mContext;
    FirebaseAuth mAuth;
    DatabaseReference myRefAdd;
    FirebaseDatabase databaseAdd;

    public RecyclerAdapterTrainee(ArrayList<Trainee> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        databaseAdd = FirebaseDatabase.getInstance();
        myRefAdd = databaseAdd.getReference();

    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.trainee_cardview,parent ,false);
        TraineeViewHolder traineeViewHolder = new TraineeViewHolder(view);
        return traineeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeViewHolder holder, int position) {

         final Trainee myList = list.get(position);
         holder.name.setText(myList.getName());
         holder.email.setText(myList.getEmail());
         holder.addTraineeBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast toast = Toast.makeText(mContext, ""+ myList.getName(), Toast.LENGTH_SHORT);
                 View view1 = toast.getView();
                 view1.setMinimumWidth(50);
                 view1.setBackgroundResource(R.drawable.toast_background);
                 toast.show();


                String trainee = null;
                 trainee = myList.getKey();

                 myRefAdd.child("GymTrainer").child(mAuth.getUid()).child("Trainees").push().setValue(trainee);
             }
         });

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast toast = Toast.makeText(mContext, ""+ myList.getName(), Toast.LENGTH_SHORT);
                 View view1 = toast.getView();
                 view1.setMinimumWidth(50);
                 view1.setBackgroundResource(R.drawable.toast_background);
                 toast.show();
             }
         });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
