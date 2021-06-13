package com.andeptrai.doantotnghiep.ui.delivery;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.DeliveryFoodAdapter;
import com.andeptrai.doantotnghiep.data.model.BillDeliveryOrdered;
import com.andeptrai.doantotnghiep.data.model.DeliveryFood;
import com.andeptrai.doantotnghiep.interf.DeliveryFoodInterf;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.andeptrai.doantotnghiep.URL.urlGetAllFoodByIdRes;
import static com.andeptrai.doantotnghiep.URL.urlUpdateBillDeliveryByIdBill;

public class EditBillDeliveryActivity extends AppCompatActivity implements View.OnClickListener, DeliveryFoodInterf {


    ImageView icPageBack;
    TextView txtNameRestaurant, txtTimeCreateBill, txtStatusConfirm, txtBillType
            , txtAddressDelivery, txtTimeDelivery, txtTotalMoney, txtPayment, txtNotes, txtChangeStatusConfirm
            , txtChangeAddress, txtChangeTimeDelivery, txtChangePayment, txtChangeNotes;

    RecyclerView rvFoodAndNumber;
    Button btnBack, btnSaveBill;

    BillDeliveryOrdered currentBillDelivery;
    ArrayList<DeliveryFood> deliveryFoodArrayList = new ArrayList<>();
    DeliveryFoodAdapter deliveryFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill_delivery);

        currentBillDelivery = (BillDeliveryOrdered) getIntent().getSerializableExtra("currentBillDelivery");

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
        btnSaveBill = findViewById(R.id.btnSaveBill);

        //add 31 - 5
        txtChangeAddress = findViewById(R.id.txtChangeAddress);
        txtChangeTimeDelivery = findViewById(R.id.txtChangeTimeDelivery);
        txtChangePayment = findViewById(R.id.txtChangePayment);
        txtChangeNotes = findViewById(R.id.txtChangeNotes);

        txtChangeStatusConfirm.setOnClickListener(this);
        txtChangeAddress.setOnClickListener(this);
        txtChangeTimeDelivery.setOnClickListener(this);
        txtChangePayment.setOnClickListener(this);
        txtChangeNotes.setOnClickListener(this);
        //end add 31 - 5


        txtNameRestaurant.setText(currentBillDelivery.getNameRestaurant());
        txtTimeCreateBill.setText(currentBillDelivery.getTimeCreateBill());
        if (currentBillDelivery.getStatusConfirm() == 0){ txtStatusConfirm.setText(R.string.accept_yet); }
        else if(currentBillDelivery.getStatusConfirm() == 1){ txtStatusConfirm.setText(R.string.accepted); }
        else if(currentBillDelivery.getStatusConfirm() == 2){ txtStatusConfirm.setText(R.string.rejected); }
        txtBillType.setText(R.string.bill_delivery);
        txtAddressDelivery.setText(currentBillDelivery.getAddressDelivery());
        txtTimeDelivery.setText(currentBillDelivery.getTimeDelivery());
        txtTotalMoney.setText(currentBillDelivery.getTotalMoneyBill() + "");
        txtPayment.setText(currentBillDelivery.getPayment());
        txtNotes.setText(currentBillDelivery.getNotes());

        deliveryFoodAdapter = new DeliveryFoodAdapter(deliveryFoodArrayList, this,this);
        rvFoodAndNumber.setAdapter(deliveryFoodAdapter);
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

        getAllFoodByIdRes();

        btnSaveBill.setOnClickListener(this);
    }

    private void setFoodNumber() {

        String foodDetail = currentBillDelivery.getDetailFood();
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
                        nameFood = nameFood.replace(" " + numberFood + ",","");
                        //Log.d("nameFoodReplace", nameFood);
                        for (int j = 0; j < deliveryFoodArrayList.size(); j++){
                            if (deliveryFoodArrayList.get(j).getName_food().equals(nameFood)){
                                deliveryFoodArrayList.get(j).setNumberDelivery(numberFood);
                            }
                        }
                        nameFood = "";numberFood = 0;
                    }
                }
                else{
                    numberFood = 0;
                }
            }
        }
        updateTotalMoney();
        deliveryFoodAdapter.notifyDataSetChanged();
    }

    private void getAllFoodByIdRes() {
        RequestQueue requestQueue = Volley.newRequestQueue(EditBillDeliveryActivity.this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlGetAllFoodByIdRes
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Get food this restaurant fail")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            deliveryFoodArrayList.add(new DeliveryFood(jsonObject.getString("Id_restaurant"),
                                    jsonObject.getString("Id_food"),
                                    jsonObject.getString("Name_food"),
                                    jsonObject.getInt("Status"),
                                    jsonObject.getInt("Price"),
                                    jsonObject.getInt("Promotion"), 0));
                        }
                        setFoodNumber();

                    } catch (JSONException e) {

                        Toast.makeText(EditBillDeliveryActivity.this, "Get food error exception! -- " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(EditBillDeliveryActivity.this, "Get food fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditBillDeliveryActivity.this, "Get all food by id res error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idRestaurant", currentBillDelivery.getIdRestaurant());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtChangeAddress){
            openChangeAddressDialog();
        }
        else if (view.getId() == R.id.txtChangeTimeDelivery){
            editTimeDelivery();
        }
        else if (view.getId() == R.id.txtChangePayment){
            openChangePaymentDialog();
        }
        else if (view.getId() == R.id.txtChangeNotes){
            openChangeNotesDialog();
        }
        else if (view.getId() == R.id.btnSaveBill){
            saveBill();
        }
    }

    private void saveBill() {
        setDetailBill();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlUpdateBillDeliveryByIdBill
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RespondUpdateDelivery", response.trim());
                if (response.trim().equals("Update bill delivery success")){
                    Toast.makeText(EditBillDeliveryActivity.this, "Update bill delivery success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("updateBillDelivery", currentBillDelivery);
                    setResult(1001, intent);
                    finish();
                }
                else if (response.trim().equals("Update status confirm success, bill delivery fail")){
                    Toast.makeText(EditBillDeliveryActivity.this, "Update status confirm success, bill delivery fail", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditBillDeliveryActivity.this, "Update bill delivery fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditBillDeliveryActivity.this, "Update bill delivery error ---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("editIdBill", currentBillDelivery.getIdBill());
                params.put("editStatusConfirm", currentBillDelivery.getStatusConfirm() + "");
                params.put("editAddressDelivery", currentBillDelivery.getAddressDelivery());
                params.put("editTimeDelivery", currentBillDelivery.getTimeDelivery() + "");
                params.put("editTotalMoneyBill", currentBillDelivery.getTotalMoneyBill() + "");
                params.put("editPayment", currentBillDelivery.getPayment());
                params.put("editNotes", currentBillDelivery.getNotes());
                params.put("editDetailBill", currentBillDelivery.getDetailBill());
                params.put("editDetailFood", currentBillDelivery.getDetailFood());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void setDetailBill(){
        String detailBill = "";
        String detailFood = "";
        for(int i = 0; i < deliveryFoodArrayList.size(); i++){
            if (deliveryFoodArrayList.get(i).getNumberDelivery() > 0){
                detailBill += deliveryFoodArrayList.get(i).getId_food() + " "
                        + deliveryFoodArrayList.get(i).getNumberDelivery() + ",";

                detailFood += deliveryFoodArrayList.get(i).getName_food() + " "
                        + deliveryFoodArrayList.get(i).getNumberDelivery() + ",";
            }
        }

        currentBillDelivery.setDetailBill(detailBill);
        currentBillDelivery.setDetailFood(detailFood);
    }

    private void openChangeNotesDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_content);

        final EditText edtContent = dialog.findViewById(R.id.edtContent);
        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnBack = dialog.findViewById(R.id.btnBack);

        edtContent.setText(currentBillDelivery.getNotes());

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBillDelivery.setNotes(edtContent.getText().toString());
                txtNotes.setText(edtContent.getText().toString());
                dialog.dismiss();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openChangePaymentDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_payment);

        final RadioGroup groupPayment = dialog.findViewById(R.id.groupPayment);
        final RadioButton paymentAirplay = dialog.findViewById(R.id.paymentAirplay);
        final RadioButton paymentCash = dialog.findViewById(R.id.paymentCash);
        final RadioButton paymentCreditCard = dialog.findViewById(R.id.paymentCreditCard);
        final RadioButton paymentATM_InternetBanking = dialog.findViewById(R.id.paymentATM_InternetBanking);
        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnBack = dialog.findViewById(R.id.btnBack);

        if (currentBillDelivery.getPayment().equalsIgnoreCase(paymentAirplay.getText().toString())) {
            groupPayment.check(R.id.paymentAirplay);
        }
        else if (currentBillDelivery.getPayment().equalsIgnoreCase(paymentCash.getText().toString())){
            groupPayment.check(R.id.paymentCash);
        }
        else if (currentBillDelivery.getPayment().equalsIgnoreCase(paymentCreditCard.getText().toString())){
            groupPayment.check(R.id.paymentCreditCard);
        }
        else if (currentBillDelivery.getPayment().equalsIgnoreCase(paymentATM_InternetBanking.getText().toString())){
            groupPayment.check(R.id.paymentATM_InternetBanking);
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (groupPayment.getCheckedRadioButtonId() == R.id.paymentAirplay){
                    currentBillDelivery.setPayment(paymentAirplay.getText().toString());
                    txtPayment.setText(paymentAirplay.getText().toString());
                }
                else if (groupPayment.getCheckedRadioButtonId() == R.id.paymentCash){
                    currentBillDelivery.setPayment(paymentCash.getText().toString());
                    txtPayment.setText(paymentCash.getText().toString());
                }
                else if (groupPayment.getCheckedRadioButtonId() == R.id.paymentCreditCard){
                    currentBillDelivery.setPayment(paymentCreditCard.getText().toString());
                    txtPayment.setText(paymentCreditCard.getText().toString());
                }
                else if (groupPayment.getCheckedRadioButtonId() == R.id.paymentATM_InternetBanking){
                    currentBillDelivery.setPayment(paymentATM_InternetBanking.getText().toString());
                    txtPayment.setText(paymentATM_InternetBanking.getText().toString());
                }
                dialog.dismiss();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void editTimeDelivery() {
        // Get Current Date
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditBillDeliveryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditBillDeliveryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        currentBillDelivery.setTimeDelivery((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()));
                        txtTimeDelivery.setText((new SimpleDateFormat("HH:mm:ss-dd/MM/yyyy")).format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void openChangeAddressDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_content);

        final EditText edtContent = dialog.findViewById(R.id.edtContent);
        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnBack = dialog.findViewById(R.id.btnBack);

        edtContent.setText(currentBillDelivery.getAddressDelivery());

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBillDelivery.setAddressDelivery(edtContent.getText().toString());
                txtAddressDelivery.setText(edtContent.getText().toString());
                dialog.dismiss();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void updateCartClickListener() {
        updateTotalMoney();
    }

    private void updateTotalMoney() {
        long totalMoney = 0;
        for (int i = 0; i < deliveryFoodArrayList.size(); i++){
            if (deliveryFoodArrayList.get(i).getNumberDelivery() > 0){
                totalMoney += deliveryFoodArrayList.get(i).getNumberDelivery() * (deliveryFoodArrayList.get(i).getPrice());
            }
        }
        currentBillDelivery.setTotalMoneyBill(totalMoney);
        txtTotalMoney.setText(totalMoney+"");
    }
}