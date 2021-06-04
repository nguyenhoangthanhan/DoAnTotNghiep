package com.andeptrai.doantotnghiep.ui.reservation;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.AdultsChildrenNumberAdapter;
import com.andeptrai.doantotnghiep.data.model.BillReservation;
import com.andeptrai.doantotnghiep.data.model.InfoRestaurant;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.interf.SetNumberInterf;
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
import static com.andeptrai.doantotnghiep.URL.urlCreateNewBillReservation;

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener, SetNumberInterf {

    ImageView icPageBackReservation;
    TextView nameRestaurantReservation, addressResReservation, txtPhoneReservation, txtCallPhone, txtDateGoRestaurant
            ,txtChangDateGo, txtTimeGo, txtChangeTimeGo, txtNumberAdults, txtChangeNumberAdults, txtNumberChildren
            , txtChangeNumberChildren, txtContinues;

    EditText edtNoteReservation;
    InfoRestaurant currentInfoRestaurant;

    String dateGoRestaurant = "";
    String timeGoRestaurant = "";
    String datetimeGoRestaurant = "";
    int numberChildren = 0;
    int numberAdults = 0;
    String notes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        currentInfoRestaurant = (InfoRestaurant) getIntent().getSerializableExtra("currentInfoRestaurant");

        icPageBackReservation = findViewById(R.id.icPageBackReservation);
        nameRestaurantReservation = findViewById(R.id.nameRestaurantReservation);
        addressResReservation = findViewById(R.id.addressResReservation);
        txtPhoneReservation = findViewById(R.id.txtPhoneReservation);
        txtCallPhone = findViewById(R.id.txtCallPhone);
        txtDateGoRestaurant = findViewById(R.id.txtDateGoRestaurant);
        txtChangDateGo = findViewById(R.id.txtChangDateGo);
        txtTimeGo = findViewById(R.id.txtTimeGo);
        txtChangeTimeGo = findViewById(R.id.txtChangeTimeGo);
        txtNumberAdults = findViewById(R.id.txtNumberAdults);
        txtChangeNumberAdults = findViewById(R.id.txtChangeNumberAdults);
        txtNumberChildren = findViewById(R.id.txtNumberChildren);
        txtChangeNumberChildren = findViewById(R.id.txtChangeNumberChildren);
        edtNoteReservation = findViewById(R.id.edtNoteReservation);
        txtContinues = findViewById(R.id.txtContinues);

        nameRestaurantReservation.setText(currentInfoRestaurant.getName_restaurant());
        addressResReservation.setText(currentInfoRestaurant.getAddress_restaurant());
        txtPhoneReservation.setText(currentInfoRestaurant.getPhone_restaurant() + "");

        icPageBackReservation.setOnClickListener(this);
        txtContinues.setOnClickListener(this);
        txtChangDateGo.setOnClickListener(this);
        txtChangeTimeGo.setOnClickListener(this);
        txtChangeNumberAdults.setOnClickListener(this);
        txtChangeNumberChildren.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.icPageBackReservation){
            openConfirmReservationBillDialog();
        }
        if (view.getId() == R.id.txtContinues){
            openConfirmReservationBillDialog();
        }
        if (view.getId() == R.id.txtChangDateGo){
            setDateGoRestaurant();
        }
        if (view.getId() == R.id.txtChangeTimeGo){
            setTimeGoRestaurant();
        }
        if (view.getId() == R.id.txtChangeNumberAdults){
            setAdultsNumber();
        }
        if (view.getId() == R.id.txtChangeNumberChildren){
            setChildrenNumber();
        }
    }

    private void setChildrenNumber() {
        final Dialog childrenNumberDialog = new Dialog(ReservationActivity.this);
        childrenNumberDialog.setContentView(R.layout.dialog_number_adults_children);

        TextView txtTittle = childrenNumberDialog.findViewById(R.id.txtTittle);
        txtTittle.setText("Trẻ em");

        RecyclerView rvNumber = childrenNumberDialog.findViewById(R.id.rvNumber);
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < 200; i++){
            stringArrayList.add((i+1) + "");
        }

        AdultsChildrenNumberAdapter numberAdapter = new AdultsChildrenNumberAdapter(stringArrayList
                        , ReservationActivity.this, childrenNumberDialog, this, "Children");
        rvNumber.setAdapter(numberAdapter);
        rvNumber.setLayoutManager(new LinearLayoutManager(this));
        Button btnBack = childrenNumberDialog.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childrenNumberDialog.dismiss();
            }
        });
        childrenNumberDialog.show();
    }

    private void setAdultsNumber() {
        final Dialog adultsNumberDialog = new Dialog(ReservationActivity.this);
        adultsNumberDialog.setContentView(R.layout.dialog_number_adults_children);

        TextView txtTittle = adultsNumberDialog.findViewById(R.id.txtTittle);
        txtTittle.setText("Người lớn");

        RecyclerView rvNumber = adultsNumberDialog.findViewById(R.id.rvNumber);
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < 200; i++){
            stringArrayList.add((i+1) + "");
        }

        AdultsChildrenNumberAdapter numberAdapter = new AdultsChildrenNumberAdapter(stringArrayList
                , ReservationActivity.this, adultsNumberDialog, this, "Adults");
        rvNumber.setAdapter(numberAdapter);
        rvNumber.setLayoutManager(new LinearLayoutManager(this));
        Button btnBack = adultsNumberDialog.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adultsNumberDialog.dismiss();
            }
        });

        adultsNumberDialog.show();
    }

    private void setTimeGoRestaurant() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(ReservationActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                txtTimeGo.setText((new SimpleDateFormat("HH:mm")).format(calendar.getTime()));
                timeGoRestaurant = (new SimpleDateFormat("HH:mm:ss")).format(calendar.getTime()) + "";
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void setDateGoRestaurant() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                txtDateGoRestaurant.setText((new SimpleDateFormat("dd/MM/yyyy")).format(calendar.getTime()));
                dateGoRestaurant = (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime()) + "";
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void openConfirmReservationBillDialog() {
        final Dialog confirmReservationDialog = new Dialog(this);
        confirmReservationDialog.setContentView(R.layout.dialog_reservation_confirmation);

        final TextView txtNameResReserDialog = confirmReservationDialog.findViewById(R.id.txtNameResReserDialog);
        final TextView txtAddrReserDialog = confirmReservationDialog.findViewById(R.id.txtAddrReserDialog);
        final TextView txtDateGoDialog = confirmReservationDialog.findViewById(R.id.txtDateGoDialog);
        final TextView txtTimeGoDialog = confirmReservationDialog.findViewById(R.id.txtTimeGoDialog);
        final TextView txtNumberAdultsDialog = confirmReservationDialog.findViewById(R.id.txtNumberAdultsDialog);
        final TextView txtNumberChildrenDialog = confirmReservationDialog.findViewById(R.id.txtNumberChildrenDialog);
        final Button btnConfirmReservation = confirmReservationDialog.findViewById(R.id.btnConfirmReservation);
        final Button btnCancelReservation = confirmReservationDialog.findViewById(R.id.btnCancelReservation);

        txtNameResReserDialog.setText(currentInfoRestaurant.getName_restaurant());
        txtAddrReserDialog.setText(currentInfoRestaurant.getAddress_restaurant());
        txtDateGoDialog.setText(txtDateGoRestaurant.getText().toString());
        txtTimeGoDialog.setText(txtTimeGo.getText().toString());
        txtNumberAdultsDialog.setText(numberAdults + "");
        txtNumberChildrenDialog.setText(numberChildren + "");

        btnCancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmReservationDialog.dismiss();
            }
        });

        btnConfirmReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewBillReservation(confirmReservationDialog);
            }
        });

        confirmReservationDialog.show();
    }

    private void createNewBillReservation(final Dialog confirmReservationDialog) {
        final BillReservation billReservation = new BillReservation((new RandomString(CREATE_NEW_BILL, new Random()).nextString())
        , InfoUserCurr.currentId, currentInfoRestaurant.getId_restaurant()
        , (new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()))
        , dateGoRestaurant + " " +timeGoRestaurant, numberAdults, numberChildren
                , edtNoteReservation.getText().toString());


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlCreateNewBillReservation
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RespondReservation", response.trim());
                if (response.trim().equals("Create new bill reservation success")){
                    Toast.makeText(ReservationActivity.this, "Create new bill reservation success!", Toast.LENGTH_SHORT).show();
                    setResult(1001);
                    confirmReservationDialog.dismiss();
                    finish();
                }
                else if (response.trim().equals("Create new bill success, reservation fail")){
                    Toast.makeText(ReservationActivity.this, "Create new bill success, reservation fail", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ReservationActivity.this, "Create new bill reservation fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReservationActivity.this, "Create new bill reservation error ---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idBill", billReservation.getIdBill());
                params.put("idUserOrder", billReservation.getIdUserOrder() + "");
                params.put("idRestaurant", billReservation.getIdRestaurant());
                params.put("timeCreateBill", billReservation.getTimeCreateBill());

                params.put("datetimeGo", billReservation.getDatetimeGo());
                params.put("adultsNumber", billReservation.getAdultsNumber() + "");
                params.put("childrenNumber", billReservation.getChildrenNumber() + "");
                params.put("notes", billReservation.getNotes());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void setNumberAdultsClickListener(Dialog childrenNumberDialog, String s) {
        numberAdults = Integer.parseInt(s);
        txtNumberAdults.setText(s);
        childrenNumberDialog.dismiss();
    }

    @Override
    public void setNumberChildrenClickListener(Dialog childrenNumberDialog, String s) {
        numberChildren = Integer.parseInt(s);
        txtNumberChildren.setText(s);
        childrenNumberDialog.dismiss();
    }
}