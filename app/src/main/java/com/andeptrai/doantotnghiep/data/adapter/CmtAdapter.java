package com.andeptrai.doantotnghiep.data.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.andeptrai.doantotnghiep.URL.urlUpdateCmtContentById;
import static com.andeptrai.doantotnghiep.URL.urlUpdateLikeAndUnlike;

public class CmtAdapter extends RecyclerView.Adapter {


    private ArrayList<Comment> commentArrayList;
    private Context mContext;
    InterfCmt interfCmt;

    public CmtAdapter(ArrayList<Comment> commentArrayList, Context mContext, InterfCmt interfCmt) {
        this.commentArrayList = commentArrayList;
        this.mContext = mContext;
        this.interfCmt = interfCmt;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cmtView = inflater.inflate(R.layout.iteam_cmt, parent, false);
        CmtViewHolder cmtViewHolder = new CmtViewHolder(cmtView);
        return cmtViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Comment comment = commentArrayList.get(position);

        final CmtViewHolder cmtViewHolder = (CmtViewHolder) holder;
        cmtViewHolder.cmter_name.setText(comment.getNameCmter());
        cmtViewHolder.txtCmtContent.setText(comment.getContent());

        String listIdLike = comment.getListLike();
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

        cmtViewHolder.txtLikeCmtNumber
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
                    cmtViewHolder.ic_post_like.setImageResource(R.drawable.ic_heart2);
                    cmtViewHolder.txtPostLike.setTextColor(Color.RED);
                    check = 1;
                    break;
                }
                checkId = 0;
            }
            if (checkId == InfoUserCurr.currentId && j == listIdLike.length() - 1){
                cmtViewHolder.ic_post_like.setImageResource(R.drawable.ic_heart2);
                cmtViewHolder.txtPostLike.setTextColor(Color.RED);
                check = 1;
                break;
            }
        }

        if (check == 0){
            cmtViewHolder.ic_post_like.setImageResource(R.drawable.ic_heart);
            cmtViewHolder.txtPostLike.setTextColor(Color.rgb(51, 51, 51));
        }
        //end check like it


        if (comment.getPointReview() != -1){
            cmtViewHolder.cmter_ratingBar.setRating((float) comment.getPointReview());
        }

        if (comment.getIdUser() != -1 && comment.getIdUser() == InfoUserCurr.currentId){
            cmtViewHolder.ic_edit_dlt_me.setVisibility(View.VISIBLE);
        }

        cmtViewHolder.ic_post_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeAndUnlike(comment, cmtViewHolder, position);
            }
        });
        cmtViewHolder.txtPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeAndUnlike(comment, cmtViewHolder, position);
            }
        });

        cmtViewHolder.ic_edit_dlt_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(mContext, cmtViewHolder.ic_edit_dlt_me);
                popup.inflate(R.menu.menu_cmt);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit_cmt:
                                Toast.makeText(mContext, "edit", Toast.LENGTH_SHORT).show();
                                DialogUpdateContentCmt(comment, position);
                                return true;
                            case R.id.menu_delete_cmt:
                                Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();
                                interfCmt.deleteCmtClickListener(comment, position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();

            }
        });

        cmtViewHolder.ic_post_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfCmt.openReplyCmtClickListener(comment, position);
            }
        });
        cmtViewHolder.txtPostCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfCmt.openReplyCmtClickListener(comment, position);
            }
        });
    }

    private void DialogUpdateContentCmt(final Comment comment, final int position) {
        final Dialog dialogUpdateContentCmt = new Dialog(mContext);
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
                updateNewContentCmtInDB(comment, edtContentCmtEdit.getText().toString(), position);
                dialogUpdateContentCmt.dismiss();
            }
        });

        dialogUpdateContentCmt.show();
    }

    private void updateNewContentCmtInDB(final Comment comment, final String s, final int position) {

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlUpdateCmtContentById
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Update cmt content success")){
                    commentArrayList.get(position).setContent(s);
                    notifyItemChanged(position);
                    Toast.makeText(mContext, "Update cmt content success!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext, "Update cmt content fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Update cmt content error!---"+error.toString(), Toast.LENGTH_LONG).show();
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

    private void setLikeAndUnlike(Comment comment, CmtViewHolder cmtViewHolder, int position) {
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
                        cmtViewHolder.ic_post_like.setImageResource(R.drawable.ic_heart);
                        cmtViewHolder.txtPostLike.setTextColor(Color.rgb(51, 51, 51));
                        listIdLike = listIdLike.replace("" + InfoUserCurr.currentId + ",", "");
                        comment.setListLike(listIdLike);
                        updateInDB(comment);
                        break;
                    }
                    checkId = 0;
                }
                if (checkId == InfoUserCurr.currentId && j == listIdLike.length() - 1){
                    check = 1;
                    cmtViewHolder.ic_post_like.setImageResource(R.drawable.ic_heart);
                    cmtViewHolder.txtPostLike.setTextColor(Color.rgb(51, 51, 51));
                    listIdLike = listIdLike.replace("," + InfoUserCurr.currentId, "");
                    comment.setListLike(listIdLike);
                    updateInDB(comment);
                    break;
                }
            }

            if (check == 0){
                cmtViewHolder.ic_post_like.setImageResource(R.drawable.ic_heart2);
                cmtViewHolder.txtPostLike.setTextColor(Color.RED);
                listIdLike += "," + InfoUserCurr.currentId;
                comment.setListLike(listIdLike);
                updateInDB(comment);
            }
            notifyItemChanged(position);

    }

    private void updateInDB(final Comment comment) {

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateLikeAndUnlike,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Like success")){
                            Toast.makeText(mContext, "Like success!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(mContext, "Like fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Error like!"+error.toString(), Toast.LENGTH_LONG).show();
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
    public int getItemCount() {
        return commentArrayList.size();
    }

    class CmtViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout item_cmt_relative;

        TextView cmter_name, txtCmtContent, txtPostLike, txtPostCmt, txtLikeCmtNumber;
        RatingBar cmter_ratingBar;
        CircleImageView img_avt_cmt;
        ImageView ic_post_like, ic_post_cmt, ic_post_share, ic_edit_dlt_me;

        public CmtViewHolder(@NonNull View itemView) {
            super(itemView);

            item_cmt_relative = itemView.findViewById(R.id.item_cmt_relative);

            cmter_name = itemView.findViewById(R.id.cmter_name);
            txtCmtContent = itemView.findViewById(R.id.txtCmtContent);
            txtPostLike = itemView.findViewById(R.id.txtPostLike);
            txtPostCmt = itemView.findViewById(R.id.txtPostCmt);
            txtLikeCmtNumber = itemView.findViewById(R.id.txtLikeCmtNumber);
            cmter_ratingBar = itemView.findViewById(R.id.cmter_ratingBar);
            img_avt_cmt = itemView.findViewById(R.id.img_avt_cmt);
            ic_post_like = itemView.findViewById(R.id.ic_post_like);
            ic_post_cmt = itemView.findViewById(R.id.ic_post_cmt);
            ic_post_share = itemView.findViewById(R.id.ic_post_share);
            ic_edit_dlt_me = itemView.findViewById(R.id.ic_edit_dlt_me);


        }
    }

}
