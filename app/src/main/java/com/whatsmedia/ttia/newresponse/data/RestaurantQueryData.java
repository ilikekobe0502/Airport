package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo on 2017/10/31.
 */

public class RestaurantQueryData {
    @SerializedName("terminalsId")
    private String terminalsId;
    @SerializedName("areaId")
    private String areaId;
    @SerializedName("floorId")
    private String floorId;
    @SerializedName("restaurantTypeId")
    private String restaurantTypeId;

    public String getTerminalsId() {
        return terminalsId;
    }

    public void setTerminalsId(String terminalsId) {
        this.terminalsId = terminalsId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getRestaurantTypeId() {
        return restaurantTypeId;
    }

    public void setRestaurantTypeId(String restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
    }
}
