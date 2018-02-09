package com.whatsmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatsmedia.ttia.newresponse.data.FlightsQueryData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetFlightsQueryResponse extends GetBaseResponse {
    private static final String TAG = GetFlightsQueryResponse.class.getSimpleName();
    private FlightsQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public FlightsQueryData getData() {
        return data;
    }

    public void setData(FlightsQueryData data) {
        this.data = data;
    }
}
