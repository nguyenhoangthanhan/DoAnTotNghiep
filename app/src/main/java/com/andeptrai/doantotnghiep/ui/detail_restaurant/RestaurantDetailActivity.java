package com.andeptrai.doantotnghiep.ui.detail_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.CmtAdapter;
import com.andeptrai.doantotnghiep.data.model.Comment;

import java.util.ArrayList;

public class RestaurantDetailActivity extends AppCompatActivity {

    ImageView ic_add_cmt;
    TextView txtAddCmt;

    RecyclerView recycleViewCmt;
    CmtAdapter cmtAdapter;
    ArrayList<Comment> commentArrayList;

    private static final int RESULT_ADD_CMT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        ic_add_cmt = findViewById(R.id.ic_add_cmt);
        ic_add_cmt.setOnClickListener(addNewCmtListener);
        txtAddCmt = findViewById(R.id.txtAddCmt);
        txtAddCmt.setOnClickListener(addNewCmtListener);

        recycleViewCmt = findViewById(R.id.recycleViewCmt);
        commentArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            commentArrayList.add(new Comment("Thanh Thien", "Rat ngon"
                    , 20, 10, 5, true, 4.2));
        }
        for (int i = 0; i < 10; i++){
            commentArrayList.add(new Comment("Thanh Thien", "Rat ngon"
                    , 10, 12, 1, false, 3.2));
        }
        commentArrayList.get(0).setIdUser(8);
        commentArrayList.get(1).setIdUser(1);

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
}