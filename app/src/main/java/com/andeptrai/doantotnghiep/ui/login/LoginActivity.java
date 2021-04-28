package com.andeptrai.doantotnghiep.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.IP;
import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.ui.Register.RegisterActivity;
import com.andeptrai.doantotnghiep.ui.main.MainActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtpassword;
    Button btnlogin, btnRegister;

    private static String urlCheckUser = "http://"+ IP.ip+"/DoAnTotNghiep/androidwebservice/checkInfoLogin.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtpassword = findViewById(R.id.edtpassword);
        btnlogin = findViewById(R.id.btnlogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"abc",Toast.LENGTH_SHORT).show();
                edtUsername.setError(null);
                edtpassword.setError(null);

                String username = edtUsername.getText().toString();
                String password = edtpassword.getText().toString();

                if(username.equalsIgnoreCase("")){
                    edtUsername.setError("Không được để trống SĐT");
                }
                else if(password.equalsIgnoreCase("")){
                    edtpassword.setError("Không được để trống Password");
                }
                else{
                    login(username, password);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String phone, String password) {
        ReadJSON(phone, password);
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
                        Toast.makeText(LoginActivity.this, "Login success! - " + InfoUserCurr.currentId +
                                "+" + InfoUserCurr.currentUsername +
                                "+" + InfoUserCurr.currentPhone +
                                "+" + InfoUserCurr.currentPwd +
                                "+" + InfoUserCurr.currentEmail +
                                "+" + InfoUserCurr.currentName +
                                "+" + InfoUserCurr.currentAddress, Toast.LENGTH_LONG).show();

//                        Toast.makeText(LoginActivity.this, "Login thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {

                        Toast.makeText(LoginActivity.this, "Loi! -- " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Login fail!---"+error.toString(), Toast.LENGTH_LONG).show();
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