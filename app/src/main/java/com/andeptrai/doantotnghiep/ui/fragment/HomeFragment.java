package com.andeptrai.doantotnghiep.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.RestaurantAdapter;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.andeptrai.doantotnghiep.URL.urlGetNotificationByListIdRes;

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
    ArrayList<InfoRestaurant> infoRestaurants = new ArrayList<>();

    RecyclerView recyclerView;
    ImageView icon_go_home, icon_go_list_restaurant, icon_go_user;

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

        recyclerView = view.findViewById(R.id.list_restaurant);
        restaurantAdapter = new RestaurantAdapter(infoRestaurantArrayList, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(restaurantAdapter);
        restaurantAdapter.notifyDataSetChanged();

        return view;
    }


    private void getNotify() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlGetNotificationByListIdRes
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Get notify fail") && !response.trim().equals("error get data!")){

                }
                else{
                    Toast.makeText(getContext(), "Get notify fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Get notify res error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("listCareRestaurant", InfoUserCurr.list_care_res);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}