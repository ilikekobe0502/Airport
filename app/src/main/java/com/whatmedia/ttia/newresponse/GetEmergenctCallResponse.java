package com.whatmedia.ttia.newresponse;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.onlyContentData;

public class GetEmergenctCallResponse {

    @SerializedName("emergencyCall")
    private onlyContentData onlyContentData;

    public static GetEmergenctCallResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetEmergenctCallResponse.class);
    }

    public onlyContentData getOnlyContentData() {
        return onlyContentData;
    }

    public void setOnlyContentData(onlyContentData onlyContentData) {
        this.onlyContentData = onlyContentData;
    }
}
