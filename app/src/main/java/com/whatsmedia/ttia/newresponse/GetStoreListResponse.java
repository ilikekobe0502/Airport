package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.StoreConditionCodeData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetStoreListResponse {
    @SerializedName("storeTypeList")
    private List<StoreConditionCodeData> storeTypeList;

    public static GetStoreListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetStoreListResponse.class);
    }

    public List<StoreConditionCodeData> getStoreTypeList() {
        return storeTypeList;
    }

    public void setStoreTypeList(List<StoreConditionCodeData> storeTypeList) {
        this.storeTypeList = storeTypeList;
    }
}
