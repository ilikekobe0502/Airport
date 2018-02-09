package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetTramInfoResponse {
    @SerializedName("tram")
    private BaseTrafficInfoData tram;

    public static GetTramInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetTramInfoResponse.class);
    }

    public BaseTrafficInfoData getTram() {
        return tram;
    }

    public void setTram(BaseTrafficInfoData tram) {
        this.tram = tram;
    }
}
