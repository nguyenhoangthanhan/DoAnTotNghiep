package com.andeptrai.doantotnghiep.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.ViewPagerAdapter;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.ui.fragment.AccountFragment;
import com.andeptrai.doantotnghiep.ui.fragment.HomeFragment;
import com.andeptrai.doantotnghiep.ui.fragment.ListRestaurantFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.andeptrai.doantotnghiep.URL.urlGetInfoRestaurant;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem tabsHome, tabsListRestaurant, tabAccount;
    ViewPager viewPager;

    ArrayList<InfoRestaurant> infoRestaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataRestaurant();

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

    private void getDataRestaurant(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetInfoRestaurant, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                infoRestaurants.add(new InfoRestaurant(
                                        object.getString("Id_restaurant"),
                                        object.getString("Name_restaurant"),
                                        object.getInt("Phone_restaurant"),
                                        object.getString("Password"),
                                        object.getString("Address_restaurant"),
                                        object.getDouble("Review_point"),
                                        object.getInt("Status_restaurant"),
                                        object.getString("Short_description"),
                                        object.getString("Promotion")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(arrayRequest);
    }

    private void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        Bundle bundle =new Bundle();
        bundle.putSerializable("infoRestaurantArrayList", infoRestaurants);

        HomeFragment homeFragment = new HomeFragment();
        ListRestaurantFragment listRestaurantFragment = new ListRestaurantFragment();
        AccountFragment accountFragment = new AccountFragment();
        homeFragment.setArguments(bundle);
        viewPagerAdapter.addFrag(homeFragment, "homeFrm");
        viewPagerAdapter.addFrag(listRestaurantFragment, "listResFrm");
        viewPagerAdapter.addFrag(accountFragment, "AccountFrm");

        viewPager.setAdapter(viewPagerAdapter);
    }
}