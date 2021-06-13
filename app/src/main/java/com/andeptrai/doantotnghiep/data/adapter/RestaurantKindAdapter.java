package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.data.model.Restaurant;
import com.andeptrai.doantotnghiep.ui.detail_restaurant.RestaurantDetailActivity;

import java.util.ArrayList;

public class RestaurantKindAdapter extends RecyclerView.Adapter {

    ArrayList<Restaurant> restaurantArrayList;
    Context mContext;

    public RestaurantKindAdapter(ArrayList<Restaurant> restaurantArrayList, Context mContext) {
        this.restaurantArrayList = restaurantArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_restaurant_kind, parent, false);
        return new RestaurantKindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Restaurant restaurant = restaurantArrayList.get(position);
        RestaurantKindViewHolder restaurantKindViewHolder = (RestaurantKindViewHolder) holder;

        restaurantKindViewHolder.txtNameRestaurant.setText(restaurant.getName_restaurant());
        restaurantKindViewHolder.txtAddress.setText(restaurant.getAddress_restaurant());
        restaurantKindViewHolder.txtPhone.setText(restaurant.getPhone_restaurant());

        restaurantKindViewHolder.item_restaurant_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoRestaurant infoRestaurant = restaurant;
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                intent.putExtra("infoCurrentRestaurant", infoRestaurant);
                intent.putExtra("Id_restaurant", infoRestaurant.getId_restaurant());;
                intent.putExtra("Name_restaurant", infoRestaurant.getName_restaurant());;
                intent.putExtra("Phone_restaurant", infoRestaurant.getPhone_restaurant());;
                intent.putExtra("Password", infoRestaurant.getPassword());;
                intent.putExtra("Address_restaurant", infoRestaurant.getAddress_restaurant());;
                intent.putExtra("Review_point", infoRestaurant.getReview_point() + "");;
                intent.putExtra("Status_restaurant", infoRestaurant.getStatus_restaurant());;
                intent.putExtra("Short_description", infoRestaurant.getShort_description());;
                intent.putExtra("Promotion", infoRestaurant.getPromotion());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    class RestaurantKindViewHolder extends  RecyclerView.ViewHolder{

        TextView txtNameRestaurant, txtAddress, txtPhone;
        LinearLayout item_restaurant_kind;

        public RestaurantKindViewHolder(@NonNull View itemView) {
            super(itemView);

            item_restaurant_kind = itemView.findViewById(R.id.item_restaurant_kind);
            txtNameRestaurant = itemView.findViewById(R.id.txtNameRestaurant);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhone = itemView.findViewById(R.id.txtPhone);
        }
    }
}
