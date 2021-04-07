package com.andeptrai.doantotnghiep.ui.setting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;

import com.andeptrai.doantotnghiep.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingAppActivity extends AppCompatActivity {

    TableRow avatar_row, password_row, private_info_row;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_app);

        avatar_row = findViewById(R.id.avatar_row);
        avatar_row.setOnClickListener(changeAvatarListener);
        password_row = findViewById(R.id.password_row);
        private_info_row = findViewById(R.id.private_info_row);

    }

    private View.OnClickListener changeAvatarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    }
}