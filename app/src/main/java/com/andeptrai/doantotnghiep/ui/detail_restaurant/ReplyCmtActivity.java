package com.andeptrai.doantotnghiep.ui.detail_restaurant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.ReplyCmtAdapter;
import com.andeptrai.doantotnghiep.data.model.ReplyCmt;

import java.util.ArrayList;

public class ReplyCmtActivity extends AppCompatActivity {

    RecyclerView recycleViewReply;
    ArrayList<ReplyCmt> replyCmtArrayList;
    ReplyCmtAdapter replyCmtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_cmt);

        recycleViewReply = findViewById(R.id.recycleViewReply);
        replyCmtArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i ++){
            replyCmtArrayList.add(new ReplyCmt("cmt_1_res1_11", 3, "Rất ngon", "Thanh Trang"));
        }
        replyCmtArrayList.get(3).setIdCmter(1);
        replyCmtAdapter = new ReplyCmtAdapter(replyCmtArrayList, this);
        recycleViewReply.setAdapter(replyCmtAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleViewReply.setLayoutManager(linearLayoutManager);

    }


}