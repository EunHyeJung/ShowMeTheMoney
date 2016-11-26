package com.dv.smtm.PartTimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.dv.smtm.Model.MessageDTO;
import com.dv.smtm.Model.UserInfo;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.R;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eunhye on 2016-09-03.
 */
public class RequestMessageDialog extends DialogFragment {
    private static final String TAG = "RequestMessageDialog";
    TextView tvStoreName;
    TextView tvHourlyWage;
    TextView tvGrade;

    int staffId;
    RestAPI restAPI = new RestRequestHelper().getRetrofit();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_request_message, container);


        loadData();

        tvStoreName = (TextView) view.findViewById(R.id.store_name);
        tvHourlyWage = (TextView) view.findViewById(R.id.hourly_wage2);
        tvGrade = (TextView) view.findViewById(R.id.grade2);

        Button approval = (Button) view.findViewById(R.id.approval);
        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestApproval();
            }
        });

        Button refusual = (Button) view.findViewById(R.id.refusal);
        refusual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRefusual();
            }
        });


        return view;
    }

    public void loadData() {
        Call<Map<String, List<MessageDTO>>> response = restAPI.readTempStaffInfo(UserInfo.user_id);
        response.enqueue(new Callback<Map<String, List<MessageDTO>>>() {
            @Override
            public void onResponse(Call<Map<String, List<MessageDTO>>> call, Response<Map<String, List<MessageDTO>>> response) {
                Map<String, List<MessageDTO>> map = response.body();
                List<MessageDTO> messageList = map.get("result");
                //Log.d(TAG, map.get())
            }

            @Override
            public void onFailure(Call<Map<String, List<MessageDTO>>> call, Throwable t) {

            }
        });


        Bundle extra = getArguments();
        tvStoreName.setText(extra.getString("store_name"));
        tvHourlyWage.setText(extra.getInt("hourly_wage") + "원");

        switch (extra.getInt("grade")) {
            case 0:
                tvGrade.setText("알바생");
                break;
            case 2:
                tvGrade.setText("매니저");
        }
    }


    public void requestApproval() {
        Call<Map<String, String>> response = restAPI.updateStaffStaus(staffId);
        response.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Map<String, String> map = response.body();
                if (map.get("RESULT").equals("SUCCESS")) {
                    Log.d(TAG, "승인 성공");
                    dismiss();
                } else if (map.get("RESULT").equals("FAIL")) {
                    Log.d(TAG, "승인 실패");
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.d(TAG, t.toString());

            }
        });
    }

    public void requestRefusual() {
        Call<Map<String, String>> response = restAPI.deleteStaff(staffId);
        response.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Map<String, String> map = response.body();
                if (map.get("RESULT").equals("SUCCESS")) {
                    Log.d(TAG, "승인 성공");
                    dismiss();
                } else if (map.get("RESULT").equals("FAIL")) {
                    Log.d(TAG, "승인 실패");
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });
    }
}
