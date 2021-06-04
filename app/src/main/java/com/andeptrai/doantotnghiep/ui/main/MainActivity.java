package com.andeptrai.doantotnghiep.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.ViewPagerAdapter;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.data.model.Notify;
import com.andeptrai.doantotnghiep.ui.fragment.AccountFragment;
import com.andeptrai.doantotnghiep.ui.fragment.BillDeliveryFragment;
import com.andeptrai.doantotnghiep.ui.fragment.HomeFragment;
import com.andeptrai.doantotnghiep.ui.fragment.NotificationFragment;
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

    Bundle bundleRes =new Bundle();
    Bundle bundleNotify =new Bundle();
    HomeFragment homeFragment = new HomeFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    AccountFragment accountFragment = new AccountFragment();
    BillDeliveryFragment billDeliveryFragment = new BillDeliveryFragment();

    ArrayList<InfoRestaurant> infoRestaurants = new ArrayList<>();
    ArrayList<Notify> notifyArrayList = new ArrayList<>();

    String responseNotify = null;

    private int check_have_notification = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataRestaurant();
//        getNotify();

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
                    if (check_have_notification == 0){
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_bell2);;
                    }
                    else{
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_notification2);;
                    }
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_invoices);
                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_user);
                }
                else if (tab.getPosition() == 1){
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);;
                    if (check_have_notification == 0){
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_bell);;
                    }
                    else{
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_notification);;
                    }
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_invoices);
                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_user);

                    bundleNotify.putSerializable("bundleNotifyArrayList", notifyArrayList);
                    notificationFragment.setArguments(bundleNotify);
                }
                else if(tab.getPosition() == 2){
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);;
                    if (check_have_notification == 0){
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_bell2);;
                    }
                    else{
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_notification2);;
                    }
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_invoices2);
                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_user);
                }
                else{
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);;
                    if (check_have_notification == 0){
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_bell2);;
                    }
                    else{
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_notification2);;
                    }
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_invoices);
                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_user_convert_color);
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
//                        bundleRes.putSerializable("infoRestaurantArrayList", infoRestaurants);
//                        homeFragment.setArguments(bundleRes);
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

        viewPagerAdapter.addFrag(homeFragment, "homeFrm");
        viewPagerAdapter.addFrag(notificationFragment, "listResFrm");
        viewPagerAdapter.addFrag(billDeliveryFragment, "BillDeliveryFrm");
        viewPagerAdapter.addFrag(accountFragment, "AccountFrm");
        bundleRes.putSerializable("infoRestaurantArrayList", infoRestaurants);
        homeFragment.setArguments(bundleRes);
        bundleNotify.putSerializable("bundleNotifyArrayList", notifyArrayList);
        bundleNotify.putSerializable("responseNotify", responseNotify);
        notificationFragment.setArguments(bundleNotify);

        viewPager.setAdapter(viewPagerAdapter);
    }
}