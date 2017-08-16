package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.AchievementsData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetAchievementsDataResponse {
    private static List<AchievementsData> data;

    public static List<AchievementsData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<AchievementsData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
