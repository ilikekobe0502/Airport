package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetRoadsideAssistInfoResponse {
    @SerializedName("roadsideAssist")
    private BaseTrafficInfoData roadsideAssist;

    public static GetRoadsideAssistInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetRoadsideAssistInfoResponse.class);
    }

    public BaseTrafficInfoData getRoadsideAssist() {
        return roadsideAssist;
    }

    public void setRoadsideAssist(BaseTrafficInfoData roadsideAssist) {
        this.roadsideAssist = roadsideAssist;
    }
}
