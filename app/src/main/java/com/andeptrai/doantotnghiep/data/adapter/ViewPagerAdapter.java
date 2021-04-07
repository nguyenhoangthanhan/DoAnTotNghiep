package com.andeptrai.doantotnghiep.data.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.andeptrai.doantotnghiep.ui.fragment.AccountFragment;
import com.andeptrai.doantotnghiep.ui.fragment.HomeFragment;
import com.andeptrai.doantotnghiep.ui.fragment.ListRestaurantFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ListRestaurantFragment();
            case 2:
                return new AccountFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
