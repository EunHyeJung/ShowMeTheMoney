package com.dv.smtm.Owner.StoreManage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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


import com.dv.smtm.Model.StoreDTO;

import com.dv.smtm.Model.UserInfo;


import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.R;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnStoreManageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "OwnStoreManageActivity";
    private ArrayList<StoreDTO> mStoreData;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private SwipeRecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_store_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(Color.parseColor("#DBDBDB"));
        setSupportActionBar(toolbar);

        FloatingActionButton btAddStore = (FloatingActionButton) findViewById(R.id.add_store);
        btAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.showDialog();
            }

        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView userId = (TextView) findViewById(R.id.tvUserName);
        userId.setText(UserInfo.username);
        userId.append("님의");
        //content 추가부분
        tvEmptyView = (TextView) findViewById(R.id.tvEmptyView);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStoreData = new ArrayList<StoreDTO>();

        loadData();
    }


    /* 사용자가 보유한 상점 리스트를 받아오는 메소드 */
    public void loadData() {
        Log.d(TAG, "user_id " + UserInfo.user_id);

        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        Call<List<StoreDTO>> response = restAPI.getStoreList(UserInfo.user_id);
        response.enqueue(new Callback<List<StoreDTO>>() {
            @Override
            public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                Log.d(TAG, "response code : " + response.code());
                List<StoreDTO> storeList = response.body();
                Log.d(TAG, storeList.toString());
                setStoreList(storeList);
            }

            @Override
            public void onFailure(Call<List<StoreDTO>> call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "상점 리스트를 불러올 수 없음", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setStoreList(List<StoreDTO> storeList) {

            for (int i = 0; i < storeList.size(); i++) {
                mStoreData.add(storeList.get(i));
            }

            if (mStoreData.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                Log.d("test", "no data");
                tvEmptyView.setVisibility(View.VISIBLE);

            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                tvEmptyView.setVisibility(View.GONE);
                Log.d("test", "data");
            }

            setAdapter(mStoreData);

    }


    public void setAdapter(ArrayList<StoreDTO> mStoreData) {
        mAdapter = new SwipeRecyclerViewAdapter(this, mStoreData);
        // 동시 슬라이드 가능하게 하는 기능
        //((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);
    }


/*    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);


        } else {
            super.onBackPressed();
        }
    }*/


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

        if (android.R.id.home == item.getItemId()) {
            Activity activity = this;
            activity.onBackPressed();
            Log.d(TAG, "back button click ? ");
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


}
