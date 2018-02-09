package com.whatsmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatsmedia.ttia.newresponse.data.LocationQueryData;

/**
 * Created by neo_mac on 2017/11/1.
 */

public class GetLocationQueryResponse {
    private final static String TAG = GetLocationQueryResponse.class.getSimpleName();

    private LocationQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public LocationQueryData getData() {
        return data;
    }

    public void setData(LocationQueryData data) {
        this.data = data;
    }
}
