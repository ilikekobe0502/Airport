package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetLostAndFoundInfoResponse {
    @SerializedName("lostAndFound")
    private BaseTrafficInfoData lostAndFound;

    public static GetLostAndFoundInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetLostAndFoundInfoResponse.class);
    }

    public BaseTrafficInfoData getLostAndFound() {
        return lostAndFound;
    }

    public void setLostAndFound(BaseTrafficInfoData lostAndFound) {
        this.lostAndFound = lostAndFound;
    }
}
