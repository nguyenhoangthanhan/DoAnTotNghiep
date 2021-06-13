package com.andeptrai.doantotnghiep.ui.reservation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.KindAdapter;
import com.andeptrai.doantotnghiep.data.adapter.RestaurantKindAdapter;
import com.andeptrai.doantotnghiep.data.model.Kind;
import com.andeptrai.doantotnghiep.data.model.Restaurant;
import com.andeptrai.doantotnghiep.interf.ResetListRestaurantInterf;
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

import static com.andeptrai.doantotnghiep.URL.urlGetAllKindReservation;

public class ListRestaurantReservationActivity extends AppCompatActivity  implements ResetListRestaurantInterf {

    ImageView icPageBack;
    RecyclerView rvKindReservation, rvListRestaurant;

    ArrayList<Kind> kindArrayList = new ArrayList<>();
    KindAdapter kindAdapter;

    ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
    RestaurantKindAdapter restaurantKindAdapter;

    ArrayList<Restaurant> currentRestaurantArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurant_reservation);

        restaurantArrayList = (ArrayList<Restaurant>) getIntent().getSerializableExtra("restaurantArrayListReservation");

        icPageBack = findViewById(R.id.icPageBack);
        rvKindReservation = findViewById(R.id.rvKindReservation);
        rvListRestaurant = findViewById(R.id.rvListRestaurant);

        kindAdapter = new KindAdapter(kindArrayList, this, this);
        rvKindReservation.setAdapter(kindAdapter);
        rvKindReservation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        getAllKind();

        restaurantKindAdapter = new RestaurantKindAdapter(restaurantArrayList, this);
        rvListRestaurant.setAdapter(restaurantKindAdapter);
        rvListRestaurant.setLayoutManager(new LinearLayoutManager(this));

        icPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getAllKind() {
        RequestQueue requestQueue = Volley.newRequestQueue(ListRestaurantReservationActivity.this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetAllKindReservation, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(ListRestaurantReservationActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                kindArrayList.add(new Kind(
                                        object.getString("Id_kind"),
                                        object.getString("Name_kind"),
                                        object.getInt("Classify_kind"),
                                        false
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        kindAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListRestaurantReservationActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(arrayRequest);
    }

    @Override
    public void resetListRestaurant(String nameKind) {
        currentRestaurantArrayList = new ArrayList<>();
        for (int i = 0; i < restaurantArrayList.size(); i++){
            if (restaurantArrayList.get(i).getListKind().contains(nameKind)){
                currentRestaurantArrayList.add(restaurantArrayList.get(i));
            }
        }
        restaurantKindAdapter = new RestaurantKindAdapter(currentRestaurantArrayList, this);
        rvListRestaurant.setAdapter(restaurantKindAdapter);
        restaurantKindAdapter.notifyDataSetChanged();
    }
}