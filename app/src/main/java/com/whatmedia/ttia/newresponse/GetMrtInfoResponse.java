package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;

/**
 * Created by neo on 2017/10/31.
 */

public class GetMrtInfoResponse {
    @SerializedName("mrtHsr")
    private BaseTrafficInfoData mrtHsr;

    public static GetMrtInfoResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetMrtInfoResponse.class);
    }

    public BaseTrafficInfoData getMrtHsr() {
        return mrtHsr;
    }

    public void setMrtHsr(BaseTrafficInfoData mrtHsr) {
        this.mrtHsr = mrtHsr;
    }
}
