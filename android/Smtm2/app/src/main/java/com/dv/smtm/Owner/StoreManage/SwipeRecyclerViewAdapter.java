package com.dv.smtm.Owner.StoreManage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import com.dv.smtm.Model.StoreDTO;
import com.dv.smtm.Model.UserInfo;

import com.dv.smtm.Network.RestAPI;
import com.dv.smtm.Network.RestRequestHelper;
import com.dv.smtm.Owner.PTManage.OwnPTManageActivity;
import com.dv.smtm.R;


import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2016-07-24.
 */
public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<StoreDTO> storeList;

    RestAPI restAPI;

    String storeName;


    public SwipeRecyclerViewAdapter(Context context, ArrayList<StoreDTO> objects) {
        this.mContext = context;
        this.storeList = objects;

        restAPI = new RestRequestHelper().getRetrofit();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_list_own_store_manage, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        final StoreDTO item = storeList.get(position);

        viewHolder.tvStoreName.setText(item.getStore_name());  // 상점 이름 set
        viewHolder.tvStoreStaffCount.setText(item.getCnt_staffs()+"명");
        viewHolder.tvStoreId.setText(Integer.toString(item.getStore_id())); // 상점 id set

         viewHolder.tvStoreId.setVisibility(View.INVISIBLE);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // 오른쪽 드래그
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.btnWrapper));


        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent = new Intent()
                Intent intent = new Intent(mContext, OwnPTManageActivity.class);
                intent.putExtra("storeId", item.getStore_id());
                intent.putExtra("storeName", item.getStore_name());
                mContext.startActivity(intent);
            }
        });

        // 상점 삭제
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                storeList.get(position);
                storeList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, storeList.size());
                mItemManger.closeAllItems();
                deleteData(viewHolder.tvStoreStaffCount.getText().toString());
            }
        });


        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    /* 서버에 상점 삭제를 요청하는 메소드 */
    public void deleteData(String storeId) {
        Call<Map<String, String>> response = restAPI.deleteStore(Integer.parseInt(storeId));
        response.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Map<String, String> map = response.body();
                Log.d(TAG, map.get("RESULT"));
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvStoreName;
        TextView tvStoreStaffCount;
        TextView tvDelete;
        TextView tvStoreId;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvStoreName = (TextView) itemView.findViewById(R.id.tvStoreName);
            tvStoreStaffCount = (TextView) itemView.findViewById(R.id.tvStaffCount);
            tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
            tvStoreId = (TextView) itemView.findViewById(R.id.tvStoreId);
        }
    }


    /* 상점 다이얼로그를 띄우는 메소드 */
    public void showDialog() {
        final EditText etEdit = new EditText(mContext);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(mContext.getText(R.string.add_store));
        dialog.setView(etEdit);

        dialog.setPositiveButton(mContext.getText(R.string.button_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeName = etEdit.getText().toString();
                if (storeName.length() == 0) {
                    Toast.makeText(mContext, mContext.getString(R.string.add_store_error), Toast.LENGTH_SHORT).show();
                }
                insertStore(storeName);
            }
        });
        dialog.setNegativeButton(mContext.getText(R.string.button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /* 서버로 새로운 상점 삽입을 요청하는 메소드 */
    public void insertStore(String storeName) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setUser_id(UserInfo.user_id);
        storeDTO.setStore_name(storeName);

        Call<Map<String, String>> response = restAPI.createStore(storeDTO);
        response.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Log.d(TAG, response.body().toString());

                Map<String, String> map = response.body();
                if (map.get("RESULT").contains("SUCCESS")) {
                    updateInsertedList(map.get("RESULT"));
                } else if (map.get("RESULT").equals("FAIL")) {

                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.d(TAG, t.toString());
                //Toast.makeText(mContext, getText(R.string.network_error), Toast.LENGTH_SHORT);
            }
        });
    }

    /* 리스트 아이템에 새로운 상점에 대한 정보를 업데이트 */
    public void updateInsertedList(String response) {
        int storeId = Integer.parseInt(response.split("/")[1]);
        Log.d(TAG, "updateIntertList called / storeId : " + storeId);
        StoreDTO dto = new StoreDTO();
        dto.setStore_name(storeName);
        dto.setStore_id(storeId);
        storeList.add(dto);
        notifyDataSetChanged();

    }


}
