package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.ToiletFacilityListData;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/31.
 */

public class GetToiletFacilityListResponse {
    @SerializedName("terminalsToiletList")
    private List<ToiletFacilityListData> terminalsToiletList;

    public static GetToiletFacilityListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetToiletFacilityListResponse.class);
    }

    public List<ToiletFacilityListData> getTerminalsToiletList() {
        return terminalsToiletList;
    }

    public void setTerminalsToiletList(List<ToiletFacilityListData> terminalsToiletList) {
        this.terminalsToiletList = terminalsToiletList;
    }
}
