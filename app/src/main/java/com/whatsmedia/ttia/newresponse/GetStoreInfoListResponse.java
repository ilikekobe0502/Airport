package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.StoreInfoData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetStoreInfoListResponse {
    @SerializedName("storeList")
    private List<StoreInfoData> storeList;

    public static GetStoreInfoListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetStoreInfoListResponse.class);
    }

    public List<StoreInfoData> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreInfoData> storeList) {
        this.storeList = storeList;
    }
}
