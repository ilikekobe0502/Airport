package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.FloorCodeData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class GetFloorCodeResponse {
    private static List<FloorCodeData> data;

    public static List<FloorCodeData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FloorCodeData>>(){}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
