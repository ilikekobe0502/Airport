package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.AchievementsData;
import com.whatmedia.ttia.response.data.StoreInfoData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetStoreInfoDataResponse {
    @SerializedName("data")
    private List<StoreInfoData> data;

    public static List<StoreInfoData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<StoreInfoData>>() {
        }.getType();
        GetStoreInfoDataResponse storeInfoDataResponse = gson.fromJson(response, GetStoreInfoDataResponse.class);
        if (storeInfoDataResponse != null && storeInfoDataResponse.data != null) {
            return storeInfoDataResponse.data;
        } else
            return new ArrayList<>();
    }
}
