package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.Food;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter {

    ArrayList<Food> foodArrayList;
    Context mContext;

    public FoodAdapter(ArrayList<Food> foodArrayList, Context mContext) {
        this.foodArrayList = foodArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View foodView = layoutInflater.inflate(R.layout.item_food,parent, false);
        return new FoodViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Food food = foodArrayList.get(position);
        FoodViewHolder foodViewHolder = (FoodViewHolder) holder;
        foodViewHolder.txtNameFood.setText(food.getName_food());
        foodViewHolder.txtPriceFood.setText(food.getPrice() + "");
        if (food.getStatus() == 1){
            foodViewHolder.txtAvailable.setVisibility(View.VISIBLE);
            foodViewHolder.txtUnavailable.setVisibility(View.INVISIBLE);
        }
        else{
            foodViewHolder.txtAvailable.setVisibility(View.INVISIBLE);
            foodViewHolder.txtUnavailable.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{

        ImageView imgAvtFood;
        TextView txtNameFood, txtPriceFood, txtAvailable, txtUnavailable;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvtFood = itemView.findViewById(R.id.imgAvtFood);
            txtNameFood = itemView.findViewById(R.id.txtNameFood);
            txtPriceFood = itemView.findViewById(R.id.txtPriceFood);
            txtAvailable = itemView.findViewById(R.id.txtAvailable);
            txtUnavailable = itemView.findViewById(R.id.txtUnavailable);
        }
    }
}
