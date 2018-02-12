package com.whatsmedia.ttia.services;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetRegisterUserResponse;
import com.whatsmedia.ttia.newresponse.data.RegisterUserData;
import com.whatsmedia.ttia.page.main.MainActivity;
import com.whatsmedia.ttia.utility.Preferences;
import com.whatsmedia.ttia.utility.Util;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/8/21.
 */

public class FCMTokenService extends FirebaseInstanceIdService {
    private final static String TAG = FCMTokenService.class.getSimpleName();

    private static final int TAG_DEFAULT_LANGUAGE = 1; // 預設語系為中文

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM", "Token:" + token);
        Preferences.saveFCMToken(getApplicationContext(), token);
        registerUser();
    }

    private int mApiFailureCount = 0;

    /**
     * 註冊User
     */
    private void registerUser() {
        Log.d(TAG, "registerUser");
        if (mApiFailureCount < 5) {

            final RegisterUserData data = new RegisterUserData();
            GetRegisterUserResponse response = new GetRegisterUserResponse();

            data.setDeviceID(Util.getDeviceId(getApplicationContext()));
            if (!TextUtils.isEmpty(Preferences.getFCMToken(getApplicationContext()))) {
                data.setPushToken(Preferences.getFCMToken(getApplicationContext()));
            } else {
                return;
            }
            data.setLangId(TAG_DEFAULT_LANGUAGE);

            response.setData(data);
            String json = response.getJson();
            NewApiConnect.getInstance(getApplicationContext()).registerUser(json, new NewApiConnect.MyCallback() {
                @Override
                public void onFailure(Call call, IOException e, int status) {
                    Log.d(TAG, "RegisterUser onFailure");
                    mApiFailureCount++;
                    registerUser();
                }

                @Override
                public void onResponse(Call call, String response) throws IOException {
                    Log.d(TAG, "RegisterUser Success");
                    mApiFailureCount = 0;

                    Preferences.saveDeviceID(getApplicationContext(), data.getDeviceID());

                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(i);
                }
            });
        }
    }
}
