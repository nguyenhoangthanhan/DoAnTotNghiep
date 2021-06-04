package com.andeptrai.doantotnghiep.ui.delivery_ordered;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.FoodAndNumberAdapter;
import com.andeptrai.doantotnghiep.data.model.BillDeliveryOrdered;
import com.andeptrai.doantotnghiep.data.model.FoodAndNumber;

import java.util.ArrayList;

public class DetailBillDeliveryOrderedActivity extends AppCompatActivity {

    ImageView icPageBack;
    TextView txtNameRestaurant, txtTimeCreateBill, txtStatusConfirm, txtBillType
            , txtAddressDelivery, txtTimeDelivery, txtTotalMoney, txtPayment, txtNotes;

    RecyclerView rvFoodAndNumber;
    Button btnBack;

    BillDeliveryOrdered currentBillDeliveryOrdered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill_delivery_ordered);


        currentBillDeliveryOrdered = (BillDeliveryOrdered) getIntent().getSerializableExtra("currentBillDeliveryOrdered");

        icPageBack = findViewById(R.id.icPageBack);
        txtNameRestaurant = findViewById(R.id.txtNameRestaurant);
        txtTimeCreateBill = findViewById(R.id.txtTimeCreateBill);
        txtStatusConfirm = findViewById(R.id.txtStatusConfirm);
        txtBillType = findViewById(R.id.txtBillType);
        txtAddressDelivery = findViewById(R.id.txtAddressDelivery);
        txtTimeDelivery = findViewById(R.id.txtTimeDelivery);
        txtTotalMoney = findViewById(R.id.txtTotalMoney);
        txtPayment = findViewById(R.id.txtPayment);
        txtNotes = findViewById(R.id.txtNotes);
        rvFoodAndNumber = findViewById(R.id.rvFoodAndNumber);
        btnBack = findViewById(R.id.btnBack);

        txtNameRestaurant.setText(currentBillDeliveryOrdered.getNameRestaurant());
        txtTimeCreateBill.setText(currentBillDeliveryOrdered.getTimeCreateBill());
        if (currentBillDeliveryOrdered.getStatusConfirm() == 0){ txtStatusConfirm.setText(R.string.accept_yet); }
        else if(currentBillDeliveryOrdered.getStatusConfirm() == 1){ txtStatusConfirm.setText(R.string.accepted); }
        else if(currentBillDeliveryOrdered.getStatusConfirm() == 2){ txtStatusConfirm.setText(R.string.rejected); }
        txtBillType.setText(R.string.bill_delivery);
        txtAddressDelivery.setText(currentBillDeliveryOrdered.getAddressDelivery());
        txtTimeDelivery.setText(currentBillDeliveryOrdered.getTimeDelivery());
        txtTotalMoney.setText(currentBillDeliveryOrdered.getTotalMoneyBill() + "");
        txtPayment.setText(currentBillDeliveryOrdered.getPayment());
        txtNotes.setText(currentBillDeliveryOrdered.getNotes());


        ArrayList<FoodAndNumber> foodAndNumberArrayList = new ArrayList<>();
        String foodDetail = currentBillDeliveryOrdered.getDetailFood();
        //Log.d("foodDetail", foodDetail);
        String nameFood = "";
        int numberFood = 0;
        for (int i = 0; i < foodDetail.length(); i++){
            if(foodDetail.charAt(i) != ','){
                nameFood += foodDetail.charAt(i);
                //Log.d("nameFood", nameFood);
                if (foodDetail.charAt(i) >= '0' && foodDetail.charAt(i) <= '9'){
                    numberFood = numberFood * 10 + Integer.parseInt(foodDetail.charAt(i) + "");
                    if (foodDetail.charAt(i + 1) == ','){
                        nameFood += ",";
                        //Log.d("nameFoodPhay", nameFood);
                        nameFood = nameFood.replace(numberFood + ",","");
                        //Log.d("nameFoodReplace", nameFood);
                        foodAndNumberArrayList.add(new FoodAndNumber(nameFood, numberFood));
                        nameFood = "";numberFood = 0;
                    }
                }
                else{
                    numberFood = 0;
                }
            }
        }
        FoodAndNumberAdapter foodAndNumberAdapter = new FoodAndNumberAdapter(foodAndNumberArrayList, this);
        rvFoodAndNumber.setAdapter(foodAndNumberAdapter);
        rvFoodAndNumber.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        icPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}