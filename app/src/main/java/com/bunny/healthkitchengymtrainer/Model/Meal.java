package com.bunny.healthkitchengymtrainer.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Meal implements Parcelable {

    String day;
    String dateTime;
    HashMap<String , Integer> foods;

    public Meal(){}

    @Override
    public String toString() {
        return "Meal{" +
                "day='" + day + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", foods=" + foods +
                '}';
    }

    public Meal(String day, String dateTime, HashMap<String, Integer> foods) {
        this.day = day;
        this.dateTime = dateTime;
        this.foods = foods;
    }

    protected Meal(Parcel in) {
        day = in.readString();
        dateTime = in.readString();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public String getDay() {

        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public HashMap<String, Integer> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<String, Integer> foods) {
        this.foods = foods;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(day);
        parcel.writeString(dateTime);
    }
}
