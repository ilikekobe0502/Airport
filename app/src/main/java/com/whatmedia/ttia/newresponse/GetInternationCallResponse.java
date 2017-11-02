package com.whatmedia.ttia.newresponse;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.InternationCallData;


public class GetInternationCallResponse {

    @SerializedName("internationalCall")
    private InternationCallData internationCallData;

    public static GetInternationCallResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetInternationCallResponse.class);
    }

    public InternationCallData getInternationCallData() {
        return internationCallData;
    }

    public void setInternationCallData(InternationCallData internationCallData) {
        this.internationCallData = internationCallData;
    }
}
