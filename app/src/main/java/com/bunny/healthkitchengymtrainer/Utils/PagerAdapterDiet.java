package com.bunny.healthkitchengymtrainer.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.DietActivity.FridayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.MondayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.SaturdayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.SundayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.ThursdayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.TuesdayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.WednesdayFragment;
import com.bunny.healthkitchengymtrainer.Model.Meal;
import com.bunny.healthkitchengymtrainer.R;

import java.util.ArrayList;

public class PagerAdapterDiet extends FragmentStatePagerAdapter {

    int noOfTabs;
    ArrayList<Meal> mealArrayList;
    private static final String TAG = "PagerAdapterDiet";

    public PagerAdapterDiet(FragmentManager fm, int noOfTabs, ArrayList<Meal> mealArrayList) {
        super(fm);
        this.noOfTabs = noOfTabs;
        this.mealArrayList = mealArrayList;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MealArrayList", mealArrayList);
        //Log.d(TAG, "getItem: " + mealArrayList);

        switch (position) {
            case 0:
                MondayDietFragment mondayDietFragment = new MondayDietFragment();
                mondayDietFragment.setArguments(bundle);
                return mondayDietFragment;
            case 1:
                TuesdayDietFragment tuesdayDietFragment = new TuesdayDietFragment();
                tuesdayDietFragment.setArguments(bundle);
                return tuesdayDietFragment;
            case 2:
                WednesdayFragment wednesdayFragment = new WednesdayFragment();
                wednesdayFragment.setArguments(bundle);
                return wednesdayFragment;
            case 3:
                ThursdayDietFragment thursdayDietFragment = new ThursdayDietFragment();
                thursdayDietFragment.setArguments(bundle);
                return thursdayDietFragment;
            case 4:
                FridayDietFragment fridayDietFragment = new FridayDietFragment();
                fridayDietFragment.setArguments(bundle);
                return fridayDietFragment;
            case 5:
                SaturdayDietFragment saturdayDietFragment = new SaturdayDietFragment();
                saturdayDietFragment.setArguments(bundle);
                return saturdayDietFragment;
            case 6:
                SundayDietFragment sundayDietFragment = new SundayDietFragment();
                sundayDietFragment.setArguments(bundle);
                return sundayDietFragment;

            default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
