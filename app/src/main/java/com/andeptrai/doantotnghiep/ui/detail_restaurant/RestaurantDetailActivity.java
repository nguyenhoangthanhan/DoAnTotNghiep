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

import com.andeptrai.doantotnghiep.CODE;
import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.CmtAdapter;
import com.andeptrai.doantotnghiep.data.model.Comment;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.interf.InterfCmt;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.andeptrai.doantotnghiep.URL.urlDeleteCmtById;
import static com.andeptrai.doantotnghiep.URL.urlGetAllCmtByIdRes;
import static com.andeptrai.doantotnghiep.URL.urlGetCmtReviewPointNumber;
import static com.andeptrai.doantotnghiep.URL.urlUpdateCareAndDontCare;

public class RestaurantDetailActivity extends AppCompatActivity implements InterfCmt, View.OnClickListener {

    ImageView ic_add_cmt, img_heart_care;
    TextView txtAddCmt, txtTittle, txtReviewPoint, txtAddressRes, txtReviewNumber, txtCmtNumber, txtCmtNumber2, txtResCare, txtPromotion;
    MapView mapView;

    RecyclerView recycleViewCmt;
    CmtAdapter cmtAdapter;
    ArrayList<Comment> commentArrayList;

    public static int positionOpenReplyCmt = -1;
    public static int cmtNumber = -1;
    public static int rwNumber = -1;
    public static String idResCurr = null;



    private final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    private Marker markerPerth;
    private Marker markerSydney;
    private Marker markerBrisbane;

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
        txtCmtNumber2 = findViewById(R.id.txtCmtNumber2);
        img_heart_care = findViewById(R.id.img_heart_care_de_ac);
        img_heart_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCareAndNoCare();
            }
        });
        txtResCare = findViewById(R.id.txtResCare);
        txtResCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCareAndNoCare();
            }
        });

        txtPromotion = findViewById(R.id.txtPromotion);
        txtPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantDetailActivity.this, PromotionNotifyResActivity.class);
                intent.putExtra("idResCurr", idResCurr);
                startActivity(intent);
            }
        });

        setInfoResCurr();

