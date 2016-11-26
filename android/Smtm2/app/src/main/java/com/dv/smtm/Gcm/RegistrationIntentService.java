package com.dv.smtm.Gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dv.smtm.Common.SharedPreferenceClass;
import com.dv.smtm.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by eunhye on 2016-08-12.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegistrationIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }


    // GCM을 위한 Instance ID를 생성하고 가져옴
    @Override
    protected void onHandleIntent(Intent intent) {
        /*GCM Instance ID의 토큰을 가져오는 작업이 시작되면
        LocalBoardcast로 GENERATING 액션을 알려 ProgressBar가 동작하도록 한다.*/
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(new Intent(QuickstartPreferences.REGISTRATION_GENERATING));

        // GCM을 위한 Instance ID를 가져온다.
        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        try {
            synchronized (TAG) {
                String defaultSenderId = getString(R.string.gcm_defaultSenderId);
                String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
                token = instanceID.getToken(defaultSenderId, scope, null);
                SharedPreferenceClass.putValue("instanceID", token);
                //Log.i(TAG, "GCM Registration Token: " + token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // GCM Instance ID에 해당하는 토큰을 획득하면 LocalBroadcast에 COMPLETE 액션을 알린다.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }
}
