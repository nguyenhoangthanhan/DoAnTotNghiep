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
import com.andeptrai.doantotnghiep.interf.SetNumberDialogInterf;

import java.util.ArrayList;

public class AdultsChildrenNumberDialogAdapter extends RecyclerView.Adapter {

    ArrayList<String> stringArrayList;
    Context mContext;
    Dialog childrenNumberDialog;
    SetNumberDialogInterf setNumberDialogInterf;
    String s;
    TextView txtNumber;

    public AdultsChildrenNumberDialogAdapter(ArrayList<String> stringArrayList, Context mContext
            , Dialog childrenNumberDialog, SetNumberDialogInterf setNumberDialogInterf, String s, TextView txtNumber) {
        this.stringArrayList = stringArrayList;
        this.mContext = mContext;
        this.childrenNumberDialog = childrenNumberDialog;
        this.setNumberDialogInterf = setNumberDialogInterf;
        this.s = s;
        this.txtNumber = txtNumber;
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
                    setNumberDialogInterf.setNumberChildrenDialogClickListener(childrenNumberDialog, number, txtNumber);
                }
                if (s.equals("Adults")){
                    setNumberDialogInterf.setNumberAdultsDialogClickListener(childrenNumberDialog, number, txtNumber);
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
