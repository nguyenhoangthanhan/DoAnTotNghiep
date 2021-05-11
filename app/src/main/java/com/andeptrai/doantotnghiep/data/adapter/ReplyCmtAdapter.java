package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.data.model.ReplyCmt;
import com.andeptrai.doantotnghiep.interf.EditInterf;
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

import static com.andeptrai.doantotnghiep.URL.urlDeleteReplyCmtById;
import static com.andeptrai.doantotnghiep.URL.urlUpdateLikeAndUnlikeReply;

public class ReplyCmtAdapter extends RecyclerView.Adapter {


    ArrayList<ReplyCmt> replyCmtArrayList;
    Context mContext;
    EditInterf editInterf;

    public ReplyCmtAdapter(ArrayList<ReplyCmt> replyCmtArrayList, Context mContext, EditInterf editInterf) {
        this.replyCmtArrayList = replyCmtArrayList;
        this.mContext = mContext;
        this.editInterf = editInterf;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ReplyCmt replyCmt = replyCmtArrayList.get(position);
        final ReplyCmtViewHolder replyCmtViewHolder = (ReplyCmtViewHolder) holder;
        replyCmtViewHolder.txtReplyCmt.setText(replyCmt.getContent());
        replyCmtViewHolder.txtReplyName.setText(replyCmt.getNameCmter());


        String editWord = new String("Edit");
        SpannableString spannableEdit = new SpannableString(editWord);
        spannableEdit.setSpan(new UnderlineSpan(), 0, editWord.length(), 0);
        replyCmtViewHolder.txtEditReplyMe.setText(spannableEdit);
        String deleteWord = new String("Delete");
        SpannableString spannableDelete = new SpannableString(deleteWord);
        spannableDelete.setSpan(new UnderlineSpan(), 0, deleteWord.length(), 0);
        replyCmtViewHolder.txtDeleteReplyMe.setText(spannableDelete);

        if (replyCmt.getIdCmter() == InfoUserCurr.currentId){
            replyCmtViewHolder.txtEditReplyMe.setVisibility(View.VISIBLE);
            replyCmtViewHolder.txtDeleteReplyMe.setVisibility(View.VISIBLE);
        }

        String listIdLike = replyCmt.getId_list_id_like_replycmt();
        if (listIdLike.equalsIgnoreCase("")) {
            replyCmtViewHolder.txtReply_Like_Number.setText(0+"");
        }
        else{
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
            replyCmtViewHolder.txtReply_Like_Number.setText(likeNumber+"");

            //check like it
            int checkId = 0;
            int check = 0;
            for (int j = 0; j < listIdLike.length(); j++){
                if (listIdLike.charAt(j) >= '0' && listIdLike.charAt(j) <= '9'){
                    checkId = checkId*10 + Integer.parseInt(String.valueOf(listIdLike.charAt(j)));
                }
                else{
                    if (checkId == InfoUserCurr.currentId){
                        String likeWordUnLike = new String("Unlike");
                        SpannableString spannableUnLike = new SpannableString(likeWordUnLike);
                        spannableUnLike.setSpan(new UnderlineSpan(), 0, likeWordUnLike.length(), 0);
                        replyCmtViewHolder.txtLikeReply.setText(spannableUnLike);
                        check = 1;
                        break;
                    }
                    checkId = 0;
                }
                if (checkId == InfoUserCurr.currentId && j == listIdLike.length() - 1){
                    String likeWordUnLike = new String("Unlike");
                    SpannableString spannableUnLike = new SpannableString(likeWordUnLike);
                    spannableUnLike.setSpan(new UnderlineSpan(), 0, likeWordUnLike.length(), 0);
                    replyCmtViewHolder.txtLikeReply.setText(spannableUnLike);
                    check = 1;
                    break;
                }
            }

            if (check == 0){
                String likeWord = new String("Like");
                SpannableString spannableLike = new SpannableString(likeWord);
                spannableLike.setSpan(new UnderlineSpan(), 0, likeWord.length(), 0);
                replyCmtViewHolder.txtLikeReply.setText(spannableLike);
            }
            //end check like it
        }

        replyCmtViewHolder.txtLikeReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeAndUnlikeReply(replyCmt, replyCmtViewHolder, position);
            }
        });

        replyCmtViewHolder.txtEditReplyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInterf.editReplyCmtClickListener(replyCmt, position);
            }
        });

        replyCmtViewHolder.txtDeleteReplyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReplyCmt(position);
            }
        });

    }

    private void deleteReplyCmt(int position) {
        deleteReplyCmtInDB(replyCmtArrayList.get(position), position);
    }

    private void deleteReplyCmtInDB(final ReplyCmt replyCmt, final int position) {

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteReplyCmtById,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Delete reply success")){
                            replyCmtArrayList.remove(position);
                            notifyItemChanged(position);
                            editInterf.deleteReplyCmtClickListener();
                            Toast.makeText(mContext, "Delete reply success!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(mContext, "Delete reply fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Error delete reply!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idReplyCmt", replyCmt.getId_reply_cmt());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setLikeAndUnlikeReply(ReplyCmt replyCmt, ReplyCmtViewHolder replyCmtViewHolder, int position) {
        String listIdLike = replyCmt.getId_list_id_like_replycmt();
        int checkId = 0;
        int check = 0;
        for (int j = 0; j < listIdLike.length(); j++){
            if (listIdLike.charAt(j) >= '0' && listIdLike.charAt(j) <= '9'){
                checkId = checkId*10 + Integer.parseInt(String.valueOf(listIdLike.charAt(j)));
            }
            else{
                if (checkId == InfoUserCurr.currentId){
                    check = 1;
                    String likeWordUnLike = new String("Unlike");
                    SpannableString spannableUnLike = new SpannableString(likeWordUnLike);
                    spannableUnLike.setSpan(new UnderlineSpan(), 0, likeWordUnLike.length(), 0);
                    replyCmtViewHolder.txtLikeReply.setText(spannableUnLike);
                    listIdLike = listIdLike.replace("" + InfoUserCurr.currentId + ",", "");
                    replyCmt.setId_list_id_like_replycmt(listIdLike);
                    updateInDB(replyCmt);
                    break;
                }
                checkId = 0;
            }
            if (checkId == InfoUserCurr.currentId && j == listIdLike.length() - 1){
                check = 1;
                String likeWordUnLike = new String("Unlike");
                SpannableString spannableUnLike = new SpannableString(likeWordUnLike);
                spannableUnLike.setSpan(new UnderlineSpan(), 0, likeWordUnLike.length(), 0);
                replyCmtViewHolder.txtLikeReply.setText(spannableUnLike);
                listIdLike = listIdLike.replace("," + InfoUserCurr.currentId, "");
                replyCmt.setId_list_id_like_replycmt(listIdLike);
                updateInDB(replyCmt);
                break;
            }
        }

        if (check == 0){
            String likeWord = new String("Like");
            SpannableString spannableLike = new SpannableString(likeWord);
            spannableLike.setSpan(new UnderlineSpan(), 0, likeWord.length(), 0);
            replyCmtViewHolder.txtLikeReply.setText(spannableLike);
            listIdLike += "," + InfoUserCurr.currentId;
            replyCmt.setId_list_id_like_replycmt(listIdLike);
            updateInDB(replyCmt);
        }
        notifyItemChanged(position);

    }


    private void updateInDB(final ReplyCmt replyCmt) {

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateLikeAndUnlikeReply,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Like reply success")){
                            Toast.makeText(mContext, "Like reply success!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(mContext, "Like reply fail - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Error like reply!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idReplyCmt", replyCmt.getId_reply_cmt());
                params.put("listIdLikeReplyNew", replyCmt.getId_list_id_like_replycmt());
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
            txtLikeReply = itemView.findViewById(R.id.txtLikeReplyCmt);
            txtReply_Like_Number = itemView.findViewById(R.id.txtReply_Like_Number);
            txtEditReplyMe = itemView.findViewById(R.id.txtEditReplyMe);
            txtDeleteReplyMe = itemView.findViewById(R.id.txtDeleteReplyMe);
        }
    }
}
