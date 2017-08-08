package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class TerminalCodeData {
    @SerializedName("TerminalsID")
    private String terminalsId;
    @SerializedName("TerminalsName")
    private String terminalsName;
    @SerializedName("lan")
    private String lan;

    public String getTerminalsId() {
        return terminalsId;
    }

    public void setTerminalsId(String terminalsId) {
        this.terminalsId = terminalsId;
    }

    public String getTerminalsName() {
        return terminalsName;
    }

    public void setTerminalsName(String terminalsName) {
        this.terminalsName = terminalsName;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