//        mapView = findViewById(R.id.mapView);

        //list comment
        recycleViewCmt = findViewById(R.id.recycleViewCmt);
        commentArrayList = new ArrayList<>();
        cmtAdapter = new CmtAdapter(commentArrayList, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleViewCmt.setAdapter(cmtAdapter);
        recycleViewCmt.setLayoutManager(linearLayoutManager);


        //list food

    }

    private void setCareAndNoCare() {
        if (!InfoUserCurr.list_care_res.equals("")){
            String list_care_res_curr_user = InfoUserCurr.list_care_res;
            String id_res_loop = "";
            boolean checkCare = true;
            for (int i = 0; i < list_care_res_curr_user.length(); i++){
                if(list_care_res_curr_user.charAt(i) != ','){
                    id_res_loop += list_care_res_curr_user.charAt(i);
                    if (i == list_care_res_curr_user.length()-1){
                        if (id_res_loop.equals(idResCurr)){
                            img_heart_care.setImageResource(R.drawable.ic_heart_care);
                            InfoUserCurr.list_care_res = InfoUserCurr.list_care_res.replace(","+idResCurr, "");
                            updateCareAndDontCare();
                            checkCare = false;
                            break;
                        }
                    }
                }
                else{
                    if (id_res_loop.equals(idResCurr)){
                        img_heart_care.setImageResource(R.drawable.ic_heart_care);
                        InfoUserCurr.list_care_res = InfoUserCurr.list_care_res.replace(","+idResCurr, "");
                        updateCareAndDontCare();
                        checkCare = false;
                        break;
                    }
                    id_res_loop = "";
                }
            }
            if (checkCare){
                img_heart_care.setImageResource(R.drawable.ic_heart_care_2_1);
                InfoUserCurr.list_care_res += "," + idResCurr;
                updateCareAndDontCare();
            }
        }
        else{
            img_heart_care.setImageResource(R.drawable.ic_heart_care_2_1);
            InfoUserCurr.list_care_res += "," + idResCurr;
            updateCareAndDontCare();
        }
    }

    private void updateCareAndDontCare() {
        RequestQueue requestQueue = Volley.newRequestQueue(RestaurantDetailActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateCareAndDontCare,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Care success")){
                            Toast.makeText(RestaurantDetailActivity.this, "Care success!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RestaurantDetailActivity.this, "Care fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RestaurantDetailActivity.this, "Error care!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", InfoUserCurr.currentId + "");
                params.put("listCareResNew", InfoUserCurr.list_care_res);
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
        startActivityForResult(intent, CODE.RESULT_ADD_CMT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE.RESULT_ADD_CMT){
            Comment c = (Comment) data.getSerializableExtra("newCmt");
            commentArrayList.add(0, c);
            cmtAdapter.notifyItemInserted(0);
            getCmtReviewNumber(idResCurr);
        }
        if (requestCode == CODE.RESULT_OPEN_REPLY_CMT && resultCode == CODE.RESULT_OPEN_REPLY_CMT){
            Comment c = (Comment) data.getSerializableExtra("editCmt");
            commentArrayList.remove(positionOpenReplyCmt);
            commentArrayList.add(positionOpenReplyCmt, c);
            cmtAdapter.notifyItemChanged(positionOpenReplyCmt);
        }

        if (requestCode == CODE.RESULT_OPEN_REPLY_CMT && resultCode == CODE.RESULT_DELETE_CMT){
            commentArrayList.remove(positionOpenReplyCmt);
            cmtAdapter.notifyDataSetChanged();
            getCmtReviewNumber(idResCurr);
        }
    }



    private void setInfoResCurr() {

        Intent intent = getIntent();
        final String Id_restaurant = intent.getStringExtra("Id_restaurant");
        idResCurr = Id_restaurant;
        if (!InfoUserCurr.list_care_res.equals("")){
            String list_care_res_curr_user = InfoUserCurr.list_care_res;
            String id_res_loop = "";
            for (int i = 0; i < list_care_res_curr_user.length(); i++){
                if(list_care_res_curr_user.charAt(i) != ','){
                    id_res_loop += list_care_res_curr_user.charAt(i);
                    if (i == list_care_res_curr_user.length()-1){
                        if (id_res_loop.equals(Id_restaurant)){
                            img_heart_care.setImageResource(R.drawable.ic_heart_care_2_1);
                        }
                    }
                }
                else{
                    if (id_res_loop.equals(Id_restaurant)){
                        img_heart_care.setImageResource(R.drawable.ic_heart_care_2_1);
                        break;
                    }
                    id_res_loop = "";
                }
            }
        }
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
                            comment.setCmtNumber(jsonObject.getInt("Reply_number"));

                            String listIdLike = jsonObject.getString("List_id_like_cmt");
                            comment.setListLike(listIdLike);
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
                                cmtNumber = Integer.parseInt(jsonObject.getString("Cmt_rw_number"));
                                txtCmtNumber.setText(cmtNumber+"");
                                txtCmtNumber2.setText((cmtNumber) + " bình luận");
                                rwNumber = Integer.parseInt(jsonObject.getString("Cmt_rw_number"));
                                txtReviewNumber.setText(rwNumber+"");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                                cmtNumber = Integer.parseInt(jsonObject1.getString("Cmt_rw_number"));
                                txtCmtNumber.setText(cmtNumber+"");
                                txtCmtNumber2.setText((cmtNumber) + " bình luận");
                                JSONObject jsonObject2 = (JSONObject) jsonArray.get(1);
                                rwNumber = Integer.parseInt(jsonObject2.getString("Cmt_rw_number"));
                                txtReviewNumber.setText(rwNumber+"");
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

    @Override
    public void openReplyCmtClickListener(Comment comment, int position) {
        openThisReplyCmt(comment, position);
    }

    @Override
    public void deleteCmtClickListener(Comment comment, int position) {
        deleteCmt(comment, position);
    }


    private void deleteCmt(final Comment comment, final int position) {
        final String idCmt = comment.getIdComt();

        RequestQueue requestQueue = Volley.newRequestQueue(RestaurantDetailActivity.this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlDeleteCmtById
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Delete cmt success")){
                    commentArrayList.remove(comment);
                    cmtAdapter.notifyDataSetChanged();
                    getCmtReviewNumber(idResCurr);
                    Toast.makeText(RestaurantDetailActivity.this, "Delete cmt success!", Toast.LENGTH_SHORT).show();
                }
                else if(response.trim().equals("Delete cmt success, delete all reply of this cmt fail")){
                    Toast.makeText(RestaurantDetailActivity.this, "Delete cmt success, delete all reply of this cmt fail!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RestaurantDetailActivity.this, "Delete cmt fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestaurantDetailActivity.this, "Update cmt content error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idCmt", idCmt);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void openThisReplyCmt(Comment comment, int position) {
        Intent intent = new Intent(this, ReplyCmtActivity.class);
        intent.putExtra("info_cmt", comment);
        startActivityForResult(intent, CODE.RESULT_OPEN_REPLY_CMT);
        positionOpenReplyCmt = position;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtResCare){
            careThisRestaurant();
        }
        if (view.getId() == R.id.img_heart_care_de_ac){
            careThisRestaurant();
        }

    }

    private void careThisRestaurant() {

    }
}