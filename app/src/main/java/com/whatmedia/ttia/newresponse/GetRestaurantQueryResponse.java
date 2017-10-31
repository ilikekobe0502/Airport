package com.whatmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatmedia.ttia.newresponse.data.FlightsQueryData;
import com.whatmedia.ttia.newresponse.data.RestaurantQueryData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetRestaurantQueryResponse extends GetBaseResponse {
    private static final String TAG = GetRestaurantQueryResponse.class.getSimpleName();
    private RestaurantQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public RestaurantQueryData getData() {
        return data;
    }

    public void setData(RestaurantQueryData data) {
        this.data = data;
    }
}
