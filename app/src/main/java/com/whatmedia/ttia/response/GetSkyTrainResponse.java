package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.SkyTrainData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class GetSkyTrainResponse {
    private static List<SkyTrainData> data;

    public static List<SkyTrainData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SkyTrainData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
