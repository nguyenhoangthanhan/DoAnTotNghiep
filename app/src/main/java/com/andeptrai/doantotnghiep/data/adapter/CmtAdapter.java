package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.Comment;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CmtAdapter extends RecyclerView.Adapter {

    private ArrayList<Comment> commentArrayList;
    private Context mContext;

    public CmtAdapter(ArrayList<Comment> commentArrayList, Context mContext) {
        this.commentArrayList = commentArrayList;
        this.mContext = mContext;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final Comment comment = commentArrayList.get(position);

        final CmtViewHolder cmtViewHolder = (CmtViewHolder) holder;
        cmtViewHolder.cmter_name.setText(comment.getNameCmter());
        cmtViewHolder.txtCmtContent.setText(comment.getContent());
        cmtViewHolder.txtLikeCmtNumber
                .setText(comment.getLikeNumber() + " Thích - " + comment.getCmtNumber() + " Trả lời - " + comment.getShareNumber() + " Chia sẻ");

        //check like it
        String listIdLike = comment.getListLike();
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
                setLikeAndUnlike(comment, cmtViewHolder);
            }
        });
        cmtViewHolder.txtPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeAndUnlike(comment, cmtViewHolder);
            }
        });

        cmtViewHolder.ic_edit_dlt_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, cmtViewHolder.ic_edit_dlt_me);
                popup.inflate(R.menu.menu_cmt);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit_cmt:
                                Toast.makeText(mContext, "edit", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu_delete_cmt:
                                Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();
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

    private void setLikeAndUnlike(Comment comment, CmtViewHolder cmtViewHolder) {
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
                        listIdLike = listIdLike.replace(InfoUserCurr.currentId + " ,", "");
                        listIdLike = listIdLike.replace(", " + InfoUserCurr.currentId, "");
                        comment.setListLike(listIdLike);
                        updateInDB(comment);
                        break;
                    }
                }
            }

            if (check == 0){
                cmtViewHolder.ic_post_like.setImageResource(R.drawable.ic_heart2);
                cmtViewHolder.txtPostLike.setTextColor(Color.RED);
                listIdLike += ", " + InfoUserCurr.currentId;
                comment.setListLike(listIdLike);
                updateInDB(comment);
            }
    }

    private void updateInDB(Comment comment) {
        
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    class CmtViewHolder extends RecyclerView.ViewHolder{

        TextView cmter_name, txtCmtContent, txtPostLike, txtPostCmt, txtLikeCmtNumber;
        RatingBar cmter_ratingBar;
        CircleImageView img_avt_cmt;
        ImageView ic_post_like, ic_post_cmt, ic_post_share, ic_edit_dlt_me;

        public CmtViewHolder(@NonNull View itemView) {
            super(itemView);

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
