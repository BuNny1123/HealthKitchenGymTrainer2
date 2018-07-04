package com.bunny.healthkitchengymtrainer.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.DietActivity.DietActivity;
import com.bunny.healthkitchengymtrainer.Model.Trainee;
import com.bunny.healthkitchengymtrainer.Model.TrainerTraineeRelation;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.ViewHolder.TraineeViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;

public class RecyclerAdapterYourTrainee extends RecyclerView.Adapter<TraineeViewHolder> {


    ArrayList<Trainee> list;
    Context mContext;
    FirebaseAuth mAuth;
    DatabaseReference myRefAdd, myRef;
    FirebaseDatabase databaseAdd, database;


    public RecyclerAdapterYourTrainee(ArrayList<Trainee> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        databaseAdd = FirebaseDatabase.getInstance();
        myRefAdd = databaseAdd.getReference("GymTrainer");

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("GymTrainer");


    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.your_trainee_cardview, parent, false);
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
                Toast toast = Toast.makeText(mContext, "" + myList.getKey(), Toast.LENGTH_SHORT);
                View view1 = toast.getView();
                view1.setMinimumWidth(50);
                view1.setBackgroundResource(R.drawable.toast_background);
                toast.show();


            }
        });

        holder.addTraineeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DietActivity.class);
                intent.putExtra("TraineeId", myList.getKey());
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true)
                        .setMessage("Do you want to Delete " + myList.getName() +
                                "from your trainee list ?")
                        .setTitle("Confirmation")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot ds : dataSnapshot.child(mAuth.getUid()).child("Trainees")
                                                        .getChildren()) {

                                                    Log.d(TAG, "onDataChange: " + myList.getKey() + ds.getValue().toString());


                                                    if (ds.getValue().equals(myList.getKey())) {
                                                        ds.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(mContext, "Deleted" +
                                                                        myList.getKey(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                })
                .setNegativeButton("No" , null);
                AlertDialog dialog = builder.create();
                dialog.show();


                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
