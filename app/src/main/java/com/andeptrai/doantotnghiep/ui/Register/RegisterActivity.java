package com.andeptrai.doantotnghiep.ui.Register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andeptrai.doantotnghiep.IP;
import com.andeptrai.doantotnghiep.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUsernameSign, edtPasswordSign, edtPhoneSign, edtNameSign, edtEmailSign, edtAddressSign;
    Button btnSignUp;

    private static String urlSignUp = "http://"+ IP.ip+"/DoAnTotNghiep/androidwebservice/signUpAPI.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsernameSign = findViewById(R.id.edtUsernameSign);
        edtPasswordSign = findViewById(R.id.edtPasswordSign);
        edtPhoneSign = findViewById(R.id.edtPhoneSign);
        edtNameSign = findViewById(R.id.edtNameSign);
        edtEmailSign = findViewById(R.id.edtEmailSign);
        edtAddressSign = findViewById(R.id.edtAddressSign);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:
                String usernameNew = edtUsernameSign.getText().toString();
                String passwordNew = edtPasswordSign.getText().toString();
                String phoneNew = edtPhoneSign.getText().toString();
                String nameNew = edtNameSign.getText().toString();
                String emailNew = edtEmailSign.getText().toString();
                String addressNew = edtAddressSign.getText().toString();
                register(usernameNew, passwordNew, phoneNew, nameNew, emailNew, addressNew);
                break;
        }
    }

    private void register(String usernameNew, String passwordNew, String phoneNew, String nameNew
            , String emailNew, String addressNew) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSignUp
                , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);
    }
}