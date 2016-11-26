package com.dv.smtm.Owner.PTManage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dv.smtm.Model.StaffDTO;

import com.dv.smtm.Model.UserInfo;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnPTManageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "OwnPTManageActivity";

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_pt_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        int storeId = intent.getIntExtra("storeId", 0);

        toolbar.setTitleTextColor(Color.parseColor("#DBDBDB"));
        setSupportActionBar(toolbar);

        FloatingActionButton btAddStaff = (FloatingActionButton) findViewById(R.id.add_staff);

        btAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStaff();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        TextView storeName = (TextView) findViewById(R.id.tvTitleStoreName);
        storeName.setText(intent.getStringExtra("storeName"));

        //content 추가부분

        tvEmptyView = (TextView) findViewById(R.id.tvEmptyView);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadData(storeId);
    }

    // 서버로부터 스태프 목록을 얻어오는 메소드
    public void loadData(int storeId) {
        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        Call<List<StaffDTO>> response = restAPI.getStaffList(storeId);
        response.enqueue(new Callback<List<StaffDTO>>() {
            @Override
            public void onResponse(Call<List<StaffDTO>> call, Response<List<StaffDTO>> response) {
                Log.d(TAG, "response code : " + response.code());
                List<StaffDTO> staffList = response.body();
                setStaffList(staffList);
            }

            @Override
            public void onFailure(Call<List<StaffDTO>> call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "알바생 리스트를 불러올 수 없음", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setStaffList(List<StaffDTO> staffList) {
        ArrayList<StaffDTO> staffData = new ArrayList<StaffDTO>();


        for (StaffDTO staffInfo : staffList) {
            /*if (staffInfo.getStatus() == 1) {
                staffData.add(staffInfo);
            }*/
            staffData.add(staffInfo);
        }

        if (staffData.isEmpty() && staffData.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            Log.d("test", "no data");
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
            Log.d("test", "data");
        }


        // Creating Adapter object
        SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, staffData);
        mRecyclerView.setAdapter(mAdapter);


    }

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
        getMenuInflater().inflate(R.menu.own_store_manage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.ownStoreManageSettingMenu) {
            Intent intent1 = new Intent(getApplicationContext(), com.dv.smtm.Owner.StoreManage.OwnStoreSettingActivity.class);
            startActivity(intent1);
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


    /* 새로운 스태프 추가 메소드 */
    public void addStaff() {
        Intent intent = getIntent();
        int storeId = intent.getIntExtra("storeId", 0);
        StaffInfo.storeId = storeId;
        FragmentManager fm = getSupportFragmentManager();
        SearchStaffDialog dialogFragment = new SearchStaffDialog();
        dialogFragment.show(fm, "fragment_dialog_test");
        dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 스태프 추가 후 액티비티 재시작
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });


    }
}
