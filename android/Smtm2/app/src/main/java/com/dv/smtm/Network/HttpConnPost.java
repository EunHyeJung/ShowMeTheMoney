package com.dv.smtm.Network;


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eunhye on 2016-08-05.
 */
public class HttpConnPost extends AsyncTask<String, Void, String> {

    String url =  "http://183.111.102.214:8079";
    Handler handler;

    public HttpConnPost(String requestUrl) {
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
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "android");

            byte[] outputInBytes = params[0].getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputInBytes);
            os.close();

            int retCode = conn.getResponseCode(); //

            if (retCode == 200) {

                InputStream is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    JSONObject reader = new JSONObject(line);
                    result = reader.getString("result");
                }
                br.close();
            } else {
                System.out.println(retCode);
            }


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
