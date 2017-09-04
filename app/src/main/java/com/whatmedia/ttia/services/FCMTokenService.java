package com.whatmedia.ttia.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.utility.Preferences;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/8/21.
 */

public class FCMTokenService extends FirebaseInstanceIdService {
    private final static String TAG = FCMTokenService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM", "Token:" + token);
        Preferences.saveFCMToken(getApplicationContext(), token);
        if (!Preferences.getUserFirstInit(getApplicationContext()))
            registerUser();
    }

    private int mApiFailureCount = 0;

    /**
     * 註冊User
     */
    private void registerUser() {
        if (mApiFailureCount < 5) {
            ApiConnect.getInstance(getApplicationContext())
                    .registerUser("A123456", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            mApiFailureCount++;
                            Log.d(TAG, "RegisterUser onFailure");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d(TAG, "RegisterUser Success");

                            Preferences.saveUserFirstInit(getApplicationContext(), true);
                        }
                    });
        }
    }
}
