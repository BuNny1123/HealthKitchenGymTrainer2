package com.bunny.healthkitchengymtrainer.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.DietActivity.DietActivity;
import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.Model.TrainerTraineeRelation;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.TraineeViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapterYourTrainee extends RecyclerView.Adapter<TraineeViewHolder> {


    ArrayList<Trainee> list;
    Context mContext;
    FirebaseAuth mAuth;
    DatabaseReference myRefAdd;
    FirebaseDatabase databaseAdd;

    public RecyclerAdapterYourTrainee(ArrayList<Trainee> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        databaseAdd = FirebaseDatabase.getInstance();
        myRefAdd = databaseAdd.getReference("TrainerTraineeRelation");

    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.your_trainee_cardview,parent ,false);
        TraineeViewHolder traineeViewHolder = new TraineeViewHolder(view);
        return traineeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TraineeViewHolder holder, int position) {

         final Trainee myList = list.get(position);
         holder.name.setText(myList.getName());
         holder.email.setText(myList.getEmail());

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast toast = Toast.makeText(mContext, ""+ myList.getName(), Toast.LENGTH_SHORT);
                 View view1 = toast.getView();
                 view1.setMinimumWidth(50);
                 view1.setBackgroundResource(R.drawable.toast_background);
                 toast.show();


//                 String trainer = null, trainee = null;
//                 trainer = mAuth.getUid().toString();
//                 trainee = myList.getEmail().toString();
//
//                 TrainerTraineeRelation trainerTraineeRelation = new
//                         TrainerTraineeRelation(trainer, trainee);
//
//                 //myRefAdd.updateChildren(trainer);
//                 HashMap<String, Object> result = new HashMap<>();
//                 result.put("trainee" ,trainee);
//
//                 myRefAdd.child(trainer).push().updateChildren(result);



             }
         });

         holder.addTraineeBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(mContext , DietActivity.class);
                 intent.putExtra("TraineeId", myList.getKey());
                 mContext.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
