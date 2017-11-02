package com.whatmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/11/1.
 */

public class TravelListQueryData {
    @SerializedName("travelSessionTypeId")
    private String travelSessionTypeId;

    public String getTravelSessionTypeId() {
        return travelSessionTypeId;
    }

    public void setTravelSessionTypeId(String travelSessionTypeId) {
        this.travelSessionTypeId = travelSessionTypeId;
    }
}
