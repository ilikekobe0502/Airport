package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetTaxiInfoResponse {
    @SerializedName("taxi")
    private BaseTrafficInfoData taxi;

    public static GetTaxiInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetTaxiInfoResponse.class);
    }

    public BaseTrafficInfoData getTaxi() {
        return taxi;
    }

    public void setTaxi(BaseTrafficInfoData taxi) {
        this.taxi = taxi;
    }
}
