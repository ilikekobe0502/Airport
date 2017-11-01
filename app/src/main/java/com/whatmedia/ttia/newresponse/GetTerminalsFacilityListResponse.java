package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.TerminalsFacilityListData;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/31.
 */

public class GetTerminalsFacilityListResponse {
    @SerializedName("terminalsFacilityList")
    private List<TerminalsFacilityListData> terminalsFacilityList;

    public static GetTerminalsFacilityListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetTerminalsFacilityListResponse.class);
    }

    public List<TerminalsFacilityListData> getTerminalsFacilityList() {
        return terminalsFacilityList;
    }

    public void setTerminalsFacilityList(List<TerminalsFacilityListData> terminalsFacilityList) {
        this.terminalsFacilityList = terminalsFacilityList;
    }
}
