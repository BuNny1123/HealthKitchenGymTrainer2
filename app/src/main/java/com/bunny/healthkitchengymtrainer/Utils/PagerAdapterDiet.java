package com.bunny.healthkitchengymtrainer.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.bunny.healthkitchengymtrainer.DietActivity.FridayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.MondayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.SaturdayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.SundayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.ThursdayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.TuesdayDietFragment;
import com.bunny.healthkitchengymtrainer.DietActivity.WednesdayFragment;

public class PagerAdapterDiet extends FragmentPagerAdapter {

    int noOfTabs;

    public PagerAdapterDiet(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MondayDietFragment mondayDietFragment = new MondayDietFragment();
                return mondayDietFragment;
            case 1:
                TuesdayDietFragment tuesdayDietFragment = new TuesdayDietFragment();
                return tuesdayDietFragment;
            case 2:
                WednesdayFragment wednesdayFragment = new WednesdayFragment();
                return wednesdayFragment;
            case 3:
                ThursdayDietFragment thursdayDietFragment = new ThursdayDietFragment();
                return thursdayDietFragment;
            case 4:
                FridayDietFragment fridayDietFragment = new FridayDietFragment();
                return fridayDietFragment;
            case 5:
                SaturdayDietFragment saturdayDietFragment = new SaturdayDietFragment();
                return saturdayDietFragment;
            case 6:
                SundayDietFragment sundayDietFragment = new SundayDietFragment();
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
