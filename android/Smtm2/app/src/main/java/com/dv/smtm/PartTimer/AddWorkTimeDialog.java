package com.dv.smtm.PartTimer;

import android.app.Activity;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import com.dv.smtm.Model.DailyVo;
import com.dv.smtm.Model.UserInfo;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.Owner.PTManage.StaffInfo;
import com.dv.smtm.Owner.StoreManage.MainActivity;
import com.dv.smtm.R;

import static com.dv.smtm.Common.CommonData.dailySeq;
import static com.dv.smtm.Common.CommonData.curDailySeq;

import java.util.Calendar;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eunhye on 2016-10-30.
 */
public class AddWorkTimeDialog extends DialogFragment {
    private static final String TAG = "AddWorkTimeDialog";

    Context mContext;

    int userId = StaffInfo.userId;
    int storeId = StaffInfo.storeId;
    int dailySeq = 0;

    Button btStWorkTitme;
    Button btStWorkTimeDir;
    Button btEndWorkTime;
    Button btEndWorkTimeDir;

    public int hour, minute;
    public int status = 0;  // 1 : 출근, 2 : 퇴근

    private DialogInterface.OnDismissListener onDismissListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_pt_worktime, container);
        Log.d(TAG, "dailySeq확인 :" + curDailySeq);
        dailySeq = curDailySeq;

        btStWorkTitme = (Button) view.findViewById(R.id.st_worktime1);
        btStWorkTitme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                String curTime = null;
                switch (c.get(Calendar.AM_PM)) {
                    case 0:
                        curTime = String.format("%02d:%02d", hour, minute);
                        break;
                    case 1:
                        curTime = String.format("%02d:%02d", (hour + 12), minute);
                        break;
                }
                btStWorkTitme.setText(curTime);
            }
        });

        btStWorkTimeDir = (Button) view.findViewById(R.id.st_worktime2);
        btStWorkTimeDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 1;
                new TimePickerDialog(getContext(), timeSetListener, hour, minute, false).show();
            }
        });


        btEndWorkTime = (Button) view.findViewById(R.id.end_worktime1);
        btEndWorkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                String curTime = null;
                switch (c.get(Calendar.AM_PM)) {
                    case 0:
                        curTime = String.format("%02d:%02d", hour, minute);
                        break;
                    case 1:
                        curTime = String.format("%02d:%02d", (hour + 12), minute);
                        break;
                }
                btEndWorkTime.setText(curTime);
            }
        });

        btEndWorkTimeDir = (Button) view.findViewById(R.id.end_worktime2);
        btEndWorkTimeDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 2;
                new TimePickerDialog(getContext(), timeSetListener, hour, minute, false).show();
            }
        });


        Button btComplete = (Button) view.findViewById(R.id.insert_worktime);
        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWorkTime();
            }
        });
        return view;
    }


    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String time = String.format("%02d:%02d", hourOfDay, minute);
            switch (status) {
                case 1:
                    btStWorkTitme.setText(time);
                    break;
                case 2:
                    btEndWorkTime.setText(time);
                    break;
            }
        }
    };


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }


    public void updateWorkTime() {
        DailyVo dailyVo = new DailyVo();
        dailyVo.setStaff_id(UserInfo.staff_id);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);

        if (btStWorkTitme.getText().toString().length() != 0) {
            String[] startTimeData = btStWorkTitme.getText().toString().split(":");
            String startTime = String.format("%d-%d-%d %s:%s:%s", year, month, date, startTimeData[0], startTimeData[1],"00");
            dailyVo.setStart_time(startTime);
            System.out.println("startTime : " + startTime);
        } else {
            String startTime = String.format("%d-%d-%d %s:%s:%s", year, month, date, "00", "00","00");
            dailyVo.setEnd_time(startTime);
        }
        if (btEndWorkTime.getText().toString().length() != 0) {
            String[] endTimeData = btEndWorkTime.getText().toString().split(":");
            String endTime = String.format("%d-%d-%d %s:%s:%s", year, month, date, endTimeData[0], endTimeData[1],"00");
            dailyVo.setEnd_time(endTime);
            System.out.println("startTime : " + endTime);
        } else {
            String endTime = String.format("%d-%d-%d %s:%s:%s", year, month, date, "00", "00","00");
            dailyVo.setEnd_time(endTime);
        }

        RestAPI restAPI = new RestRequestHelper().getRetrofit();

        if(curDailySeq == 0) {      // DB에 근무내역 데이터가없을 때 새로운 데이터 삽입
            Call<Map<String, String>> response = restAPI.insertDaily(dailyVo);
            response.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    Map<String, String> map = response.body();
                    if (map.get("RESULT").equals("SUCCESS")) {
                        Log.d(TAG, "daily insert success");

                    } else if (map.get("RESULT").equals("FAIL")) {
                        Log.d(TAG, "daily insert fail(server error)");
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Log.d(TAG, "daily insert fail(network error)");
                }
            });
        } else {        // DB에 근무내역 데이터가 있을때 update(추후 수정할 것)
            Call<Map<String, String>> response = restAPI.insertDaily(dailyVo);
            response.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    Map<String, String> map = response.body();
                    if (map.get("RESULT").equals("SUCCESS")) {
                        Log.d(TAG, "daily insert success");

                    } else if (map.get("RESULT").equals("FAIL")) {
                        Log.d(TAG, "daily insert fail(server error)");
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Log.d(TAG, "daily insert fail(network error)");
                }
            });
        }

        dismiss();

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


}
