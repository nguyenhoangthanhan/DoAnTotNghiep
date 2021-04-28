package com.andeptrai.doantotnghiep.ui.detail_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.Comment;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.library_code.RandomString;

import java.util.Random;

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
            String title = edtTitleCmt.getText().toString();
            String content = edtContentCmt.getText().toString();
            String contentFull = title + "\n\n" + content;

            Random rand = new Random();
            String idCmt = new RandomString(30, rand).nextString();
            Comment newCmt = new Comment(idCmt, InfoUserCurr.currentId, InfoUserCurr.currentName, "res1"
                    , contentFull, 0, 0, 0, null, false, rate);
            Intent intent = new Intent();
            intent.putExtra("newCmt", newCmt);
            setResult(1000, intent);
            finish();
        }
    };
}