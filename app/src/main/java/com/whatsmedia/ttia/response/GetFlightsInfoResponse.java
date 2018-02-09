package com.whatsmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatsmedia.ttia.response.data.FlightsInfoData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 2017/8/4.
 */

public class GetFlightsInfoResponse {
    private static List<FlightsInfoData> data;

    public static List<FlightsInfoData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FlightsInfoData>>(){}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
