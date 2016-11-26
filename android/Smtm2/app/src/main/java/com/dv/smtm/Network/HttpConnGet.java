package com.dv.smtm.Network;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eunhye on 2016-08-07.
 */
public class HttpConnGet extends AsyncTask<String, Void, String> {

    String url = "http://183.111.102.214:8079";
    Handler handler;

    public HttpConnGet(String requestUrl) {
        this.url += requestUrl;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public String doInBackground(String... params) {
        String result = null;
        try {

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            // 연결 설정
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");


            InputStream stream = new BufferedInputStream(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            // JSONObject topLevel = new JSONObject(builder.toString());

            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }


    @Override
    protected void onPostExecute(String result) {
        // 액티비티로 결과 메시지 전달
        if (handler != null) {
            Message message = new Message();
            message.obj = result;
            handler.sendMessage(message);
        }
    }

}
