package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/31.
 */

public class ToiletFacilityListData {
    @SerializedName("terminalsName")
    private String terminalsName;
    @SerializedName("terminalsToiletList")
    private List<TerminalsFacilityData> terminalsToiletList;

    public String getTerminalsName() {
        return terminalsName;
    }

    public void setTerminalsName(String terminalsName) {
        this.terminalsName = terminalsName;
    }

    public List<TerminalsFacilityData> getTerminalsToiletList() {
        return terminalsToiletList;
    }

    public void setTerminalsToiletList(List<TerminalsFacilityData> terminalsToiletList) {
        this.terminalsToiletList = terminalsToiletList;
    }
}
