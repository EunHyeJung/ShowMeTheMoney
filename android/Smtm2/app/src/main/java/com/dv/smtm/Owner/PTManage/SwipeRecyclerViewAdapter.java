package com.dv.smtm.Owner.PTManage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.dv.smtm.Model.StaffDTO;
import com.dv.smtm.Model.StoreDTO;
import com.dv.smtm.Model.UserInfo;
import com.dv.smtm.Network.HttpConnPost;
import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2016-07-24.
 */
public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {


    private static final String TAG = "StaffAdapter";


    private Context mContext;
    private ArrayList<StaffDTO> staffList;
    private int status;

    String staff_name;

    // HttpConnHelper로부터 결과메시지를 받는 핸들러
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String value = (String) msg.obj;
            Log.d("OwnPTManageActivity", "value : " + value);
            if (value.equals("1")) {
                Toast.makeText(mContext, staff_name + " 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            } else { // 정보 받아오기 실패시 or 정보 없음
                Log.d("test", value);
            }
        }
    };

    public SwipeRecyclerViewAdapter(Context context, ArrayList<StaffDTO> objects) {
        this.mContext = context;
        this.staffList = objects;

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_list_own_pt_manage, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        final StaffDTO item = staffList.get(position);

        viewHolder.tvStaffName.setText(item.getName());
        viewHolder.tvStaffHourlyWage.setText(Integer.toString(item.getHourly_wage()));
        viewHolder.tvStaffId.setText(Integer.toString(item.getStaff_id()));
        viewHolder.tvStaffId.setVisibility(View.INVISIBLE);

        Log.d("test", item.getName());


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // 오른쪽 드래그
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.btnWrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "item.getStaffId() : "+item.getStaff_id());
                StaffInfo.staffId = item.getStaff_id();
                Intent intent = new Intent(mContext, OwnPTScheduleActivity.class);
                intent.putExtra("staffId", item.getStaff_id());
                intent.putExtra("staffName", item.getName());
                intent.putExtra("hourlyWage", item.getHourly_wage());
                Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getHourly_wage() + " \n" + item.getStaff_id(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });

        //스태프삭제
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                staffList.get(position);
                staffList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, staffList.size());
                mItemManger.closeAllItems();
                deleteStaff(viewHolder.tvStaffName.getText().toString(), viewHolder.tvStaffId.getText().toString());
            }
        });


        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    public void deleteStaff(String staffName, String staffId) {
        Log.d(TAG, "staffId : " + staffId);
        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        Call<Map<String, String>> response = restAPI.deleteStaff(Integer.parseInt(staffId));
        response.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Map<String, String> result = response.body();
                String message = result.get("RESULT");
                if(message.equals("SUCCESS")){
                    Toast.makeText(mContext, "아르바이트생 정보 삭제 완료", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(mContext, "아르바이트생 정보 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvStaffName;
        TextView tvStaffId;
        TextView tvStaffHourlyWage;
        TextView tvDelete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

            tvStaffName = (TextView) itemView.findViewById(R.id.tvStaffName);
            tvStaffId = (TextView) itemView.findViewById(R.id.tvStaffId);
            tvStaffHourlyWage = (TextView) itemView.findViewById(R.id.tvStaffHourlyWage);
            tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
        }
    }
}
