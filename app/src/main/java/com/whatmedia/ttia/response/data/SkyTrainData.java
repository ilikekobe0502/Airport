package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class SkyTrainData {
    @SerializedName("SkytrainHTML")
    private String skytrainHtml;
    @SerializedName("lan")
    private String lan;

    public String getSkytrainHtml() {
        return skytrainHtml;
    }

    public void setSkytrainHtml(String skytrainHtml) {
        this.skytrainHtml = skytrainHtml;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
