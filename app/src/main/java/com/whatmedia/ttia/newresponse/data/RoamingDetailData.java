package com.whatmedia.ttia.newresponse.data;


import com.google.gson.annotations.SerializedName;

public class RoamingDetailData {
    @SerializedName("imgUrl")
    private String irHtml;
    @SerializedName("url")
    private String irUrl;

    RoamingDetailData(String html,String url){
        this.setIrUrl(url);
        this.setIrHtml(html);
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
