package com.whatmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatmedia.ttia.newresponse.data.WeatherQueryData;

/**
 * Created by neo_mac on 2017/11/1.
 */

public class GetWeatherQueryResponse {
    private final static String TAG = GetWeatherQueryResponse.class.getSimpleName();

    private WeatherQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public WeatherQueryData getData() {
        return data;
    }

    public void setData(WeatherQueryData data) {
        this.data = data;
    }
}
