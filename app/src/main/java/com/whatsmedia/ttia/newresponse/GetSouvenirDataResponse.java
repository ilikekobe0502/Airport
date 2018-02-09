package com.whatsmedia.ttia.newresponse;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.SouvenirData;

import java.util.List;

public class GetSouvenirDataResponse {

    @SerializedName("souvenirList")
    private List<SouvenirData> souvenirDataList;

    public static GetSouvenirDataResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetSouvenirDataResponse.class);
    }

    public List<SouvenirData> getSouvenirDataList() {
        return souvenirDataList;
    }

    public void setSouvenirDataList(List<SouvenirData> souvenirDataList) {
        this.souvenirDataList = souvenirDataList;
    }
}
