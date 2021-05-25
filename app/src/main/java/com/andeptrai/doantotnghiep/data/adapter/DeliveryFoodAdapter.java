package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.DeliveryFood;
import com.andeptrai.doantotnghiep.interf.DeliveryFoodInterf;

import java.util.ArrayList;

public class DeliveryFoodAdapter extends RecyclerView.Adapter{
    ArrayList<DeliveryFood> foodArrayList;
    Context mContext;
    DeliveryFoodInterf deliveryFoodInterf;

    public DeliveryFoodAdapter(ArrayList<DeliveryFood> foodArrayList, Context mContext, DeliveryFoodInterf deliveryFoodInterf) {
        this.foodArrayList = foodArrayList;
        this.mContext = mContext;
        this.deliveryFoodInterf = deliveryFoodInterf;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View deliveryFoodView = layoutInflater.inflate(R.layout.item_food_delivery,parent, false);
        return new DeliveryFoodViewHolder(deliveryFoodView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final DeliveryFood deliveryFood = foodArrayList.get(position);
        DeliveryFoodViewHolder deliveryFoodViewHolder = (DeliveryFoodViewHolder) holder;
        deliveryFoodViewHolder.txtNameFood.setText(deliveryFood.getName_food());
        deliveryFoodViewHolder.txtPriceFood.setText(deliveryFood.getPrice() + "");
        if (deliveryFood.getStatus() == 1){
            deliveryFoodViewHolder.txtAvailable.setVisibility(View.INVISIBLE);
            deliveryFoodViewHolder.txtUnavailable.setVisibility(View.INVISIBLE);
            deliveryFoodViewHolder.viewAddSub.setVisibility(View.VISIBLE);
        }
        else{
            deliveryFoodViewHolder.txtAvailable.setVisibility(View.INVISIBLE);
            deliveryFoodViewHolder.txtUnavailable.setVisibility(View.VISIBLE);
            deliveryFoodViewHolder.viewAddSub.setVisibility(View.INVISIBLE);
        }

        if (deliveryFood.getNumberDelivery() == 0){
            deliveryFoodViewHolder.imgRemoveToCart.setVisibility(View.INVISIBLE);
            deliveryFoodViewHolder.txtNumberThisFood.setVisibility(View.INVISIBLE);
            deliveryFoodViewHolder.imgAddToCart.setVisibility(View.VISIBLE);
        }
        else{
            deliveryFoodViewHolder.imgRemoveToCart.setVisibility(View.VISIBLE);
            deliveryFoodViewHolder.txtNumberThisFood.setVisibility(View.VISIBLE);
            deliveryFoodViewHolder.txtNumberThisFood.setText(deliveryFood.getNumberDelivery()+"");
            deliveryFoodViewHolder.imgAddToCart.setVisibility(View.VISIBLE);
        }

        deliveryFoodViewHolder.imgRemoveToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryFood.setNumberDelivery(deliveryFood.getNumberDelivery() - 1);
                notifyItemChanged(position);
                deliveryFoodInterf.updateCartClickListener();
            }
        });

        deliveryFoodViewHolder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryFood.setNumberDelivery(deliveryFood.getNumberDelivery() + 1);
                notifyItemChanged(position);
                deliveryFoodInterf.updateCartClickListener();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    class DeliveryFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView imgAvtFood;
        ImageView imgAddToCart;
        ImageView imgRemoveToCart;
        TextView txtNameFood, txtPriceFood, txtAvailable, txtUnavailable, txtNumberThisFood;
        LinearLayout viewAddSub;

        public DeliveryFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvtFood = itemView.findViewById(R.id.imgAvtFood);
            txtNameFood = itemView.findViewById(R.id.txtNameFood);
            txtPriceFood = itemView.findViewById(R.id.txtPriceFood);
            txtAvailable = itemView.findViewById(R.id.txtAvailable);
            txtUnavailable = itemView.findViewById(R.id.txtUnavailable);

            imgAddToCart = itemView.findViewById(R.id.imgAddToCart);
            imgRemoveToCart = itemView.findViewById(R.id.imgRemoveToCart);
            txtNumberThisFood = itemView.findViewById(R.id.txtNumberThisFood);
            viewAddSub = itemView.findViewById(R.id.viewAddSub);
        }
    }
}
