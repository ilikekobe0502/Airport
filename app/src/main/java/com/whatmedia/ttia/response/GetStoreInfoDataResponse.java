package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.AchievementsData;
import com.whatmedia.ttia.response.data.StoreInfoData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetStoreInfoDataResponse {
    private static List<StoreInfoData> data;

    public static List<StoreInfoData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<StoreInfoData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
