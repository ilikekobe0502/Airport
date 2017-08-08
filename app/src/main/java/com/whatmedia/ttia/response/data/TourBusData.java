package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class TourBusData {
    @SerializedName("ShuttlesHTML")
    private String shuttlesHtml;
    @SerializedName("lan")
    private String lan;

    public String getShuttlesHtml() {
        return shuttlesHtml;
    }

    public void setShuttlesHtml(String shuttlesHtml) {
        this.shuttlesHtml = shuttlesHtml;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
