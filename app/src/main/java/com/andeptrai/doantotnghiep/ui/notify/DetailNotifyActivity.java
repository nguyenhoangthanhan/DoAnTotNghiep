package com.andeptrai.doantotnghiep.ui.notify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.Notify;

public class DetailNotifyActivity extends AppCompatActivity {

    TextView txtTittleDetailNotify, txtContentDetailNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notify);

        txtTittleDetailNotify = findViewById(R.id.txtTittleDetailNotify);
        txtContentDetailNotify = findViewById(R.id.txtContentDetailNotify);
        Intent intent = getIntent();
        Notify notify = (Notify) intent.getSerializableExtra("notifyInfo");
        txtTittleDetailNotify.setText(notify.getTitle_notify());
        txtContentDetailNotify.setText(notify.getContent_notification());
    }
}