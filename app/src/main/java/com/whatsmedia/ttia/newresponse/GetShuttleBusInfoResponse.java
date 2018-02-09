package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetShuttleBusInfoResponse {
    @SerializedName("shuttle")
    private BaseTrafficInfoData shuttle;

    public static GetShuttleBusInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetShuttleBusInfoResponse.class);
    }

    public BaseTrafficInfoData getShuttle() {
        return shuttle;
    }

    public void setShuttle(BaseTrafficInfoData shuttle) {
        this.shuttle = shuttle;
    }
}
