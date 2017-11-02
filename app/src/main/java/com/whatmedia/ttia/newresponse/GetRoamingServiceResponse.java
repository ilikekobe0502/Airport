package com.whatmedia.ttia.newresponse;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.RoamingServiceData;
import com.whatmedia.ttia.newresponse.data.SouvenirData;

import java.util.List;

public class GetRoamingServiceResponse {

    @SerializedName("telecommunicationIndustryList")
    private List<RoamingServiceData> roamingServiceDataList;

    public static GetRoamingServiceResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetRoamingServiceResponse.class);
    }

    public List<RoamingServiceData> getRoamingServiceDataList() {
        return roamingServiceDataList;
    }

    public void setRoamingServiceDataList(List<RoamingServiceData> roamingServiceDataList) {
        this.roamingServiceDataList = roamingServiceDataList;
    }
}
