package com.andeptrai.doantotnghiep.ui.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.ui.main.MainActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.andeptrai.doantotnghiep.URL.urlCheckUser;
import static com.andeptrai.doantotnghiep.URL.urlSignUp;
import static com.andeptrai.doantotnghiep.library_code.CheckInfoSignUp.checkPass;
import static com.andeptrai.doantotnghiep.library_code.CheckInfoSignUp.checkRePass;
import static com.andeptrai.doantotnghiep.library_code.CheckInfoSignUp.inputYet;
import static com.andeptrai.doantotnghiep.library_code.CheckInfoSignUp.isEmail;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUsernameSign, edtPasswordSign, edtPhoneSign, edtNameSign, edtEmailSign, edtAddressSign, edtRePasswordSign;
    Button btnSignUp;
    TextView txtShowError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsernameSign = findViewById(R.id.edtUsernameSign);
        edtPasswordSign = findViewById(R.id.edtPasswordSign);
        edtRePasswordSign = findViewById(R.id.edtRePasswordSign);
        edtPhoneSign = findViewById(R.id.edtPhoneSign);
        edtNameSign = findViewById(R.id.edtNameSign);
        edtEmailSign = findViewById(R.id.edtEmailSign);
        edtAddressSign = findViewById(R.id.edtAddressSign);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtShowError = findViewById(R.id.txtShowError);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:
                String usernameNew = edtUsernameSign.getText().toString();
                String passwordNew = edtPasswordSign.getText().toString();
                String rePasswordNew = edtRePasswordSign.getText().toString();
                String phoneNew = edtPhoneSign.getText().toString();
                String nameNew = edtNameSign.getText().toString();
                String emailNew = edtEmailSign.getText().toString();
                String addressNew = edtAddressSign.getText().toString();

                if (usernameNew.length() < 8){
                    edtUsernameSign.setError("T??i kho???n ph???i l???n h??n 8 k?? t???");
                }
                else if(checkPass(passwordNew).equals("Length litter 8 characters")){
                    edtPasswordSign.setError("M???t kh???u ph???i l???n h??n 8 k?? t???");
                }
                else if(!checkRePass(passwordNew, rePasswordNew)){
                    edtRePasswordSign.setError("Nh???p l???i m???t kh???u kh??ng kh???p");
                }
                else if(inputYet(nameNew)){
                    edtNameSign.setError("B???n ch??a nh???p t??n");
                }
                else if(!isEmail(emailNew)){
                    edtEmailSign.setError("Sai ?????nh d???ng email");
                }
                else{
                    register(usernameNew, passwordNew, phoneNew, nameNew, emailNew, addressNew);
                }

                break;
        }
    }

    private void register(final String usernameNew, final String passwordNew, final String phoneNew, final String nameNew
            , final String emailNew, final String addressNew) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSignUp
                , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Register success")){
                            Toast.makeText(RegisterActivity.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                            ReadJSON(usernameNew, passwordNew);
                        }
                        else if(response.trim().equals("Error when insert into Database")){
                            txtShowError.setVisibility(View.VISIBLE);
                            txtShowError.setText("L???i khi th??m d??? li???u v??o database");
                        }
                        else if(response.trim().equals("Username already exist")){
                            txtShowError.setVisibility(View.VISIBLE);
                            txtShowError.setText("T??i kho???n ???? t???n t???i");
                        }
                        else if(response.trim().equals("Error create account")){
                            txtShowError.setVisibility(View.VISIBLE);
                            txtShowError.setText("L???i t???o t??i kho???n");
                        }
                        else{
                            txtShowError.setVisibility(View.VISIBLE);
                            txtShowError.setText("L???i t???o t??i kho???n");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register fail!---"+error.toString(), Toast.LENGTH_LONG).show();
                    }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("usernameNew", usernameNew);
                params.put("passwordNew", passwordNew);
                params.put("phoneNew", phoneNew);
                params.put("nameNew", nameNew);
                params.put("emailNew", emailNew);
                params.put("addressNew", addressNew);
                params.put("checkAdmin", "0");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ReadJSON(final String username, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlCheckUser
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Login fail")){

                    try {
                        JSONObject jObject = new JSONObject(response);
                        InfoUserCurr.currentId = jObject.getInt("Id_customer");
                        InfoUserCurr.currentUsername = jObject.getString("Username");
                        InfoUserCurr.currentPhone = jObject.getString("Phone");
                        InfoUserCurr.currentPwd = jObject.getString("Password");
                        InfoUserCurr.currentEmail = jObject.getString("Email");
                        InfoUserCurr.currentName = jObject.getString("Name");
                        InfoUserCurr.currentAddress = jObject.getString("Address");
                        Toast.makeText(RegisterActivity.this, "Register success! - " + InfoUserCurr.currentId +
                                "+" + InfoUserCurr.currentUsername +
                                "+" + InfoUserCurr.currentPhone +
                                "+" + InfoUserCurr.currentPwd +
                                "+" + InfoUserCurr.currentEmail +
                                "+" + InfoUserCurr.currentName +
                                "+" + InfoUserCurr.currentAddress, Toast.LENGTH_LONG).show();

//                        Toast.makeText(LoginActivity.this, "Login th??nh c??ng", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {

                        Toast.makeText(RegisterActivity.this, "Loi! -- " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Register fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Register fail!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("usernameClient", username);
                params.put("passwordClient", password);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}