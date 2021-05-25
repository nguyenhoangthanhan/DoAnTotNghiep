package com.andeptrai.doantotnghiep.ui.detail_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.Comment;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.library_code.RandomString;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.andeptrai.doantotnghiep.CODE.CREATE_CMT_NEW;
import static com.andeptrai.doantotnghiep.URL.urlInsertNewCmt;

public class NewCmtActivity extends AppCompatActivity {

    ImageView icPageBack;
    TextView txtPostNewCmt;
    RatingBar ratingBarNewCmt;
    EditText edtTitleCmt, edtContentCmt;


    private float rate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cmt);

        icPageBack = findViewById(R.id.icPageBack);
        icPageBack.setOnClickListener(backPageOnClickListener);
        edtTitleCmt = findViewById(R.id.edtTitleCmt);
        edtContentCmt = findViewById(R.id.edtContentCmt);
        ratingBarNewCmt = findViewById(R.id.ratingBarNewCmt);
        ratingBarNewCmt.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate = v;
            }
        });

        txtPostNewCmt = findViewById(R.id.txtPostNewCmt);
        txtPostNewCmt.setOnClickListener(postCmtOnClickListener);

    }

    private View.OnClickListener backPageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private View.OnClickListener postCmtOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentId = getIntent();
            final String Id_restaurant = intentId.getStringExtra("idRes");

            String title = edtTitleCmt.getText().toString();
            String content = edtContentCmt.getText().toString();
            String contentFull = title + "\n" + content;

            Random rand = new Random();
            String idCmt = new RandomString(CREATE_CMT_NEW, rand).nextString();
            Comment newCmt = new Comment(idCmt, InfoUserCurr.currentId, InfoUserCurr.currentName, "res1"
                    , contentFull, 0, 0, 0, null, "", false, rate);
            newCmt.setIdRestaurant(Id_restaurant);
            insertToDB(newCmt);

        }
    };

    private void insertToDB(final Comment newCmt) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        final String strDate = formatter.format(date);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlInsertNewCmt
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Insert new cmt success")){
                    Intent intent = new Intent();
                    intent.putExtra("newCmt", newCmt);
                    setResult(1000, intent);
                    Toast.makeText(NewCmtActivity.this, "Create new cmt success!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(NewCmtActivity.this, "Create new cmt fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewCmtActivity.this, "Create new cmt error ---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idCmt", newCmt.getIdComt());
                params.put("idUser", InfoUserCurr.currentId + "");
                params.put("idRestaurant", newCmt.getIdRestaurant());
                params.put("content", newCmt.getContent());
                params.put("timeCreateCmt", strDate);
                params.put("pointReview", newCmt.getPointReview() + "");

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}