package com.dv.smtm.Network;

import com.dv.smtm.Model.DailyVo;
import com.dv.smtm.Model.LoginDTO;
import com.dv.smtm.Model.MessageDTO;
import com.dv.smtm.Model.StaffDTO;
import com.dv.smtm.Model.StoreDTO;
import com.dv.smtm.Model.UserDTO;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RestAPI {
    @POST("/users/create")
    Call<Map<String, String>> signUp(@Body UserDTO userDTO);

    @Headers({"User-Agent: android"})
    @POST("/users/authentication")
    Call<Map<String, UserDTO>> authentication(@Body LoginDTO loginDTO);

    // 상점 관련 API
    @GET("/stores/list")
    Call<List<StoreDTO>> getStoreList(@Query("user_id") int user_id);
    @POST("/stores/create")
    Call<Map<String, String>> createStore(@Body StoreDTO storeDTO);
    @POST("/stores/delete")
    Call<Map<String, String>> deleteStore(@Query("store_id") int store_id);

    // 스태프 관련 API
    @GET("/staffs/searchPhone")
    Call<Map<String, UserDTO>> searchByPhone(@Query("phone") String phone);
    @GET("/staffs/searchEmail")
    Call<Map<String, UserDTO>> searchByEmail(@Query("email") String email);
    @GET("/staffs/list")
    Call<List<StaffDTO>> getStaffList(@Query("store_id") int store_id);
    @POST("/staffs/create")
    Call<Map<String, String>> createStaff(@Body StaffDTO staffDTO);
    @GET("/staffs/readTempStaff")
    Call<Map<String, List<MessageDTO>>> readTempStaffInfo(@Query("user_id") int user_id);
    @POST("/staffs/updateStatus")
    Call<Map<String, String>> updateStaffStaus(@Query("staff_id") int staff_id);
    @POST("/staffs/delete")
    Call<Map<String, String>> deleteStaff(@Query("staff_id") int staff_id);

    // 스케줄관리 API
    @GET("/checkdailytimelist")
    Call<List<DailyVo>> getDailyTimeList(@Query("staff_id") int staff_id, @Query("start_time") String start_time);
    @POST("/insertDaily")
    Call<Map<String, String>> insertDaily(@Body DailyVo dailyVo);
}
