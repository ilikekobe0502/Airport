package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.AirportFacilityData;
import com.whatmedia.ttia.response.data.MyFlightsInfoData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class GetMyFlightsResponse {
    private static List<MyFlightsInfoData> data;

    public static List<MyFlightsInfoData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MyFlightsInfoData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
