package com.andeptrai.doantotnghiep.ui.detail_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.IP;
import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.CmtAdapter;
import com.andeptrai.doantotnghiep.data.model.Comment;
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

public class RestaurantDetailActivity extends AppCompatActivity {

    ImageView ic_add_cmt;
    TextView txtAddCmt, txtTittle, txtReviewPoint, txtAddressRes, txtReviewNumber, txtCmtNumber;

    RecyclerView recycleViewCmt;
    CmtAdapter cmtAdapter;
    ArrayList<Comment> commentArrayList;


    private static final int RESULT_ADD_CMT = 1000;

    private static String urlGetAllCmtByIdRes = "http://"+ IP.ip+"/DoAnTotNghiep/androidwebservice/getAllCmtByIdRes.php";
    private static String urlGetCmtReviewPointNumber = "http://"+ IP.ip+"/DoAnTotNghiep/androidwebservice/getCmtReviewPointNumber.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        ic_add_cmt = findViewById(R.id.ic_add_cmt);
        ic_add_cmt.setOnClickListener(addNewCmtListener);
        txtAddCmt = findViewById(R.id.txtAddCmt);
        txtAddCmt.setOnClickListener(addNewCmtListener);

        txtTittle = findViewById(R.id.txtTittle);
        txtReviewPoint = findViewById(R.id.txtReviewPoint);
        txtAddressRes = findViewById(R.id.txtAddressRes);

        txtReviewNumber = findViewById(R.id.txtReviewNumber);
        txtCmtNumber = findViewById(R.id.txtCmtNumber);

        setInfoResCurr();

        recycleViewCmt = findViewById(R.id.recycleViewCmt);
        commentArrayList = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            commentArrayList.add(new Comment("Thanh Thien", "Rat ngon"
//                    , 20, 10, 5, true, 4.2));
//        }
//        for (int i = 0; i < 10; i++){
//            commentArrayList.add(new Comment("Thanh Thien", "Rat ngon"
//                    , 10, 12, 1, false, 3.2));
//        }
//        commentArrayList.get(0).setIdUser(8);
//        commentArrayList.get(1).setIdUser(1);

        cmtAdapter = new CmtAdapter(commentArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleViewCmt.setAdapter(cmtAdapter);
        recycleViewCmt.setLayoutManager(linearLayoutManager);
    }
    private View.OnClickListener addNewCmtListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createNewCmt();
        }
    };

    private void createNewCmt() {

        Intent intent = new Intent(RestaurantDetailActivity.this, NewCmtActivity.class);
        Intent intentId = getIntent();
        final String Id_restaurant = intentId.getStringExtra("Id_restaurant");
        intent.putExtra("idRes", Id_restaurant);
        startActivityForResult(intent, RESULT_ADD_CMT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ADD_CMT){
            Comment c = (Comment) data.getSerializableExtra("newCmt");
            commentArrayList.add(0, c);
            cmtAdapter.notifyItemInserted(0);
        }

    }



    private void setInfoResCurr() {
        Intent intent = getIntent();
        final String Id_restaurant = intent.getStringExtra("Id_restaurant");
        getCmtReviewNumber(Id_restaurant);
        final String Name_restaurant = intent.getStringExtra("Name_restaurant");
        txtTittle.setText(Name_restaurant);
        final String Phone_restaurant = intent.getStringExtra("Phone_restaurant");
        final String Password = intent.getStringExtra("Password");
        final String Address_restaurant = intent.getStringExtra("Address_restaurant");
        txtAddressRes.setText(Address_restaurant);
        final String Review_point = intent.getStringExtra("Review_point");
        txtReviewPoint.setText(Review_point);
        final String Status_restaurant = intent.getStringExtra("Status_restaurant");
        final String Short_description = intent.getStringExtra("Short_description");
        final String Promotion = intent.getStringExtra("Promotion");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlGetAllCmtByIdRes
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Get restaurant comment fail")){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            Comment comment = new Comment();
                            comment.setIdComt(jsonObject.getString("Id_cmt"));
                            comment.setIdUser(jsonObject.getInt("Id_user"));
                            comment.setNameCmter(jsonObject.getString("Name_user"));
                            comment.setIdRestaurant(jsonObject.getString("Id_restaurant"));
                            comment.setContent(jsonObject.getString("Content"));

                            String listIdLike = jsonObject.getString("List_id_like_cmt");
                            comment.setListLike(listIdLike);
                            int likeNumber = 0;
                            for (int j = 0; j < listIdLike.length(); j++){
                                if (listIdLike.charAt(j) >= '0' && listIdLike.charAt(j) <= '9'){
                                    likeNumber++;
                                    int k;
                                    for (k = j + 1; k < listIdLike.length(); k++){
                                        if (listIdLike.charAt(k) >= '0' &&  listIdLike.charAt(k) <= '9') {
                                            k++;
                                        }
                                        else break;
                                    }
                                    j = k - 1;
                                }
                            }
                            comment.setLikeNumber(likeNumber);
                            comment.setTime_create_cmt(jsonObject.getString("Time_create_cmt"));
                            comment.setPointReview(jsonObject.getDouble("Point_review"));

                            commentArrayList.add(comment);
                        }

                        cmtAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {

                        Toast.makeText(RestaurantDetailActivity.this, "Loi! -- " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(RestaurantDetailActivity.this, "Get all cmt fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestaurantDetailActivity.this, "Get cmt for this res fail!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idRestaurant", Id_restaurant);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getCmtReviewNumber(final String id_restaurant) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlGetCmtReviewPointNumber
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Get cmt review number fail")) {

                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response);
                        if (jsonArray.length() < 2) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                            try {
                                txtCmtNumber.setText(jsonObject.getString("Cmt_rw_number"));
                                txtReviewNumber.setText(jsonObject.getString("Cmt_rw_number"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                                txtCmtNumber.setText(jsonObject1.getString("Cmt_rw_number"));
                                JSONObject jsonObject2 = (JSONObject) jsonArray.get(1);
                                txtReviewNumber.setText(jsonObject2.getString("Cmt_rw_number"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestaurantDetailActivity.this, "Get cmt review number error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idRestaurant", id_restaurant);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}