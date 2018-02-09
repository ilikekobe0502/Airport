package com.whatsmedia.ttia.newresponse.data;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by neo on 2017/10/31.
 */

public class BeaconInfoData {
    private final static String TAG = BeaconInfoData.class.getSimpleName();

    private String beaconId;

    public String getJson() {
        Gson gson = new Gson();
        HashMap<String, String> map = new HashMap<>();
        map.put("beaconId", beaconId);
        String json = gson.toJson(map);
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }
}
