package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.TravelTypeListData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetTravelTypeListResponse {
    @SerializedName("travelSessionTypeList")
    private List<TravelTypeListData> travelSessionTypeList;

    public static GetTravelTypeListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetTravelTypeListResponse.class);
    }

    public List<TravelTypeListData> getTravelSessionTypeList() {
        return travelSessionTypeList;
    }

    public void setTravelSessionTypeList(List<TravelTypeListData> travelSessionTypeList) {
        this.travelSessionTypeList = travelSessionTypeList;
    }
}
