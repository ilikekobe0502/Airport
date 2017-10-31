package com.whatmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatmedia.ttia.newresponse.data.RegisterUserData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetRegisterUserResponse {
    private final static String TAG = GetRegisterUserResponse.class.getSimpleName();

    private RegisterUserData data;

    public RegisterUserData getData() {
        return data;
    }

    public void setData(RegisterUserData data) {
        this.data = data;
    }

    public String getJson() {
        String json;

        Gson gson = new Gson();
        json = gson.toJson(data);

        Log.i(TAG, String.format("[Json] %s",json));
        return json;
    }
}
