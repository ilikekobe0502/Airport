package com.whatmedia.ttia.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.whatmedia.ttia.utility.Preferences;

/**
 * Created by neo_mac on 2017/8/21.
 */

public class FCMService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM", "Token:" + token);
        Preferences.saveFCMToken(getApplicationContext(), token);
    }
}
