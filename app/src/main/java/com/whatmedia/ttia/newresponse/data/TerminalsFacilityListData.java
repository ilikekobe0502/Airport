package com.whatmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/31.
 */

public class TerminalsFacilityListData {
    @SerializedName("terminalsName")
    private String terminalsName;
    @SerializedName("terminalsFacilityList")
    private List<TerminalsFacilityData> terminalsFacilityList;

    public String getTerminalsName() {
        return terminalsName;
    }

    public void setTerminalsName(String terminalsName) {
        this.terminalsName = terminalsName;
    }

    public List<TerminalsFacilityData> getTerminalsFacilityList() {
        return terminalsFacilityList;
    }

    public void setTerminalsFacilityList(List<TerminalsFacilityData> terminalsFacilityList) {
        this.terminalsFacilityList = terminalsFacilityList;
    }
}
