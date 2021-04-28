package com.andeptrai.doantotnghiep.ui.setting_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.andeptrai.doantotnghiep.IP;
import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingAppActivity extends AppCompatActivity {

    TableRow avatar_row, password_row, private_info_row;
    CircleImageView avatar_setting;
    private static String urlChangePassword = "http://"+ IP.ip+"/DoAnTotNghiep/androidwebservice/changePassword.php";

//    FirebaseAuth firebaseAuth;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_app);

        avatar_row = findViewById(R.id.avatar_row);
        avatar_row.setOnClickListener(changeAvatarListener);
        avatar_setting = findViewById(R.id.avatar_setting);
        password_row = findViewById(R.id.password_row);
        password_row.setOnClickListener(changePasswordClickListener);
        private_info_row = findViewById(R.id.private_info_row);
        private_info_row.setOnClickListener(openChangePrivateInfoListener);

    }

    private View.OnClickListener changeAvatarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGalleryIntent,  1000);

        }
    };

    private View.OnClickListener changePasswordClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogChangePassword();
        }
    };

    private View.OnClickListener openChangePrivateInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SettingAppActivity.this, EditInfoUserActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                avatar_setting.setImageURI(uri);
            }
        }
    }

    private void DialogChangePassword(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_pwd);

        final EditText edtOldPwd = dialog.findViewById(R.id.edtOldPwd);
        final EditText edtNewPwd = dialog.findViewById(R.id.edtNewPwd);
        final EditText edtReNewPwd = dialog.findViewById(R.id.edtReNewPwd);
        Button btnChangePwd = dialog.findViewById(R.id.btnChangePwd);
        Button btnCancelChangePwd = dialog.findViewById(R.id.btnCancelChangePwd);

        btnCancelChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwdOld = edtOldPwd.getText().toString();
                String pwdNew = edtNewPwd.getText().toString();
                String pwdNewRe = edtReNewPwd.getText().toString();
                if (!pwdOld.equals(InfoUserCurr.currentPwd)){
                    edtOldPwd.setError("Mật khẩu cũ không đúng");
                }
                else if(pwdNew.equalsIgnoreCase("")){
                    edtNewPwd.setError("Không để trống mật khẩu");
                }
                else if(!pwdNewRe.equals(pwdNew)){
                    edtNewPwd.setError("Mật khẩu nhập lại không đúng");
                }
                else {
                    changePassword(InfoUserCurr.currentId, pwdNew);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void changePassword(final int id, final String newPassword) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlChangePassword
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success_change_password")){
                    InfoUserCurr.currentPwd = newPassword;
                    Toast.makeText(SettingAppActivity.this, "success_change_password!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SettingAppActivity.this, "Error change pwd!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingAppActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idToChangePwd", id + "");
                params.put("passwordNew", newPassword);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}