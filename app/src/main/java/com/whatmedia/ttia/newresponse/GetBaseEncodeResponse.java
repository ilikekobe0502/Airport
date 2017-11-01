package com.whatmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.newresponse.data.BaseEncodeData;

import java.lang.reflect.Type;

/**
 * Created by neo_mac on 2017/10/27.
 */

public class GetBaseEncodeResponse {
    private final static String TAG = GetBaseEncodeResponse.class.getSimpleName();

    private BaseEncodeData data;

    public BaseEncodeData getGson(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEncodeData>() {
        }.getType();
        try {
            data = gson.fromJson(response, type);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "[BaseEncodeData getGson is not Json]", e);
        }
        return data;
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public BaseEncodeData getData() {
        return data;
    }

    public void setData(BaseEncodeData data) {
        this.data = data;
    }
}
