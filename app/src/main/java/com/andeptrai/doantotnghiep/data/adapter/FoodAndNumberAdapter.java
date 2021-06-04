package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.FoodAndNumber;

import java.util.ArrayList;

public class FoodAndNumberAdapter extends RecyclerView.Adapter {

    private ArrayList<FoodAndNumber> foodAndNumberArrayList;
    private Context mContext;

    public FoodAndNumberAdapter(ArrayList<FoodAndNumber> foodAndNumberArrayList, Context mContext) {
        this.foodAndNumberArrayList = foodAndNumberArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_food_and_number, parent, false);
        return new FoodAndNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodAndNumber foodAndNumber = foodAndNumberArrayList.get(position);
        FoodAndNumberViewHolder foodAndNumberViewHolder = (FoodAndNumberViewHolder) holder;

        foodAndNumberViewHolder.txtNameFood.setText(foodAndNumber.getNameFood());
        foodAndNumberViewHolder.foodNumber.setText(foodAndNumber.getNumber() + "");
    }

    @Override
    public int getItemCount() {
        return foodAndNumberArrayList.size();
    }

    class FoodAndNumberViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameFood, foodNumber;

        public FoodAndNumberViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameFood = itemView.findViewById(R.id.txtNameFood);
            foodNumber = itemView.findViewById(R.id.foodNumber);
        }
    }
}
