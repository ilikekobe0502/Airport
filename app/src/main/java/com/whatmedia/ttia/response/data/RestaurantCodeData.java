package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class RestaurantCodeData {
    @SerializedName("RestaurantTypeID")
    private String restaurantTypeId;
    @SerializedName("RestaurantTypeName")
    private String restaurantTypeName;
    @SerializedName("lan")
    private String lan;

    public String getRestaurantTypeId() {
        return restaurantTypeId;
    }

    public void setRestaurantTypeId(String restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
    }

    public String getRestaurantTypeName() {
        return restaurantTypeName;
    }

    public void setRestaurantTypeName(String restaurantTypeName) {
        this.restaurantTypeName = restaurantTypeName;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
