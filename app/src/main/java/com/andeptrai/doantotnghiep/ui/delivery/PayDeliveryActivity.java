package com.andeptrai.doantotnghiep.ui.delivery;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.PayFoodAdapter;
import com.andeptrai.doantotnghiep.data.model.BillDelivery;
import com.andeptrai.doantotnghiep.data.model.DeliveryFood;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.library_code.RandomString;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.andeptrai.doantotnghiep.CODE.CREATE_NEW_BILL;
import static com.andeptrai.doantotnghiep.URL.urlCreateNewBillDelivery;

public class PayDeliveryActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout viewBackPayment, viewFooterDelivery, viewAddressDelivery, viewNoteBillIn, viewPromotionId, viewPay, viewOrderNow;
    ImageView icPageBackDelivery;
    TextView txtEditAddressDelivery, txtEditTimeDelivery, txtTotalMoney, txtTimeDelivery
            , txtAddressUser, txtNamePhoneUser, numberFood, txtTotalMoney2;
    RadioGroup groupPayment;

    RecyclerView rvFoodPay;
    ArrayList<DeliveryFood> deliveryFoodArrayListCart;
    PayFoodAdapter payFoodAdapter;
    InfoRestaurant currentInfoRestaurant;

    int totalMoney = 0;
    int numberFoodInCart = 0;
    String notes = "";
    String detailBill = "";
    String detailFood = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_delivery);

        deliveryFoodArrayListCart = (ArrayList<DeliveryFood>) getIntent().getSerializableExtra("deliveryFoodArrayListCart");
        currentInfoRestaurant = (InfoRestaurant) getIntent().getSerializableExtra("currentInfoRestaurant");

        viewBackPayment = findViewById(R.id.viewBackPayment);
        viewFooterDelivery = findViewById(R.id.viewFooterDelivery);
        viewAddressDelivery = findViewById(R.id.viewAddressDelivery);
        viewNoteBillIn = findViewById(R.id.viewNoteBillIn);
        viewPromotionId = findViewById(R.id.viewPromotionId);
        viewPay = findViewById(R.id.viewPay);
        viewOrderNow = findViewById(R.id.viewOrderNow);
        icPageBackDelivery = findViewById(R.id.icPageBackDelivery);
        txtNamePhoneUser = findViewById(R.id.txtNamePhoneUser);
        txtAddressUser = findViewById(R.id.txtAddressUser);
        txtTimeDelivery = findViewById(R.id.txtTimeDelivery);
        txtEditAddressDelivery = findViewById(R.id.txtEditAddressDelivery);
        txtEditTimeDelivery = findViewById(R.id.txtEditTimeDelivery);
        numberFood = findViewById(R.id.numberFood);
        txtTotalMoney = findViewById(R.id.txtTotalMoney);
        txtTotalMoney2 = findViewById(R.id.txtTotalMoney2);
        groupPayment = findViewById(R.id.groupPayment);

        txtNamePhoneUser.setText(InfoUserCurr.currentName+ " - " + InfoUserCurr.currentPhone);
        txtAddressUser.setText(InfoUserCurr.currentAddress);
        txtTimeDelivery.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        updateCart();

        rvFoodPay = findViewById(R.id.rvFoodPay);
        payFoodAdapter = new PayFoodAdapter(deliveryFoodArrayListCart, this);
        rvFoodPay.setAdapter(payFoodAdapter);
        rvFoodPay.setLayoutManager(new LinearLayoutManager(this));

        icPageBackDelivery.setOnClickListener(this);
        txtEditTimeDelivery.setOnClickListener(this);
        viewOrderNow.setOnClickListener(this);
        viewNoteBillIn.setOnClickListener(this);

    }

    private String getDetailBill(){
        detailBill = "";
        detailFood = "";
        for(int i = 0; i < deliveryFoodArrayListCart.size(); i++){
            detailBill += deliveryFoodArrayListCart.get(i).getId_food() + " "
                    + deliveryFoodArrayListCart.get(i).getNumberDelivery() + ",";

            detailFood += deliveryFoodArrayListCart.get(i).getName_food() + " "
                    + deliveryFoodArrayListCart.get(i).getNumberDelivery() + ",";
        }
        return detailBill;
    }

    private String getPaymentMethod(){
        String paymentMethod = "";
        if(groupPayment.getCheckedRadioButtonId() == R.id.paymentAirplay){
            paymentMethod = "Ví airlpay";
        }
        else if(groupPayment.getCheckedRadioButtonId() == R.id.paymentCash){
            paymentMethod = "Tiền mặt";
        }
        else if(groupPayment.getCheckedRadioButtonId() == R.id.paymentCreditCard){
            paymentMethod = "Thẻ tín dụng/ghi nợ";
        }
        else if(groupPayment.getCheckedRadioButtonId() == R.id.paymentATM_InternetBanking){
            paymentMethod = "ATM/Internet Banking";
        }
        return paymentMethod;
    }

    private void orderFoodDelivery() {

        final BillDelivery billDelivery = new BillDelivery((new RandomString(CREATE_NEW_BILL, new Random()).nextString())
                , InfoUserCurr.currentId, currentInfoRestaurant.getId_restaurant()
                , (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()))
                , InfoUserCurr.currentAddress, txtTimeDelivery.getText().toString()
                , totalMoney, getPaymentMethod(), notes, getDetailBill(), detailFood);
        Log.d("Delivery", "***" + billDelivery.getIdBill() + "__" + billDelivery.getIdUserOrder() + "__"
                + billDelivery.getIdRestaurant() + "__"+ billDelivery.getTimeCreateBill() + "__"+ billDelivery.getAddressDelivery() + "__"
                + billDelivery.getTimeDelivery() + "__"+ billDelivery.getTotalMoneyBill() + "__"+ billDelivery.getPayment() + "__"
                + billDelivery.getNotes() + "__"+ billDelivery.getDetailBill()+ "__"+ detailFood + "***");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlCreateNewBillDelivery
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RespondDelivery", response.trim());
                if (response.trim().equals("Create new bill delivery success")){
                    Toast.makeText(PayDeliveryActivity.this, "Create new bill delivery success!", Toast.LENGTH_SHORT).show();
                    setResult(1001);
                    finish();
                }
                else if (response.trim().equals("Create new bill success, delivery fail")){
                    Toast.makeText(PayDeliveryActivity.this, "Create new bill success, delivery fail", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PayDeliveryActivity.this, "Create new bill delivery fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PayDeliveryActivity.this, "Create new bill delivery error ---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idBill", billDelivery.getIdBill());
                params.put("idUserOrder", billDelivery.getIdUserOrder() + "");
                params.put("idRestaurant", billDelivery.getIdRestaurant());
                params.put("timeCreateBill", billDelivery.getTimeCreateBill());
                params.put("addressDelivery", billDelivery.getAddressDelivery());
                params.put("timeDelivery", billDelivery.getTimeDelivery() + "");
                params.put("totalMoneyBill", billDelivery.getTotalMoneyBill() + "");
                params.put("payment", billDelivery.getPayment());
                params.put("notes", billDelivery.getNotes());
                params.put("detailBill", billDelivery.getDetailBill());
                params.put("detailFood", billDelivery.getDetailFood());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void selectTimeDelivery() {

        // Get Current Date
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(PayDeliveryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog timePickerDialog = new TimePickerDialog(PayDeliveryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        txtTimeDelivery.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private void updateCart() {
        totalMoney = 0;
        numberFoodInCart = 0;
        for (int i = 0; i < deliveryFoodArrayListCart.size(); i++){
            if (deliveryFoodArrayListCart.get(i).getNumberDelivery() > 0){
                numberFoodInCart += deliveryFoodArrayListCart.get(i).getNumberDelivery();
                totalMoney += deliveryFoodArrayListCart.get(i).getNumberDelivery() * (deliveryFoodArrayListCart.get(i).getPrice());
            }
        }

        numberFood.setText(numberFoodInCart+"");
        txtTotalMoney.setText(totalMoney+"");
        txtTotalMoney2.setText(totalMoney+"");

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.icPageBackDelivery){
            finish();
        }
        if (view.getId() == R.id.txtEditTimeDelivery){
            selectTimeDelivery();
        }
        if (view.getId() == R.id.viewOrderNow){
            orderFoodDelivery();
        }
        if (view.getId() == R.id.viewNoteBillIn){
            openSetNoteDialog();
        }
    }

    private void openSetNoteDialog() {

        final Dialog dialogSetNotes = new Dialog(this);
        dialogSetNotes.setContentView(R.layout.dialog_notes_delivery);

        final EditText edtContentNotes = dialogSetNotes.findViewById(R.id.edtContentNotes);
        edtContentNotes.setText(notes);
        Button btnGetNotes = dialogSetNotes.findViewById(R.id.btnGetNotes);
        Button btnBack = dialogSetNotes.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetNotes.dismiss();
            }
        });

        btnGetNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes = edtContentNotes.getText().toString();
                dialogSetNotes.dismiss();
            }
        });

        dialogSetNotes.show();
    }
}