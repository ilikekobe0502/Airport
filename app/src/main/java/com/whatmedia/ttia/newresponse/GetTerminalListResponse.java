package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetTerminalListResponse {
    @SerializedName("terminalsList")
    private List<StoreConditionCodeData> terminalsList;

    public static GetTerminalListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetTerminalListResponse.class);
    }

    public List<StoreConditionCodeData> getTerminalsList() {
        return terminalsList;
    }

    public void setTerminalsList(List<StoreConditionCodeData> terminalsList) {
        this.terminalsList = terminalsList;
    }
}
