package com.whatmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatmedia.ttia.newresponse.data.TravelListQueryData;

/**
 * Created by neo_mac on 2017/11/1.
 */

public class GetTravelListQueryResponse {
    private final static String TAG = GetTravelListQueryResponse.class.getSimpleName();

    private TravelListQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public TravelListQueryData getData() {
        return data;
    }

    public void setData(TravelListQueryData data) {
        this.data = data;
    }
}
