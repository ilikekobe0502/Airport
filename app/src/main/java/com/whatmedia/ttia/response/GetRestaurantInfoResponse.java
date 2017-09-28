package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.RestaurantInfoData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class GetRestaurantInfoResponse {
    @SerializedName("data")
    private  List<RestaurantInfoData> data;

    public static List<RestaurantInfoData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RestaurantInfoData>>() {
        }.getType();
        GetRestaurantInfoResponse restaurantInfoResponse = gson.fromJson(response, GetRestaurantInfoResponse.class);
        if (restaurantInfoResponse != null && restaurantInfoResponse.data != null) {
            return restaurantInfoResponse.data;
        } else
            return new ArrayList<>();
    }
}
