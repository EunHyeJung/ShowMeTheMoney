package com.dv.smtm.Common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dv.smtm.Model.UserDTO;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.R;


import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";
    Context mContext;

    Button btn1;

    EditText inputName;
    EditText inputEmail;
    EditText inputPwd;
    EditText inputPwdChk;
    EditText inputPhone;

    CheckBox inputAdminMode;
    CheckBox inputUserMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();

        btn1 = (Button) findViewById(R.id.button_signup);

        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPwd = (EditText) findViewById(R.id.input_pwd);
        inputPwdChk = (EditText) findViewById(R.id.input_pwd_chk);
        inputPhone = (EditText) findViewById(R.id.input_phone);
        inputAdminMode = (CheckBox) findViewById(R.id.input_admin_mode);
        inputUserMode = (CheckBox) findViewById(R.id.input_user_mode);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }


    public void signup() {

        UserDTO user = getData(); // 입력된 회원가입 데이터

        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        Call<Map<String, String>> response = restAPI.signUp(user);

        response.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Map<String, String> map = response.body();
                if (map.get("RESULT").equals("SUCCESS")) {
                    Toast.makeText(mContext, getText(R.string.signup_success), Toast.LENGTH_SHORT);
                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (map.get("RESULT").equals("FAIL")) {
                    Toast.makeText(mContext, getText(R.string.sign_fail), Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(mContext, getText(R.string.network_error), Toast.LENGTH_SHORT);
            }
        });


    }

    public UserDTO getData() {
        UserDTO user = new UserDTO();

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String pwd = inputPwd.getText().toString();
        String phone = inputPhone.getText().toString();
        int mode = -1;
        if (inputAdminMode.isChecked()) {
            mode = 0;
        } else {
            mode = 1;
        }

        // 데이터 입력 확인
        if (name.length() == 0) {
            Toast.makeText(JoinActivity.this, getText(R.string.no_input_name), Toast.LENGTH_SHORT).show();
            return null;
        } else if (email.length() == 0) {
            Toast.makeText(getApplicationContext(), getText(R.string.no_input_email), Toast.LENGTH_SHORT).show();
            return null;
        } else if (pwd.length() == 0) {
            Toast.makeText(getApplicationContext(), getText(R.string.no_input_pwd), Toast.LENGTH_SHORT).show();
            return null;
        } else if (phone.length() == 0) {
            Toast.makeText(getApplicationContext(), getText(R.string.no_input_phone), Toast.LENGTH_SHORT).show();
            return null;
        } else if (mode == -1) {
            Toast.makeText(getApplicationContext(), "모드를 선택해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            user.setName(name);
            user.setEmail(email);
            user.setPwd(pwd);
            user.setPhone(phone);
            user.setMode(mode);
        }
        return user;
    }
}



