package com.andeptrai.doantotnghiep.data.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.interf.SetNumberInterf;

import java.util.ArrayList;

public class AdultsChildrenNumberAdapter extends RecyclerView.Adapter {

    ArrayList<String> stringArrayList;
    Context mContext;
    Dialog childrenNumberDialog;
    SetNumberInterf setNumberInterf;
    String s;

    public AdultsChildrenNumberAdapter(ArrayList<String> stringArrayList, Context mContext
            , Dialog childrenNumberDialog, SetNumberInterf setNumberInterf, String s) {
        this.stringArrayList = stringArrayList;
        this.mContext = mContext;
        this.childrenNumberDialog = childrenNumberDialog;
        this.setNumberInterf = setNumberInterf;
        this.s = s;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View numberView = inflater.inflate(R.layout.item_adults_children_number, parent, false);
        NumberViewHolder numberViewHolder = new NumberViewHolder(numberView);
        return numberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String number = stringArrayList.get(position);
        NumberViewHolder numberViewHolder = (NumberViewHolder) holder;
        numberViewHolder.txtNumberCurrent.setText(number);

        numberViewHolder.txtNumberCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.equals("Children")){
                    setNumberInterf.setNumberChildrenClickListener(childrenNumberDialog, number);
                }
                if (s.equals("Adults")){
                    setNumberInterf.setNumberAdultsClickListener(childrenNumberDialog, number);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder{

        TextView txtNumberCurrent;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumberCurrent = itemView.findViewById(R.id.txtNumberCurrent);

        }
    }
}
