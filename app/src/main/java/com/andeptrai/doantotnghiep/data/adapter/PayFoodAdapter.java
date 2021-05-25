package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.DeliveryFood;

import java.util.ArrayList;

public class PayFoodAdapter extends RecyclerView.Adapter {

    ArrayList<DeliveryFood> deliveryFoodArrayList;
    Context mContext;

    public PayFoodAdapter(ArrayList<DeliveryFood> deliveryFoodArrayList, Context mContext) {
        this.deliveryFoodArrayList = deliveryFoodArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View payFoodView = layoutInflater.inflate(R.layout.item_food_pay,parent, false);
        return new PayFoodViewHolder(payFoodView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeliveryFood deliveryFood = deliveryFoodArrayList.get(position);
        PayFoodViewHolder payFoodViewHolder = (PayFoodViewHolder) holder;
        payFoodViewHolder.txtNameFoodPay.setText(deliveryFood.getName_food());
        payFoodViewHolder.txtPriceFoodPay.setText(deliveryFood.getPrice()+"");
        payFoodViewHolder.txtNumberThisFoodPay.setText("x" + deliveryFood.getNumberDelivery());
    }

    @Override
    public int getItemCount() {
        return deliveryFoodArrayList.size();
    }

    class PayFoodViewHolder extends RecyclerView.ViewHolder{

        TextView txtNumberThisFoodPay, txtNameFoodPay, txtPriceFoodPay;

        public PayFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumberThisFoodPay = itemView.findViewById(R.id.txtNumberThisFoodPay);
            txtNameFoodPay = itemView.findViewById(R.id.txtNameFoodPay);
            txtPriceFoodPay = itemView.findViewById(R.id.txtPriceFoodPay);

        }
    }
}
