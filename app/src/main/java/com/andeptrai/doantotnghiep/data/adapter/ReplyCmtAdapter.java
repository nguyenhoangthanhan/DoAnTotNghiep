package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.data.model.ReplyCmt;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReplyCmtAdapter extends RecyclerView.Adapter {

    ArrayList<ReplyCmt> replyCmtArrayList;
    Context mContext;

    public ReplyCmtAdapter(ArrayList<ReplyCmt> replyCmtArrayList, Context mContext) {
        this.replyCmtArrayList = replyCmtArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_cmt_reply, parent, false);
        ReplyCmtViewHolder replyCmtViewHolder = new ReplyCmtViewHolder(view);
        return replyCmtViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReplyCmt replyCmt = replyCmtArrayList.get(position);
        ReplyCmtViewHolder replyCmtViewHolder = (ReplyCmtViewHolder) holder;
        replyCmtViewHolder.txtReplyCmt.setText(replyCmt.getContent());
        replyCmtViewHolder.txtReplyName.setText(replyCmt.getNameCmter());

        String likeWord = new String("Thích");
        SpannableString spannableLike = new SpannableString(likeWord);
        spannableLike.setSpan(new UnderlineSpan(), 0, likeWord.length(), 0);
        replyCmtViewHolder.txtLikeReply.setText(spannableLike);
        String editWord = new String("Sửa");
        SpannableString spannableEdit = new SpannableString(editWord);
        spannableEdit.setSpan(new UnderlineSpan(), 0, editWord.length(), 0);
        replyCmtViewHolder.txtEditReplyMe.setText(spannableEdit);
        String deleteWord = new String("Xóa");
        SpannableString spannableDelete = new SpannableString(deleteWord);
        spannableDelete.setSpan(new UnderlineSpan(), 0, deleteWord.length(), 0);
        replyCmtViewHolder.txtDeleteReplyMe.setText(spannableDelete);

        if (replyCmt.getIdCmter() == InfoUserCurr.currentId){
            replyCmtViewHolder.txtEditReplyMe.setVisibility(View.VISIBLE);
            replyCmtViewHolder.txtDeleteReplyMe.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return replyCmtArrayList.size();
    }

    class ReplyCmtViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imgReplyUser;
        TextView txtReplyName, txtReplyCmt, txtLikeReply, txtReply_Like_Number, txtEditReplyMe, txtDeleteReplyMe;

        public ReplyCmtViewHolder(@NonNull View itemView) {
            super(itemView);

            imgReplyUser = itemView.findViewById(R.id.imgReplyUser);
            txtReplyName = itemView.findViewById(R.id.txtReplyName);
            txtReplyCmt = itemView.findViewById(R.id.txtReplyCmt);
            txtLikeReply = itemView.findViewById(R.id.txtLikeReply);
            txtReply_Like_Number = itemView.findViewById(R.id.txtReply_Like_Number);
            txtEditReplyMe = itemView.findViewById(R.id.txtEditReplyMe);
            txtDeleteReplyMe = itemView.findViewById(R.id.txtDeleteReplyMe);
        }
    }
}
