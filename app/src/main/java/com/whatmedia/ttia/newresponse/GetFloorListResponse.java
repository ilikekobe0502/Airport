package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetFloorListResponse {
    @SerializedName("floorList")
    private List<StoreConditionCodeData> floorList;

    public static GetFloorListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetFloorListResponse.class);

    }

    public List<StoreConditionCodeData> getFloorList() {
        return floorList;
    }

    public void setFloorList(List<StoreConditionCodeData> floorList) {
        this.floorList = floorList;
    }
}
