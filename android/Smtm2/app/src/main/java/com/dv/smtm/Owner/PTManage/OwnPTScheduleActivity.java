package com.dv.smtm.Owner.PTManage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
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
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
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

public class OwnPTScheduleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "OwnPTScheduleActivity";

    public List<DailyDTO> dailyDTOList = new ArrayList<DailyDTO>();
    private List<DailyDTO> mDailyData;

    int staffId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_pt_schdule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#DBDBDB"));
        setSupportActionBar(toolbar);

        //////////////////////////////////////
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(getApplicationContext(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        staffId = intent.getIntExtra("staffId", 0);
        String staffName = intent.getStringExtra("staffName");
        int hourlyWage = intent.getIntExtra("hourlyWage", 0);

        loadData();
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

        Log.d(TAG, "Request Month  : " + requestMonth); // 201610

        Log.d(TAG, "Request info StaffId : " + StaffInfo.staffId);
        Call<List<DailyVo>> response = restAPI.getDailyTimeList(StaffInfo.staffId, requestMonth);  // staff_id, start_time

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
                Log.d(TAG, "dailyDTOList size : " + dailyDTOList.size());
                CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
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

    public void getData(List<DailyDTO> data) {


    }

    public void setUserInfo(int staffId, String staffName, int hourlyWage) {
        //   tvHourlyWage.setText(Integer.toString(hourlyWage));
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*  일정  */
    public void showSchedule() {

    }
}
