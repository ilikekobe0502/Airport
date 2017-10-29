package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.BaseResultData;

/**
 * Created by neo_mac on 2017/10/28.
 */

public class GetBaseResponse {
    @SerializedName("result")
    private BaseResultData result;


    public GetBaseResponse getBaseGson(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, GetBaseResponse.class);
    }

    public BaseResultData getResult() {
        return result != null ? result : new BaseResultData();
    }

    public void setResult(BaseResultData result) {
        this.result = result;
    }
}
