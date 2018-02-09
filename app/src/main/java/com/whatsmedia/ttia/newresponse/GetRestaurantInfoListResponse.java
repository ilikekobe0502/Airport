package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.RestaurantInfoData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetRestaurantInfoListResponse {
    @SerializedName("restaurantList")
    private List<RestaurantInfoData> restaurantList;

    public static GetRestaurantInfoListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetRestaurantInfoListResponse.class);
    }

    public List<RestaurantInfoData> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<RestaurantInfoData> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
