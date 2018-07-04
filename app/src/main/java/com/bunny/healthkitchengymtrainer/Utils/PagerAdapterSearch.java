package com.bunny.healthkitchengymtrainer.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bunny.healthkitchengymtrainer.DietActivity.MondayDietFragment;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.SearchActivity.FoodSearchFragment;
import com.bunny.healthkitchengymtrainer.SearchActivity.TraineeSearchFragment;

import java.util.ArrayList;

public class PagerAdapterSearch extends FragmentStatePagerAdapter {

    int noOfTabs;
    ArrayList<Foods> foodsArrayList;
    ArrayList<String> traineeArrayList;
    private static final String TAG = "PagerAdapterSearch";

    public PagerAdapterSearch(FragmentManager fm, int noOfTabs, ArrayList<Foods> foodsArrayList, ArrayList<String> traineeArrayList) {
        super(fm);
        this.noOfTabs = noOfTabs;
        this.foodsArrayList = foodsArrayList;
        this.traineeArrayList = traineeArrayList;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle =new Bundle();
        bundle.putParcelableArrayList("FoodSearch" , foodsArrayList);
        Log.d(TAG, "getItem: " + foodsArrayList.toString());

        switch (position){
            case 0:
                FoodSearchFragment foodSearchFragment =new FoodSearchFragment();
                foodSearchFragment.setArguments(bundle);
                return foodSearchFragment;
            case 1:
                TraineeSearchFragment traineeSearchFragment = new TraineeSearchFragment();
                return traineeSearchFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }


}
