package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.Kind;
import com.andeptrai.doantotnghiep.interf.ResetListRestaurantInterf;

import java.util.ArrayList;

public class KindAdapter extends RecyclerView.Adapter {

    ArrayList<Kind> kindArrayList;
    Context mContext;
    ResetListRestaurantInterf resetListRestaurantInterf;

    private static int lastCheck = 0;

    public KindAdapter(ArrayList<Kind> kindArrayList, Context mContext, ResetListRestaurantInterf resetListRestaurantInterf) {
        this.kindArrayList = kindArrayList;
        this.mContext = mContext;
        this.resetListRestaurantInterf = resetListRestaurantInterf;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_kind, parent, false);
        return new KindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Kind kind = kindArrayList.get(position);
        KindViewHolder kindViewHolder = (KindViewHolder) holder;

        kindViewHolder.cbKind.setText(kind.getNameKind());

        if (kind.isCheck()){
            kindViewHolder.cbKind.setChecked(true);
        }
        else{
            kindViewHolder.cbKind.setChecked(false);
        }



        kindViewHolder.cbKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kindArrayList.get(lastCheck).setCheck(false);
                notifyItemChanged(lastCheck);
                lastCheck = position;
                kind.setCheck(true);
                resetListRestaurantInterf.resetListRestaurant(kind.getNameKind());
            }
        });
    }

    @Override
    public int getItemCount() {
        return kindArrayList.size();
    }

    class KindViewHolder extends RecyclerView.ViewHolder{

        RadioButton cbKind;

        public KindViewHolder(@NonNull View itemView) {
            super(itemView);

            cbKind = itemView.findViewById(R.id.cbKind);
        }
    }
}
