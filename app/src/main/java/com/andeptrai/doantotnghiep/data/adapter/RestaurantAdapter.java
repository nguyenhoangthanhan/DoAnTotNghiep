package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.ui.detail_restaurant.RestaurantDetailActivity;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter {

    private List listRestaurant;

    private Context context;

    public RestaurantAdapter(List listRestaurant, Context context) {
        this.listRestaurant = listRestaurant;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View restaurantView = layoutInflater.inflate(R.layout.layout_item_restaurant, parent, false);

        ViewHolder viewHolder = new ViewHolder(restaurantView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final InfoRestaurant infoRestaurant = (InfoRestaurant) listRestaurant.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name_restaurant.setText(infoRestaurant.getName_restaurant());
        viewHolder.review_point.setText(infoRestaurant.getReview_point()+"");
        viewHolder.address_restaurant.setText(infoRestaurant.getAddress_restaurant());
        viewHolder.introduce.setText(infoRestaurant.getShort_description());
        viewHolder.promotion.setText(infoRestaurant.getPromotion());
        viewHolder.item_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RestaurantDetailActivity.class);
                intent.putExtra("Id_restaurant", infoRestaurant.getId_restaurant());;
                intent.putExtra("Name_restaurant", infoRestaurant.getName_restaurant());;
                intent.putExtra("Phone_restaurant", infoRestaurant.getPhone_restaurant());;
                intent.putExtra("Password", infoRestaurant.getPassword());;
                intent.putExtra("Address_restaurant", infoRestaurant.getAddress_restaurant());;
                intent.putExtra("Review_point", infoRestaurant.getReview_point() + "");;
                intent.putExtra("Status_restaurant", infoRestaurant.getStatus_restaurant());;
                intent.putExtra("Short_description", infoRestaurant.getShort_description());;
                intent.putExtra("Promotion", infoRestaurant.getPromotion());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRestaurant.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView review_point, name_restaurant, address_restaurant, introduce, promotion;
        RelativeLayout item_res;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            review_point = itemView.findViewById(R.id.review_point);
            name_restaurant = itemView.findViewById(R.id.name_restaurant);
            address_restaurant = itemView.findViewById(R.id.address_restaurant);
            introduce = itemView.findViewById(R.id.introduce);
            promotion = itemView.findViewById(R.id.promotion);
            item_res = itemView.findViewById(R.id.item_res);

        }
    }
}
