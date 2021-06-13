package com.andeptrai.doantotnghiep.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.RestaurantAdapter;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.data.model.Restaurant;
import com.andeptrai.doantotnghiep.ui.delivery.ListRestaurantDeliveryActivity;
import com.andeptrai.doantotnghiep.ui.reservation.ListRestaurantReservationActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String DESCRIBABLE_KEY_DATA_RESTAURANT = "key_data_restaurant";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Bundle bundle;
    private ArrayList<InfoRestaurant> infoRestaurantArrayList;

    RestaurantAdapter restaurantAdapter;

    RecyclerView recyclerView;
    ImageView imgDelivery, imgReservation;

    ArrayList<Restaurant> restaurantArrayListDelivery = new ArrayList<>();
    ArrayList<Restaurant> restaurantArrayListReservation = new ArrayList<>();
    ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();

    public HomeFragment() {
    }

//    public static HomeFragment newInstance(ArrayList<InfoRestaurant> replyCmtArrayList){
//        Bundle args = new Bundle();
//        args.putSerializable("replyCmtArrayLst",  replyCmtArrayList);
//        HomeFragment homeFragment = new HomeFragment();
//        homeFragment.setArguments(args);
//        return homeFragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bundle = this.getArguments();
        infoRestaurantArrayList = (ArrayList<InfoRestaurant>) bundle.getSerializable("infoRestaurantArrayList");
        restaurantArrayList = (ArrayList<Restaurant>) bundle.getSerializable("restaurantArrayList");

        for (int i = 0; i < restaurantArrayList.size();i++){
            if (restaurantArrayList.get(i).getStatus_restaurant() == 1 ||
                    restaurantArrayList.get(i).getStatus_restaurant() == 3){
                restaurantArrayListReservation.add(restaurantArrayList.get(i));
            }
            if (restaurantArrayList.get(i).getStatus_restaurant() == 2 ||
                    restaurantArrayList.get(i).getStatus_restaurant() == 3){
                restaurantArrayListDelivery.add(restaurantArrayList.get(i));
            }
        }
        imgDelivery = view.findViewById(R.id.imgDelivery);
        imgReservation = view.findViewById(R.id.imgReservation);
        recyclerView = view.findViewById(R.id.list_restaurant);
        restaurantAdapter = new RestaurantAdapter(infoRestaurantArrayList, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(restaurantAdapter);
        restaurantAdapter.notifyDataSetChanged();
        imgDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListRestaurantDeliveryActivity.class);
                intent.putExtra("restaurantArrayListDelivery", restaurantArrayListDelivery);
                startActivity(intent);
            }
        });
        imgReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListRestaurantReservationActivity.class);
                intent.putExtra("restaurantArrayListReservation", restaurantArrayListReservation);
                startActivity(intent);
            }
        });
        return view;
    }

}