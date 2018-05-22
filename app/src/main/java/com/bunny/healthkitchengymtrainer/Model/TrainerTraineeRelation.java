package com.bunny.healthkitchengymtrainer.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TrainerTraineeRelation {
    String trainer, trainee;

    public TrainerTraineeRelation(){

    }

    public TrainerTraineeRelation(String trainer, String trainee) {
        this.trainer = trainer;
        this.trainee = trainee;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainee() {
        return trainee;
    }

    public void setTrainee(String trainee) {
        this.trainee = trainee;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("trainer", trainer);
        result.put("trainee", trainee);
        return result;
    }
}
