package com.andeptrai.doantotnghiep.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.andeptrai.doantotnghiep.IP;
import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.RestaurantAdapter;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.ui.main.MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RestaurantAdapter restaurantAdapter;
    ArrayList<InfoRestaurant> infoRestaurants = new ArrayList<>();

    RecyclerView recyclerView;
    ImageView icon_go_home, icon_go_list_restaurant, icon_go_user;

    private static String urlGetInfoRestaurant = "http://"+ IP.ip+"/DoAnTotNghiep/androidwebservice/getInfoRestaurant.php";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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


        recyclerView = view.findViewById(R.id.list_restaurant);
        restaurantAdapter = new RestaurantAdapter(infoRestaurants, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(restaurantAdapter);

        getDataRestaurant();

        return view;
    }

    private void getDataRestaurant(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        restaurantAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(arrayRequest);
    }
}