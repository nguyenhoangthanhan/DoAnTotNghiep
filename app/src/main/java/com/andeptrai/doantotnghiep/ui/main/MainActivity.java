package com.andeptrai.doantotnghiep.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem tabsHome, tabsListRestaurant, tabAccount;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        tabsHome = findViewById(R.id.tabsHome);
        tabsListRestaurant = findViewById(R.id.tabsListRestaurant);
        tabAccount = findViewById(R.id.tabAccount);

        viewPager = findViewById(R.id.viewPager);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);;
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_restaurant);;
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_user);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_convert_color);;
                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_restaurant);;
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_user);
                }
                else if (tab.getPosition() == 1){
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);;
                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_restaurant_convert_color);;
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_user);
                }
                else{
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);;
                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_restaurant);;
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_user_convert_color);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
    }
}