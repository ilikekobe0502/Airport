package com.whatmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.FlightsListData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetFlightsListResponse extends GetBaseResponse {
    private final static String TAG = GetFlightsListResponse.class.getSimpleName();

    @SerializedName("flightList")
    private List<FlightsListData> flightList;

    private FlightsListData uploadData;//要新增我的航班的變數

    public static GetFlightsListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetFlightsListResponse.class);
    }

    public String getJson() {
        Gson gson = new Gson();
        String json = uploadData != null ? gson.toJson(uploadData) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public String getDeleteJson() {
        Gson gson = new Gson();
        HashMap<String, List<FlightsListData>> map = new HashMap<>();
        map.put("flights", flightList);

        String json = gson.toJson(map);
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public List<FlightsListData> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightsListData> flightList) {
        this.flightList = flightList;
    }

    public FlightsListData getUploadData() {
        return uploadData;
    }

    public void setUploadData(FlightsListData uploadData) {
        this.uploadData = uploadData;
    }
}
