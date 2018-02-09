package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetBusInfoResponse {
    @SerializedName("bus")
    private BaseTrafficInfoData bus;

    public static GetBusInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetBusInfoResponse.class);
    }

    public BaseTrafficInfoData getBus() {
        return bus;
    }

    public void setBus(BaseTrafficInfoData bus) {
        this.bus = bus;
    }
}
