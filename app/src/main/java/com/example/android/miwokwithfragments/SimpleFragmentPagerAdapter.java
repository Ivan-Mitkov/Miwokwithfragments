package com.example.android.miwokwithfragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ivan on 22.9.2017 г..
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1) {
            return new ColorsFragment();

        } else if (position == 2) {
            return new FamilyMembersFragment();
        }
        else {
            return new PhrasesActivityFragment();
        }


    }

    @Override
    public int getCount() {
        return 4;
    }
}
