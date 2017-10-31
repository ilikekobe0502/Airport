package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetAreaListResponse {
    @SerializedName("areaList")
    private List<StoreConditionCodeData> areaList;

    public static GetAreaListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetAreaListResponse.class);
    }

    public List<StoreConditionCodeData> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<StoreConditionCodeData> areaList) {
        this.areaList = areaList;
    }
}
