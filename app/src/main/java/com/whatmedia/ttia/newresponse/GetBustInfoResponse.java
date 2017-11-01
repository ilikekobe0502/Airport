package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.BusInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetBustInfoResponse {
    @SerializedName("bus")
    private BusInfoData bus;

    public static GetBustInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetBustInfoResponse.class);
    }

    public BusInfoData getBus() {
        return bus;
    }

    public void setBus(BusInfoData bus) {
        this.bus = bus;
    }
}
