package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;

public class RoamingDetailData {
    @SerializedName("TINAME")
    private String tiName;
    @SerializedName("IRHTML")
    private String irHtml;
    @SerializedName("IRURL")
    private String irUrl;

    public String getTiName() {
        return tiName;
    }

    public void setTiName(String tiName) {
        this.tiName = tiName;
    }

    public String getIrHtml() {
        return irHtml;
    }

    public void setIrHtml(String irHtml) {
        this.irHtml = irHtml;
    }

    public String getIrUrl() {
        return irUrl;
    }

    public void setIrUrl(String irUrl) {
        this.irUrl = irUrl;
    }
}
