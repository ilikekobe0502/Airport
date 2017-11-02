package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.TravelListData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetTravelListResponse {
    @SerializedName("travelSessionList")
    private List<TravelListData> travelSessionList;

    public static GetTravelListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetTravelListResponse.class);
    }

    public List<TravelListData> getTravelSessionList() {
        return travelSessionList;
    }

    public void setTravelSessionList(List<TravelListData> travelSessionList) {
        this.travelSessionList = travelSessionList;
    }
}
