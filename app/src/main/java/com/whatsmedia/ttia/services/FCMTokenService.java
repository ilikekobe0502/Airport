package com.whatsmedia.ttia.services;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetRegisterUserResponse;
import com.whatsmedia.ttia.newresponse.data.RegisterUserData;
import com.whatsmedia.ttia.utility.Preferences;

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
        if (mApiFailureCount < 5) {

            RegisterUserData data = new RegisterUserData();
            GetRegisterUserResponse response = new GetRegisterUserResponse();

            if (!TextUtils.isEmpty(NewApiConnect.getDeviceID())) {
                data.setDeviceID(NewApiConnect.getDeviceID());
            } else {
                return;
            }
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
                    mApiFailureCount++;
                    Log.d(TAG, "RegisterUser onFailure");
                }

                @Override
                public void onResponse(Call call, String response) throws IOException {
                    Log.d(TAG, "RegisterUser Success");
                    mApiFailureCount = 0;
                }
            });
        }
    }
}
