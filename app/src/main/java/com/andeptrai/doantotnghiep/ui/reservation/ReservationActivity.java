package com.andeptrai.doantotnghiep.ui.reservation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.R;

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView icPageBackReservation;
    TextView nameRestaurantReservation, addressResReservation, txtPhoneReservation, txtCallPhone, txtDateGoRestaurant
            ,txtChangDateGo, txtTimeGo, txtChangeTimeGo, txtNumberAdults, txtChangeNumberAdults, txtNumberChildren
            , txtChangeNumberChildren, txtNoteReservation, txtContinues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

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
        txtNoteReservation = findViewById(R.id.txtNoteReservation);
        txtContinues = findViewById(R.id.txtContinues);

        txtContinues.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtContinues){
            openConfirmReservationBillDialog();
        }
    }

    private void openConfirmReservationBillDialog() {

    }
}