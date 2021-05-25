package com.andeptrai.doantotnghiep.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.DeliveryFoodAdapter;
import com.andeptrai.doantotnghiep.data.model.DeliveryFood;
import com.andeptrai.doantotnghiep.data.model.Food;
import com.andeptrai.doantotnghiep.interf.DeliveryFoodInterf;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;

import java.util.ArrayList;

public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener, DeliveryFoodInterf {

    InfoRestaurant currentInfoRestaurant;

    LinearLayout viewBackCmt, viewInfoResDelivery, viewFooterDeliver, viewDeliveryAction;
    RelativeLayout viewContentDelivery;
    ImageView icPageBackDelivery;
    TextView nameRestaurantDelivery, addressResDelivery, txtNumberDelivery, txtTotalPrice;
    RecyclerView rvFoodDelivery;

    DeliveryFoodAdapter deliveryFoodAdapter;
    ArrayList<DeliveryFood> deliveryFoodArrayList = new ArrayList<>();
    ArrayList<DeliveryFood> deliveryFoodArrayListCart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        currentInfoRestaurant = (InfoRestaurant) getIntent().getSerializableExtra("currentInfoRestaurant");
        ArrayList<Food> foodArrayList = (ArrayList<Food>) getIntent().getSerializableExtra("foodArrayList");

        icPageBackDelivery = findViewById(R.id.icPageBackDelivery);
        nameRestaurantDelivery = findViewById(R.id.nameRestaurantDelivery);
        nameRestaurantDelivery.setText(currentInfoRestaurant.getName_restaurant());
        addressResDelivery = findViewById(R.id.addressResDelivery);
        addressResDelivery.setText(currentInfoRestaurant.getAddress_restaurant());
        rvFoodDelivery = findViewById(R.id.rvFoodDelivery);
        txtNumberDelivery = findViewById(R.id.txtNumberDelivery);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        viewDeliveryAction = findViewById(R.id.viewDeliveryAction);

        for(int i = 0; i < foodArrayList.size();i++){
            DeliveryFood deliveryFood = new DeliveryFood(foodArrayList.get(i).getId_restaurant()
            , foodArrayList.get(i).getId_food(), foodArrayList.get(i).getName_food()
            , foodArrayList.get(i).getStatus(), foodArrayList.get(i).getPrice()
            , foodArrayList.get(i).getPromotion(), 0);
            deliveryFood.setNumberDelivery(0);
            deliveryFoodArrayList.add(deliveryFood);
        }

        deliveryFoodAdapter = new DeliveryFoodAdapter(deliveryFoodArrayList, this, this);
        rvFoodDelivery.setAdapter(deliveryFoodAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvFoodDelivery.setLayoutManager(linearLayoutManager);
        updateCart();

        viewDeliveryAction.setOnClickListener(this);
        icPageBackDelivery.setOnClickListener(this);

    }

    private void updateCart() {
        deliveryFoodArrayListCart = new ArrayList<>();
        int totalMoney = 0;
        int numberFoodInCart = 0;
        for (int i = 0; i < deliveryFoodArrayList.size(); i++){
            if (deliveryFoodArrayList.get(i).getNumberDelivery() > 0){
                deliveryFoodArrayListCart.add(deliveryFoodArrayList.get(i));
                numberFoodInCart += deliveryFoodArrayList.get(i).getNumberDelivery();
                totalMoney += deliveryFoodArrayList.get(i).getNumberDelivery() * (deliveryFoodArrayList.get(i).getPrice());
            }
        }

        txtNumberDelivery.setText(numberFoodInCart+"");
        txtTotalPrice.setText(totalMoney+"");

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.icPageBackDelivery){
            finish();
        }
        if (view.getId() == R.id.viewDeliveryAction){
            openPayDelivery();
        }
    }

    private void openPayDelivery() {
        Intent intent = new Intent(DeliveryActivity.this, PayDeliveryActivity.class);
        intent.putExtra("deliveryFoodArrayListCart", deliveryFoodArrayListCart);
        intent.putExtra("currentInfoRestaurant", currentInfoRestaurant);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void updateCartClickListener() {
        updateCart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001){
            finish();
        }
    }
}