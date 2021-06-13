package com.andeptrai.doantotnghiep.ui.setting_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.IP;
import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditInfoUserActivity extends AppCompatActivity {

    ImageView icPageBack;
    EditText edtPrivateName, edtPrivatePhone, edtPrivateEmail, edtPrivateAddress;
    TextView txtUsername;
    Button btnSaveNewPrivateInfo;
    public static String urlUpdateInfoUser = "http://"+ IP.ip+"/DoAnTotNghiep/androidwebservice/updateInfoUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_user);

        icPageBack = findViewById(R.id.icPageBack);
        icPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edtPrivateName = findViewById(R.id.edtPrivateName);
        edtPrivatePhone = findViewById(R.id.edtPrivatePhone);
        edtPrivateEmail = findViewById(R.id.edtPrivateEmail);
        edtPrivateAddress = findViewById(R.id.edtPrivateAddress);
        txtUsername = findViewById(R.id.txtUsername);
        btnSaveNewPrivateInfo = findViewById(R.id.btnSaveNewPrivateInfo);
        btnSaveNewPrivateInfo.setOnClickListener(updateUserClickListener);

        setCurrInfo();
    }

    private void setCurrInfo() {
        txtUsername.setText(InfoUserCurr.currentUsername);
        edtPrivateName.setText(InfoUserCurr.currentName);
        edtPrivatePhone.setText(InfoUserCurr.currentPhone);
        edtPrivateEmail.setText(InfoUserCurr.currentEmail);
        edtPrivateAddress.setText(InfoUserCurr.currentAddress);
    }

    private View.OnClickListener updateUserClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            upDateInfoUser();
        }
    };

    private void upDateInfoUser() {
        final String newName = edtPrivateName.getText().toString();
        final String newPhone = edtPrivatePhone.getText().toString();
        final String newEmail = edtPrivateEmail.getText().toString();
        final String newAddress = edtPrivateAddress.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateInfoUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Update user success")){
                            Toast.makeText(EditInfoUserActivity.this, "Update success!",
                                    Toast.LENGTH_LONG).show();
                            InfoUserCurr.currentName = newName;
                            InfoUserCurr.currentEmail = newEmail;
                            InfoUserCurr.currentAddress = newAddress;
                            InfoUserCurr.currentPhone = newPhone;
                        }
                        else{
                            Toast.makeText(EditInfoUserActivity.this, "Error in code server! - " + response.trim(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditInfoUserActivity.this, "Error Update User!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("new_id_customerClient", InfoUserCurr.currentId + "");
                params.put("newNameClient", newName + "");
                params.put("newPhoneClient", newPhone + "");
                params.put("newEmailClient", newEmail + "");
                params.put("newAddressClient", newAddress + "");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}