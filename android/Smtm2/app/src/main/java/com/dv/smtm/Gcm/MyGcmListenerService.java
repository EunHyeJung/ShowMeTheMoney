package com.dv.smtm.Gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dv.smtm.Common.LoginActivity;
import com.dv.smtm.Common.SharedPreferenceClass;
import com.dv.smtm.R;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * GCM으로 메시지가 도착하면 디바이스에 받은 메시지를 어떻게 사용할지에 대해 정의한 클래스
 */
public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListner";


    /**
     * @param from SenderID 값을 받아온다.
     * @param data Set형태로 GCM으로 받은 데이터
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String title = data.getString("title");
        String message = data.getString("message");

        Log.d(TAG, "From : " + from);
        Log.d(TAG, "Title : " + title);
        Log.d(TAG, "Message : " + message);


        // GCM으로 받은 메시지를 디바이스에 알려주는 sendNotification()을 호출한다.
        sendNotification(title, message);
    }


    /**
     * 실제 디바이스에 GCM으로 받은 메시지를 알려주는 함수
     * 디바이스 Notification Center에 나타난다.
     * @param title
     * @param message
     */
    private void sendNotification(String title, String message) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_messege)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        if(message.contains("아르바이트생추가요청")) {
            SharedPreferenceClass sharedPreference = new SharedPreferenceClass(this);
            int staffId = Integer.parseInt(message.split("/")[1]);
            sharedPreference.putValue("requestAddStaff", staffId);

        }

    }
}
