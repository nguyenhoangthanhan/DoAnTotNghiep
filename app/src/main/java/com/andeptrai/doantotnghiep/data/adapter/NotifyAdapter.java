package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.Notify;
import com.andeptrai.doantotnghiep.ui.notify.DetailNotifyActivity;

import java.util.ArrayList;

public class NotifyAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<Notify> notifyArrayList;

    public NotifyAdapter(Context mContext, ArrayList<Notify> notifyArrayList) {
        this.mContext = mContext;
        this.notifyArrayList = notifyArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View notifyView = layoutInflater.inflate(R.layout.item_notification,parent, false);
        return new NotifyViewHolder(notifyView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Notify notify = notifyArrayList.get(position);
        NotifyViewHolder notifyViewHolder = (NotifyViewHolder) holder;
        notifyViewHolder.txtTittleNotify.setText(notify.getTitle_notify());
        notifyViewHolder.txtContentNotify.setText(notify.getContent_notification());
        notifyViewHolder.item_notify_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotify(notify);
            }
        });
    }

    private void openNotify(Notify notify) {
        Intent intent = new Intent(mContext, DetailNotifyActivity.class);
        intent.putExtra("notifyInfo", notify);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return notifyArrayList.size();
    }

    private class NotifyViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout item_notify_relative;
        TextView txtTittleNotify, txtContentNotify;
        ImageView img_new_notify;

        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_notify_relative = itemView.findViewById(R.id.item_notify_relative);
            txtTittleNotify = itemView.findViewById(R.id.txtTittleNotify);
            txtContentNotify = itemView.findViewById(R.id.txtContentNotify);
            img_new_notify = itemView.findViewById(R.id.img_new_notify);

        }
    }
}
