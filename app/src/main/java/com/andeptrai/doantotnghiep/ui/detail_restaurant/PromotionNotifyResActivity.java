package com.andeptrai.doantotnghiep.ui.detail_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.NotifyAdapter;
import com.andeptrai.doantotnghiep.data.model.Notify;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.andeptrai.doantotnghiep.URL.urlGetAllNotifyByIdRes;

public class PromotionNotifyResActivity extends AppCompatActivity {

//    String idResCurr;
    RecyclerView rvNotify;
    NotifyAdapter notifyAdapter;
    private ArrayList<Notify> notifyArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_notify_res);

        Intent intent = getIntent();
        String idResCurr = intent.getStringExtra("idResCurr");
        getNotifyThisCurr(idResCurr);

        rvNotify = findViewById(R.id.rvNotify);
        notifyAdapter = new NotifyAdapter(this, notifyArrayList);
        rvNotify.setLayoutManager(new LinearLayoutManager(this));
        rvNotify.setAdapter(notifyAdapter);
        notifyAdapter.notifyDataSetChanged();
    }

    private void getNotifyThisCurr(final String idResCurr) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlGetAllNotifyByIdRes
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Get notify fail")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            notifyArrayList.add(new Notify(jsonObject.getString("Id_notification"),
                                    jsonObject.getString("Id_restaurant"),
                                    jsonObject.getString("Title_notify"),
                                    jsonObject.getString("Content_notification"),
                                    jsonObject.getString("Time_create_notification")));
                        }
                        notifyAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {

                        Toast.makeText(PromotionNotifyResActivity.this, "Get notify error exception! -- " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(PromotionNotifyResActivity.this, "Get notify fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PromotionNotifyResActivity.this, "Get all notify by id res error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idRestaurant", idResCurr);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}