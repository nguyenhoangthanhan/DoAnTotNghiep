package com.andeptrai.doantotnghiep.ui.detail_restaurant;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.ReplyCmtAdapter;
import com.andeptrai.doantotnghiep.data.model.Comment;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.data.model.ReplyCmt;
import com.andeptrai.doantotnghiep.interf.EditInterf;
import com.andeptrai.doantotnghiep.library_code.RandomString;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.andeptrai.doantotnghiep.URL.urlDeleteCmtById;
import static com.andeptrai.doantotnghiep.URL.urlGetAllReplyCmtByIdCmt;
import static com.andeptrai.doantotnghiep.URL.urlInsertNewReplyCmt;
import static com.andeptrai.doantotnghiep.URL.urlUpdateCmtContentById;
import static com.andeptrai.doantotnghiep.URL.urlUpdateLikeAndUnlike;
import static com.andeptrai.doantotnghiep.URL.urlUpdateReplyContentById;
import static com.andeptrai.doantotnghiep.URL.urlUpdateReplyNumber;

public class ReplyCmtActivity extends AppCompatActivity implements EditInterf {

    TextView cmter_name, txtCmtContent, txtPostLike, txtPostCmt, txtLikeCmtNumber, btnOkNewReplyContent, btnCancelNewReplyContent;
    RatingBar cmter_ratingBar;
    CircleImageView img_avt_cmt, imgMeAvatarReply;
    ImageView ic_post_like, ic_post_cmt, ic_post_share, ic_edit_dlt_me, icPageBackReply, img_send_reply_cmt;
    EditText edtMeReplyCmt;
    LinearLayout viewOkCancelEditReplyContent;

    RecyclerView recycleViewReply;
    ArrayList<ReplyCmt> replyCmtArrayList = new ArrayList<>();;
    ReplyCmtAdapter replyCmtAdapter;

