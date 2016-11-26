package com.dv.smtm.Owner.PTManage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dv.smtm.Model.UserDTO;
import com.dv.smtm.Network.HttpConnGet;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.R;


import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eunhye on 2016-08-22.
 */
public class SearchStaffDialog extends DialogFragment {
    private static final String TAG = "SearchStaffDialog";

    EditText etSerachInput;
    Button btnSsearch;
    LinearLayout resultContent;


    TextView tvResultUserId;
    TextView tvResultEmail;
    TextView tvResultName;
    TextView tvResultPhone;
    Button btnAdd;

    RadioButton rbSearchEmail;
    RadioButton rbSearchPhone;

    RestAPI restAPI;

    private DialogInterface.OnDismissListener onDismissListener;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_own_staff_search, container);

        restAPI = new RestRequestHelper().getRetrofit();

        etSerachInput = (EditText) view.findViewById(R.id.search_input);
        btnSsearch = (Button) view.findViewById(R.id.search);
        resultContent = (LinearLayout) view.findViewById(R.id.result_content);
        tvResultUserId = (TextView) view.findViewById(R.id.result_user_id);
        tvResultName = (TextView) view.findViewById(R.id.result_name);
        tvResultEmail = (TextView) view.findViewById(R.id.result_email);
        tvResultPhone = (TextView) view.findViewById(R.id.result_phone);
        btnAdd = (Button) view.findViewById(R.id.add);

        rbSearchEmail = (RadioButton) view.findViewById(R.id.search_email);
        rbSearchPhone = (RadioButton) view.findViewById(R.id.search_phone);

        btnSsearch.setOnClickListener(clickListener);
        btnAdd.setOnClickListener(clickListener);

        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.search:
                    searchStaff();
                    break;
                case R.id.add:
                    addStaff();
                    break;
            }
        }
    };

    public void searchStaff() {
        String input = etSerachInput.getText().toString();
        if (input.length() == 0) {
            //  Toast.makeText(mContext, "검색어를 입력하세요.", Toast.LENGTH_SHORT);
            return;
        }

        Call<Map<String, UserDTO>> response = null;
        if (rbSearchEmail.isChecked()) {
            Log.d(TAG, "이메일 검색 클릭");
            rbSearchPhone.setChecked(false);
            etSerachInput.setHint("예) abc@naver.com");
            response = restAPI.searchByEmail(input);
        } else if (rbSearchPhone.isChecked()) {
            Log.d(TAG, "연락처 검색 클릭");
            rbSearchEmail.setChecked(false);
            etSerachInput.setHint("예) 010-1234-5678");
            response = restAPI.searchByPhone(input);
        } else {
            return;
        }

        response.enqueue(new Callback<Map<String, UserDTO>>() {
            @Override
            public void onResponse(Call<Map<String, UserDTO>> call, Response<Map<String, UserDTO>> response) {
                Map<String, UserDTO> map = response.body();
                UserDTO staffInfo = map.get("RESULT");
                if(staffInfo != null) {
                    dataParsing(map.get("RESULT"));
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "등록되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Map<String, UserDTO>> call, Throwable t) {

            }
        });

    }

    public void dataParsing(UserDTO userInfo) {

        int userId = userInfo.getUser_id();

        String name = userInfo.getName();
        String email = userInfo.getEmail();
        String phone = userInfo.getPhone();
        Log.d(TAG, "parsing name : " + name);
        resultContent.setVisibility(View.VISIBLE);
        tvResultUserId.setText(userId + "");
        System.out.println("userId Check : " + tvResultUserId.getText().toString());
        tvResultName.setText(name);
        tvResultEmail.setText(email);
        tvResultPhone.setText(phone);

        StaffInfo.userId = userId;
        StaffInfo.staffName = name;
        StaffInfo.staffPhone = phone;
        StaffInfo.staffEmail = email;

    }

    public void addStaff() {
        Log.d(TAG, "addStaff() called");

        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddStaffDialog dialogFragment = new AddStaffDialog();
        dialogFragment.show(fm, "fragment_dialog_test");
        dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismiss();
            }
        });
    }


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

}
