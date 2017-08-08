package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.TourBusData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class GetTourBusResponse {
    private static List<TourBusData> data;

    public static List<TourBusData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TourBusData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