    Comment mCmt;
    int positionChangeReplyContent =  -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_cmt);

        icPageBackReply = findViewById(R.id.icPageBackReply);


        cmter_name = findViewById(R.id.cmter_name);
        txtCmtContent = findViewById(R.id.txtCmtContent);
        txtPostLike = findViewById(R.id.txtPostLike);
        txtPostCmt = findViewById(R.id.txtPostCmt);
        txtLikeCmtNumber = findViewById(R.id.txtLikeCmtNumber);
        cmter_ratingBar = findViewById(R.id.cmter_ratingBar);
        img_avt_cmt = findViewById(R.id.img_avt_cmt);
        ic_post_like = findViewById(R.id.ic_post_like);
        ic_post_cmt = findViewById(R.id.ic_post_cmt);
        ic_post_share = findViewById(R.id.ic_post_share);
        ic_edit_dlt_me = findViewById(R.id.ic_edit_dlt_me);
        imgMeAvatarReply = findViewById(R.id.imgMeAvatarReply);
        ic_edit_dlt_me = findViewById(R.id.ic_edit_dlt_me);
        edtMeReplyCmt = findViewById(R.id.edtMeReplyCmt);

        // Edit reply content
        viewOkCancelEditReplyContent = findViewById(R.id.viewOkCancelEditReplyContent);
        btnOkNewReplyContent = findViewById(R.id.btnOkNewReplyContent);
        btnCancelNewReplyContent = findViewById(R.id.btnCancelNewReplyContent);
        btnCancelNewReplyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_send_reply_cmt.setVisibility(View.VISIBLE);
                viewOkCancelEditReplyContent.setVisibility(View.GONE);
                edtMeReplyCmt.setText("");
            }
        });

        btnOkNewReplyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyCmtArrayList.get(positionChangeReplyContent).setContent(edtMeReplyCmt.getText().toString());
                replyCmtAdapter.notifyItemChanged(positionChangeReplyContent);
                updateReplyContentInDB(replyCmtArrayList.get(positionChangeReplyContent));
                img_send_reply_cmt.setVisibility(View.VISIBLE);
                viewOkCancelEditReplyContent.setVisibility(View.GONE);
                edtMeReplyCmt.setText("");
            }
        });
        //end Edit reply content

        //Delete reply content

        //End delete reply content

        //create new reply
        img_send_reply_cmt = findViewById(R.id.img_send_reply_cmt);
        img_send_reply_cmt.setOnClickListener(createNewReplyListener);
        //end create new reply

        recycleViewReply = findViewById(R.id.recycleViewReply);
        replyCmtAdapter = new ReplyCmtAdapter(replyCmtArrayList, this, this);
        recycleViewReply.setAdapter(replyCmtAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleViewReply.setLayoutManager(linearLayoutManager);

        setInfoCurrCmt();

        icPageBackReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("editCmt", mCmt);
                setResult(1001, intent);
                finish();
            }
        });

        getAllReplyCmt();
    }

    private View.OnClickListener createNewReplyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createNewReplyComment();
        }
    };

    private void createNewReplyComment() {
        ReplyCmt replyCmt = new ReplyCmt();

        String contentReply = edtMeReplyCmt.getText().toString();

        Random rand = new Random();
        String idReplyCmt = new RandomString(254, rand).nextString();
        replyCmt.setId_reply_cmt(idReplyCmt);
        replyCmt.setId_cmt(mCmt.getIdComt());
        replyCmt.setIdCmter(InfoUserCurr.currentId);
        replyCmt.setNameCmter(InfoUserCurr.currentName);
        replyCmt.setContent(contentReply);
        replyCmt.setId_list_id_like_replycmt("");

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        final String strDate = formatter.format(date);
        replyCmt.setTime_create_reply(strDate);

        insertNewReplyCmtToDB(replyCmt, mCmt);
    }

    private void insertNewReplyCmtToDB(final ReplyCmt replyCmt, final Comment mCmtNew) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlInsertNewReplyCmt
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Insert new reply cmt success")){
                    Toast.makeText(ReplyCmtActivity.this, "Create new reply cmt success!", Toast.LENGTH_SHORT).show();
                    replyCmtArrayList.add(replyCmt);
                    replyCmtAdapter.notifyItemChanged(replyCmtArrayList.size());
                    edtMeReplyCmt.setText("");
                    updateReplyNumber(mCmtNew);
                }
                else if(response.trim().equals("Insert new reply cmt success change reply number fail")){
                    Toast.makeText(ReplyCmtActivity.this, "Insert new reply cmt success change reply number fail!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ReplyCmtActivity.this, "Create new reply cmt fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReplyCmtActivity.this, "Create new reply cmt error ---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idReplyCmt", replyCmt.getId_reply_cmt());
                params.put("idCmt", replyCmt.getId_cmt());
                params.put("idUserReply", replyCmt.getIdCmter()+"");
                params.put("content", replyCmt.getContent());
                params.put("listIdLikeReply", replyCmt.getId_list_id_like_replycmt());
                params.put("timeCreateReply", replyCmt.getTime_create_reply());
                params.put("newCmtNumber", (ReplyCmtActivity.this.mCmt.getCmtNumber() + 1) + "");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateReplyNumber(Comment mCmtNew) {
        mCmtNew.setCmtNumber(mCmtNew.getCmtNumber() + 1);
        txtLikeCmtNumber
                .setText(mCmtNew.getLikeNumber() + " Thích - " + mCmtNew.getCmtNumber() + " Trả lời - " + mCmtNew.getShareNumber() + " Chia sẻ");
    }

    private void getAllReplyCmt() {
        final String idCmt = mCmt.getIdComt();

        RequestQueue requestQueue = Volley.newRequestQueue(ReplyCmtActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetAllReplyCmtByIdCmt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.trim().equals("Get reply comment fail")){

                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    replyCmtArrayList.add(new ReplyCmt(jsonObject.getString("Id_reply_cmt"),
                                            jsonObject.getString("Id_cmt"),
                                            jsonObject.getInt("Id_user_reply"),
                                            jsonObject.getString("Name_user_reply"),
                                            jsonObject.getString("Content"),
                                            jsonObject.getString("List_id_like_reply"),
                                            jsonObject.getString("Time_create_reply")));
                                }
                                replyCmtAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(ReplyCmtActivity.this, "Like fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReplyCmtActivity.this, "Error like!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idCmt", idCmt);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setInfoCurrCmt() {
        Intent intent = getIntent();
        final Comment comment = (Comment) intent.getSerializableExtra("info_cmt");
        mCmt = comment;

        cmter_name.setText(comment.getNameCmter());
        txtCmtContent.setText(comment.getContent());

        likeChanged(comment);
        //end check like it

        if (comment.getPointReview() != -1){
            cmter_ratingBar.setRating((float) comment.getPointReview());
        }

        if (comment.getIdUser() != -1 && comment.getIdUser() == InfoUserCurr.currentId){
            ic_edit_dlt_me.setVisibility(View.VISIBLE);
        }

        ic_post_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeAndUnlike(comment);
            }
        });
        txtPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeAndUnlike(comment);
            }
        });

        ic_edit_dlt_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(ReplyCmtActivity.this, ic_edit_dlt_me);
                popup.inflate(R.menu.menu_cmt);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit_cmt:
                                Toast.makeText(ReplyCmtActivity.this, "edit", Toast.LENGTH_SHORT).show();
                                DialogUpdateContentCmt(comment);
                                return true;
                            case R.id.menu_delete_cmt:
                                Toast.makeText(ReplyCmtActivity.this, "delete", Toast.LENGTH_SHORT).show();
                                deleteCmt(comment);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();

            }
        });

    }


    private void deleteCmt(final Comment comment) {
        final String idCmt = comment.getIdComt();

        RequestQueue requestQueue = Volley.newRequestQueue(ReplyCmtActivity.this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlDeleteCmtById
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Delete cmt success")){
                    Toast.makeText(ReplyCmtActivity.this, "Delete cmt success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("deleteCmt", mCmt);
                    setResult(1002, intent);
                    finish();
                }
                else if(response.trim().equals("Delete cmt success, delete all reply of this cmt fail")){
                    Toast.makeText(ReplyCmtActivity.this, "Delete cmt success, delete all reply of this cmt fail!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ReplyCmtActivity.this, "Delete cmt fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReplyCmtActivity.this, "Update cmt content error!---"+error.toString(), Toast.LENGTH_LONG).show();
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



    private void DialogUpdateContentCmt(final Comment comment) {
        final Dialog dialogUpdateContentCmt = new Dialog(ReplyCmtActivity.this);
        dialogUpdateContentCmt.setContentView(R.layout.dialog_update_content_cmt);

        final EditText edtContentCmtEdit = dialogUpdateContentCmt.findViewById(R.id.edtContentCmtEdit);
        edtContentCmtEdit.setText(comment.getContent());
        Button btnAcceptChangeContentcmt = dialogUpdateContentCmt.findViewById(R.id.btnAcceptChangeContentcmt);
        Button btnCancelChangeContentCmt = dialogUpdateContentCmt.findViewById(R.id.btnCancelChangeContentCmt);

        btnCancelChangeContentCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdateContentCmt.dismiss();
            }
        });

        btnAcceptChangeContentcmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNewContentCmtInDB(comment, edtContentCmtEdit.getText().toString());
                dialogUpdateContentCmt.dismiss();
            }
        });

        dialogUpdateContentCmt.show();
    }


    private void updateNewContentCmtInDB(final Comment comment, final String s) {

        RequestQueue requestQueue = Volley.newRequestQueue(ReplyCmtActivity.this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlUpdateCmtContentById
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Update cmt content success")){
                    comment.setContent(s);
                    txtCmtContent.setText(comment.getContent());
                    Toast.makeText(ReplyCmtActivity.this, "Update cmt content success!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ReplyCmtActivity.this, "Update cmt content fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReplyCmtActivity.this, "Update cmt content error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idCmt", comment.getIdComt());
                params.put("newCmtContent", s);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void likeChanged(Comment comment){

        String listIdLike = comment.getListLike();
        int likeNumber = 0;
        for (int j = 0; j < listIdLike.length(); j++){
            if (listIdLike.charAt(j) >= '0' && listIdLike.charAt(j) <= '9'){
                likeNumber++;
                int k;
                for (k = j + 1; k < listIdLike.length(); k++){
                    if (listIdLike.charAt(k) >= '0' &&  listIdLike.charAt(k) <= '9') {
                    }
                    else break;
                }
                j = k - 1;
            }
        }
        comment.setLikeNumber(likeNumber);

        txtLikeCmtNumber
                .setText(comment.getLikeNumber() + " Thích - " + comment.getCmtNumber() + " Trả lời - " + comment.getShareNumber() + " Chia sẻ");

        //check like it
        int checkId = 0;
        int check = 0;
        for (int j = 0; j < listIdLike.length(); j++){
            if (listIdLike.charAt(j) >= '0' && listIdLike.charAt(j) <= '9'){
                checkId = checkId*10 + Integer.parseInt(String.valueOf(listIdLike.charAt(j)));
            }
            else{
                if (checkId == InfoUserCurr.currentId){
                    ic_post_like.setImageResource(R.drawable.ic_heart2);
                    txtPostLike.setTextColor(Color.RED);
                    check = 1;
                    break;
                }
                checkId = 0;
            }
            if (checkId == InfoUserCurr.currentId && j == listIdLike.length() - 1){
                ic_post_like.setImageResource(R.drawable.ic_heart2);
                txtPostLike.setTextColor(Color.RED);
                check = 1;
                break;
            }
        }

        if (check == 0){
            ic_post_like.setImageResource(R.drawable.ic_heart);
            txtPostLike.setTextColor(Color.rgb(51, 51, 51));
        }
    }

    private void setLikeAndUnlike(Comment comment) {
        String listIdLike = comment.getListLike();
        int checkId = 0;
        int check = 0;
        for (int j = 0; j < listIdLike.length(); j++){
            if (listIdLike.charAt(j) >= '0' && listIdLike.charAt(j) <= '9'){
                checkId = checkId*10 + Integer.parseInt(String.valueOf(listIdLike.charAt(j)));
            }
            else{
                if (checkId == InfoUserCurr.currentId){
                    check = 1;
                    ic_post_like.setImageResource(R.drawable.ic_heart);
                    txtPostLike.setTextColor(Color.rgb(51, 51, 51));
                    listIdLike = listIdLike.replace("" + InfoUserCurr.currentId + ",", "");
                    comment.setListLike(listIdLike);
                    updateInDB(comment);
                    break;
                }
                checkId = 0;
            }
            if (checkId == InfoUserCurr.currentId && j == listIdLike.length() - 1){
                check = 1;
                ic_post_like.setImageResource(R.drawable.ic_heart);
                txtPostLike.setTextColor(Color.rgb(51, 51, 51));
                listIdLike = listIdLike.replace("," + InfoUserCurr.currentId, "");
                comment.setListLike(listIdLike);
                updateInDB(comment);
                break;
            }
        }

        if (check == 0){
            ic_post_like.setImageResource(R.drawable.ic_heart2);
            txtPostLike.setTextColor(Color.RED);
            listIdLike += "," + InfoUserCurr.currentId;
            comment.setListLike(listIdLike);
            updateInDB(comment);
        }

    }

    private void updateInDB(final Comment comment) {

        RequestQueue requestQueue = Volley.newRequestQueue(ReplyCmtActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateLikeAndUnlike,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Like success")){
                            Toast.makeText(ReplyCmtActivity.this, "Like success!",
                                    Toast.LENGTH_SHORT).show();
                            likeChanged(comment);
                        }
                        else{
                            Toast.makeText(ReplyCmtActivity.this, "Like fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReplyCmtActivity.this, "Error like!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idCmt", comment.getIdComt());
                params.put("listIdLikeNew", comment.getListLike());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("editCmt", mCmt);
        setResult(1001, intent);
        finish();
        super.onBackPressed();
    }


    //Edit reply content
    @Override
    public void editReplyCmtClickListener(ReplyCmt replyCmt, int position) {
        edtMeReplyCmt.setText(replyCmt.getContent());
        img_send_reply_cmt.setVisibility(View.GONE);
        viewOkCancelEditReplyContent.setVisibility(View.VISIBLE);
        positionChangeReplyContent = position;
    }

    @Override
    public void deleteReplyCmtClickListener() {
        updateNewReplyNumber();
    }

    private void updateNewReplyNumber() {
        mCmt.setCmtNumber(replyCmtArrayList.size());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateReplyNumber,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Update reply number success")){
                            txtLikeCmtNumber
                                    .setText(mCmt.getLikeNumber() + " Thích - " + mCmt.getCmtNumber() + " Trả lời - " + mCmt.getShareNumber() + " Chia sẻ");
//                            Toast.makeText(ReplyCmtActivity.this, "Update reply number success!",
//                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ReplyCmtActivity.this, "Update reply number fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReplyCmtActivity.this, "Error update reply number!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idCmt", mCmt.getIdComt());
                params.put("newReplyNumber", mCmt.getCmtNumber()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateReplyContentInDB(final ReplyCmt replyCmt) {
        RequestQueue requestQueue = Volley.newRequestQueue(ReplyCmtActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateReplyContentById,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Update reply content success")){
                            replyCmtAdapter.notifyItemChanged(positionChangeReplyContent);
                            Toast.makeText(ReplyCmtActivity.this, "Update reply content success!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ReplyCmtActivity.this, "Update reply content fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReplyCmtActivity.this, "Error update reply content!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idReplyCmt", replyCmt.getId_reply_cmt());
                params.put("contentReplyCmt", replyCmt.getContent());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    //end Edit reply content

}