package com.andeptrai.doantotnghiep.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.ui.fragment.AccountFragment;
import com.andeptrai.doantotnghiep.ui.fragment.HomeFragment;
import com.andeptrai.doantotnghiep.ui.fragment.ListRestaurantFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<InfoRestaurant> infoRestaurantArrayList;

    private int numOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int numOfTabs, ArrayList<InfoRestaurant> infoRestaurantArrayList) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.infoRestaurantArrayList = infoRestaurantArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment(infoRestaurantArrayList);
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
