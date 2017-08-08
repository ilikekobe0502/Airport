package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class RoadsideAssistanceData {
    @SerializedName("RoadsideAssistanceHTML")
    private String roadsideAssistanceHtml;
    @SerializedName("lan")
    private String lan;

    public String getRoadsideAssistanceHtml() {
        return roadsideAssistanceHtml;
    }

    public void setRoadsideAssistanceHtml(String roadsideAssistanceHtml) {
        this.roadsideAssistanceHtml = roadsideAssistanceHtml;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
