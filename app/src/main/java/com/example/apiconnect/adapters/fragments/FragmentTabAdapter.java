package com.example.apiconnect.adapters.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.apiconnect.fragments.ActivityCameraFragment;
import com.example.apiconnect.fragments.ActivityOneFragment;
import com.example.apiconnect.fragments.ActivityThreeFragment;
import com.example.apiconnect.fragments.ActivityTwoFragment;
import com.example.apiconnect.fragments.TestAdapter;

public class FragmentTabAdapter extends FragmentPagerAdapter{

    public FragmentTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            //return new TestAdapter();
            return new ActivityCameraFragment();
        }
        else if (i == 1) {
            //return new TestAdapter();
            return new ActivityOneFragment();

        }
        else if(i == 2){
            //return new TestAdapter();
            return new ActivityTwoFragment();
        }
        else {
            //return new TestAdapter();
            return new ActivityThreeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "";
            case 1: return "";
            case 2: return "";
            case 3: return "";
            default: return null;
        }
    }
}
