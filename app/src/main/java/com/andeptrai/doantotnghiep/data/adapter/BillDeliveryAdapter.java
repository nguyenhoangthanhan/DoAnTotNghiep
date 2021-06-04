package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.BillDeliveryOrdered;
import com.andeptrai.doantotnghiep.interf.BillDeliveryInterf;
import com.andeptrai.doantotnghiep.ui.delivery_ordered.DetailBillDeliveryOrderedActivity;

import java.util.ArrayList;


public class BillDeliveryAdapter extends RecyclerView.Adapter {

    private ArrayList<BillDeliveryOrdered> billDeliveryOrderedArrayList;
    Context mContext;
    BillDeliveryInterf billDeliveryInterf;

    public BillDeliveryAdapter(ArrayList<BillDeliveryOrdered> billDeliveryOrderedArrayList
            , Context mContext, BillDeliveryInterf billDeliveryInterf) {
        this.billDeliveryOrderedArrayList = billDeliveryOrderedArrayList;
        this.mContext = mContext;
        this.billDeliveryInterf = billDeliveryInterf;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_bill_delivery, parent, false);
        return new BillDeliveryOrderedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final BillDeliveryOrdered billDeliveryOrdered = billDeliveryOrderedArrayList.get(position);
        BillDeliveryOrderedViewHolder billDeliveryOrderedViewHolder = (BillDeliveryOrderedViewHolder) holder;

        billDeliveryOrderedViewHolder.txtNameRestaurant.setText(billDeliveryOrdered.getNameRestaurant());
        billDeliveryOrderedViewHolder.txtAddressDelivery.setText(billDeliveryOrdered.getAddressDelivery());
        billDeliveryOrderedViewHolder.txtTotalMoney.setText(billDeliveryOrdered.getTotalMoneyBill()+"đ");

        String listFoodAndNumber = billDeliveryOrdered.getDetailBill();
        int numberFood = 0;
        for (int i = 0; i < listFoodAndNumber.length(); i++){
            if (listFoodAndNumber.charAt(i) >= '0' && listFoodAndNumber.charAt(i) <= '9' && listFoodAndNumber.charAt(i - 1) == ' '){
                numberFood++;
            }
        }
        billDeliveryOrderedViewHolder.txtFoodNumber.setText(numberFood + " món ăn");

        if (billDeliveryOrdered.getStatusConfirm() == 0){
            billDeliveryOrderedViewHolder.txtYetAccepted.setVisibility(View.VISIBLE);
            billDeliveryOrderedViewHolder.txtAccepted.setVisibility(View.GONE);
            billDeliveryOrderedViewHolder.txtRejected.setVisibility(View.GONE);
        }
        else if (billDeliveryOrdered.getStatusConfirm() == 1){
            billDeliveryOrderedViewHolder.txtEditBillDelivery.setVisibility(View.INVISIBLE);
            billDeliveryOrderedViewHolder.txtYetAccepted.setVisibility(View.GONE);
            billDeliveryOrderedViewHolder.txtAccepted.setVisibility(View.VISIBLE);
            billDeliveryOrderedViewHolder.txtRejected.setVisibility(View.GONE);
        }
        else if (billDeliveryOrdered.getStatusConfirm() == 2){
            billDeliveryOrderedViewHolder.txtEditBillDelivery.setVisibility(View.INVISIBLE);
            billDeliveryOrderedViewHolder.txtYetAccepted.setVisibility(View.GONE);
            billDeliveryOrderedViewHolder.txtAccepted.setVisibility(View.GONE);
            billDeliveryOrderedViewHolder.txtRejected.setVisibility(View.VISIBLE);
        }

        billDeliveryOrderedViewHolder.imgDetailBillDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailBillDeliveryOrderedActivity.class);
                intent.putExtra("currentBillDeliveryOrdered", billDeliveryOrdered);
                mContext.startActivity(intent);
            }
        });

        billDeliveryOrderedViewHolder.txtEditBillDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billDeliveryInterf.updateBillClickListener(billDeliveryOrdered, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return billDeliveryOrderedArrayList.size();
    }

    class BillDeliveryOrderedViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameRestaurant, txtFoodNumber, txtTotalMoney, txtAddressDelivery, txtEditBillDelivery
                , txtAccepted, txtRejected, txtYetAccepted;
        ImageView imgDetailBillDelivery;

        public BillDeliveryOrderedViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameRestaurant = itemView.findViewById(R.id.txtNameRestaurant);
            txtFoodNumber = itemView.findViewById(R.id.txtFoodNumber);
            txtTotalMoney = itemView.findViewById(R.id.txtTotalMoney);
            txtAddressDelivery = itemView.findViewById(R.id.txtAddressDelivery);
            txtEditBillDelivery = itemView.findViewById(R.id.txtEditBillDelivery);
            imgDetailBillDelivery = itemView.findViewById(R.id.imgDetailBillDelivery);
            txtAccepted = itemView.findViewById(R.id.txtAccepted);
            txtRejected = itemView.findViewById(R.id.txtRejected);
            txtYetAccepted = itemView.findViewById(R.id.txtYetAccepted);
        }
    }
}
