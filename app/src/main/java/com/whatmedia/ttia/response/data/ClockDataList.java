package com.whatmedia.ttia.response.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class ClockDataList {
    private static List<ClockData> data;

    public static List<ClockData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ClockData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        if (data == null)
            data = new ArrayList<>();
        return data;
    }
}
