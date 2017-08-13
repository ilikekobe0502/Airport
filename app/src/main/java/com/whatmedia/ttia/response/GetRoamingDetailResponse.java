package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.RoamingDetailData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetRoamingDetailResponse {
    private static List<RoamingDetailData> data;

    public static List<RoamingDetailData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RoamingDetailData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
