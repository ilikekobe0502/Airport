package com.whatmedia.ttia.newresponse;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.onlyContentData;


public class GetInternationCallResponse {

    @SerializedName("internationalCall")
    private onlyContentData onlyContentData;

    public static GetInternationCallResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetInternationCallResponse.class);
    }

    public onlyContentData getOnlyContentData() {
        return onlyContentData;
    }

    public void setOnlyContentData(onlyContentData onlyContentData) {
        this.onlyContentData = onlyContentData;
    }
}
