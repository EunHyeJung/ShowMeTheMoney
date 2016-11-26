package com.dv.smtm.Network;




import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eunhye on 2016-09-07.
 */
public class RestRequestHelper {

    private static final String API_URL = "http://183.111.102.214:8079/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public  RestAPI getRetrofit() {
        return this.retrofit.create(RestAPI.class);
    }
}
