package com.whatmedia.ttia.newresponse;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.AchievementsData;

import java.util.List;

public class GetAchievementsDataResponse {

    @SerializedName("achievementList")
    private List<AchievementsData> achievementList;

    public static GetAchievementsDataResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetAchievementsDataResponse.class);
    }

    public List<AchievementsData> getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(List<AchievementsData> achievementList) {
        this.achievementList = achievementList;
    }
}
