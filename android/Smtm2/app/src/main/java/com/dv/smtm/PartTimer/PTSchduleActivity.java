package com.dv.smtm.PartTimer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.dv.smtm.Common.CalendarView;
import com.dv.smtm.Model.DailyDTO;
import com.dv.smtm.Model.DailyVo;
import com.dv.smtm.Model.UserInfo;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.Owner.PTManage.StaffInfo;
import com.dv.smtm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PTSchduleActivity extends AppCompatActivity {

    private static final String TAG = "PTSchduleActivity";

    public List<DailyDTO> dailyDTOList = new ArrayList<DailyDTO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pt_schdule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#DBDBDB"));

        ///////////////
        loadData();
        ////////////////////////////////////////////////
    }


    public void loadData() {
        Calendar c = Calendar.getInstance();
        String year = Integer.toString(c.get(Calendar.YEAR));
        String month = Integer.toString((c.get(Calendar.MONTH) + 1));

        if (month.length() < 2) {
            month = "0" + month;
        }

        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        String requestMonth = year + month;
        Log.d(TAG, "requestMonth : " + requestMonth);
        Call<List<DailyVo>> response = restAPI.getDailyTimeList(UserInfo.staff_id, requestMonth);  // staff_id, start_time
        response.enqueue(new Callback<List<DailyVo>>() {
            @Override
            public void onResponse(Call<List<DailyVo>> call, Response<List<DailyVo>> response) {
                List<DailyVo> dailyVOs = response.body();
                if (dailyVOs != null) {
                    for (DailyVo vo : dailyVOs) {
                        DailyDTO dto = new DailyDTO(vo.getDaily_seq(), vo.getStart_time(), vo.getEnd_time());
                        dailyDTOList.add(dto);
                    }
                }
                HashSet<Date> events = new HashSet<>();
                events.add(new Date());

                CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view2));
                cv.updateCalendar(events, dailyDTOList);

                // assign event handler
                cv.setEventHandler(new CalendarView.EventHandler() {
                    @Override
                    public void onDayLongPress(Date date) {
                        // show returned day
                        DateFormat df = SimpleDateFormat.getDateInstance();
                        Toast.makeText(getApplicationContext(), df.format(date), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<DailyVo>> call, Throwable t) {
                Log.d(TAG, "getData failed");
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.own_ptschdule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(PTSchduleActivity.this, "setting 버튼 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_messege) {
            FragmentManager fm = getSupportFragmentManager();
            RequestMessageDialog dialogFragment = new RequestMessageDialog();
            dialogFragment.show(fm, "fragment_dialog_test");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
