package com.dv.smtm.Owner.PTManage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dv.smtm.Model.StaffDTO;

import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by eunhye on 2016-08-24.
 */
public class AddStaffDialog extends DialogFragment {
    private static final String TAG = "AddStaffDialog";

    Context mContext;

    int userId = StaffInfo.userId;
    int storeId = StaffInfo.storeId;


    EditText etHourlyWage;


    private DialogInterface.OnDismissListener onDismissListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_own_staff_add, container);

        TextView tvStaffName = (TextView) view.findViewById(R.id.add_staff_name);
        tvStaffName.setText(StaffInfo.staffName);

        TextView tvStaffEmail = (TextView) view.findViewById(R.id.add_staff_email);
        tvStaffEmail.setText(StaffInfo.staffEmail);

        TextView tvStaffPhone = (TextView) view.findViewById(R.id.add_staff_phone);
        tvStaffPhone.setText(StaffInfo.staffPhone);

        etHourlyWage = (EditText) view.findViewById(R.id.input_hourly_wage);

        Button btComplete = (Button) view.findViewById(R.id.complete);
        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewStaff();
            }
        });
        return view;
    }


    public void addNewStaff() {
        StaffDTO staffInfo = new StaffDTO(storeId, userId);
        int hourlyWage = Integer.parseInt(etHourlyWage.getText().toString());

        staffInfo.setHourly_wage(hourlyWage);


        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        Call<Map<String, String>> response = restAPI.createStaff(staffInfo);
        response.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Log.d(TAG, response.body().toString());

                Map<String, String> map = response.body();
                if (map.get("RESULT").contains("SUCCESS")) {
                    updateStaffList(map.get("RESULT"));
                } else if (map.get("RESULT").equals("FAIL")) {

                }
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

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

    public void updateStaffList(String value) {
        int staffId = Integer.parseInt(value.split("/")[1]);
        System.out.println("staffId : "+staffId);
        System.out.println("user ID : "+StaffInfo.userId);
        Log.d(TAG, "스태프 추가 완료 ");
        Log.d(TAG, "userId : "+staffId);
      //  Toast.makeText(mContext, "추가 완료", Toast.LENGTH_SHORT).show();
        dismiss();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


}
