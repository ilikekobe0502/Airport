package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetRestaurantListResponse {
    @SerializedName("restaurantTypeList")
    private List<StoreConditionCodeData> restaurantList;

    public static GetRestaurantListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetRestaurantListResponse.class);
    }

    public List<StoreConditionCodeData> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<StoreConditionCodeData> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
