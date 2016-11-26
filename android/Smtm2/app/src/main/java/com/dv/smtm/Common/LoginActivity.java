package com.dv.smtm.Common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dv.smtm.Gcm.RegistrationIntentService;
import com.dv.smtm.Model.LoginDTO;
import com.dv.smtm.Model.UserDTO;
import com.dv.smtm.Model.UserInfo;
import com.dv.smtm.Model.UserStaffDTO;
import com.dv.smtm.Owner.PTManage.OwnPTScheduleActivity;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.Owner.StoreManage.OwnStoreManageActivity;
import com.dv.smtm.PartTimer.PTSchduleActivity;
import com.dv.smtm.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private Context mContext;
    SharedPreferenceClass sharedPreference;


    TextView goToJoin, join, forget;

    EditText userID;
    EditText userPW;

    // Button join;

    /**
     * Instance ID를 이용하여 디바이스 토큰을 가져오는 RegistratrionIntentService
     */
    public void getInstanceIdToken() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mContext = getApplicationContext();

        goToJoin = (TextView) findViewById(R.id.goToJoin);
        join = (TextView) findViewById(R.id.login);
        forget = (TextView) findViewById(R.id.forgot);

        userID = (EditText) findViewById(R.id.userID);
        userPW = (EditText) findViewById(R.id.userPW);


        // for GCM
        //registBroadcastReceiver();
        sharedPreference = new SharedPreferenceClass(this);
        getInstanceIdToken();


        goToJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //임시
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OwnPTScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login() {
        LoginDTO user = getData();

        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        Call<Map<String, UserDTO>> response = restAPI.authentication(user);
        response.enqueue(new Callback<Map<String, UserDTO>>() {
            @Override
            public void onResponse(Call<Map<String, UserDTO>> call, Response<Map<String, UserDTO>> response) {
                Map<String, UserDTO> map = response.body();
                if (map.get("result") != null) {
                    setUserInfo(map.get("result"));
                } else {
                    Toast.makeText(getApplicationContext(), getText(R.string.login_fail), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, UserDTO>> call, Throwable t) {
                Toast.makeText(mContext, getText(R.string.network_error), Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.toString());
            }
        });
    }

    public void setUserInfo(UserDTO userInfo) {
        Log.d(TAG, userInfo.toString());
        UserInfo.user_id = userInfo.getUser_id();
        UserInfo.phone = userInfo.getPhone();
        UserInfo.email = userInfo.getEmail();
        UserInfo.mode = userInfo.getMode();
        UserInfo.username = userInfo.getName();

        switch (UserInfo.mode) {
            case 0:
                Intent intent = new Intent(LoginActivity.this, OwnStoreManageActivity.class);
                startActivity(intent);
                break;
            case 1:
                UserInfo.staff_id = userInfo.getUserStaffs().get(0).getStaff_id();
                intent = new Intent(LoginActivity.this, PTSchduleActivity.class);
                startActivity(intent);
                break;
        }


        Log.d("UserInfo", UserInfo.phone + ' ' + UserInfo.pwd + ' ' + UserInfo.user_id + ' ' + UserInfo.email + ' ' + UserInfo.mode + ' ' + UserInfo.username);

    }


    public LoginDTO getData() {
        LoginDTO loginInfo = new LoginDTO();
        String email = userID.getText().toString();
        String pwd = userPW.getText().toString();
        String deviceId = sharedPreference.getValue("instanceID", "empty");

        // 데이터 입력 확인
        if (email.length() == 0) {
            Toast.makeText(mContext, getText(R.string.no_input_email), Toast.LENGTH_SHORT).show();
            return null;
        } else if (pwd.length() == 0) {
            Toast.makeText(mContext, getText(R.string.no_input_pwd), Toast.LENGTH_SHORT).show();
            return null;
        }

        loginInfo.setEmail(email);
        loginInfo.setPwd(pwd);
        loginInfo.setDevice_id(deviceId);
        return loginInfo;
    }


    /**
     * Google Play Service를 사용할 수 있는 환경이지를 체크한다.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}