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
                    edtUsernameSign.setError("Tài khoản phải lớn hơn 8 ký tự");
                }
                else if(checkPass(passwordNew).equals("Length litter 8 characters")){
                    edtPasswordSign.setError("Mật khẩu phải lớn hơn 8 ký tự");
                }
                else if(!checkRePass(passwordNew, rePasswordNew)){
                    edtRePasswordSign.setError("Nhập lại mật khẩu không khớp");
                }
                else if(inputYet(nameNew)){
                    edtNameSign.setError("Bạn chưa nhập tên");
                }
                else if(!isEmail(emailNew)){
                    edtEmailSign.setError("Sai định dạng email");
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
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                            InfoUserCurr.currentId = -1;
//                            InfoUserCurr.currentUsername = usernameNew;
//                            InfoUserCurr.currentPwd = passwordNew;
//                            InfoUserCurr.currentPhone = phoneNew;
//                            InfoUserCurr.currentEmail = emailNew;
//                            InfoUserCurr.currentName = nameNew;
//                            InfoUserCurr.currentAddress = addressNew;
//                            startActivity(intent);

                            ReadJSON(usernameNew, passwordNew);
                        }
                        else if(response.trim().equals("Error when insert into Database")){
                            txtShowError.setText("Lỗi khi thêm dữ liệu vào database");
                        }
                        else if(response.trim().equals("Username already exist")){
                            txtShowError.setText("Tài khoản đã tồn tại");
                        }
                        else if(response.trim().equals("Error create account")){
                            txtShowError.setText("Lỗi tạo tài khoản");
                        }
                        else{
                            txtShowError.setText("Lỗi tạo tài khoản");
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

//                        Toast.makeText(LoginActivity.this, "Login thành công", Toast.LENGTH_SHORT).show();
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