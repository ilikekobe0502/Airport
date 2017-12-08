package com.whatmedia.ttia.newresponse;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class GetRoamingDetailResponse {

    @SerializedName("imgDetailUrl")
    private String imgDetailUrl;
    @SerializedName("imgUrl")
    private String irHtml;
    @SerializedName("url")
    private String irUrl;

    public static GetRoamingDetailResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetRoamingDetailResponse.class);
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

    public String getImgDetailUrl() {
        return imgDetailUrl;
    }

    public void setImgDetailUrl(String imgDetailUrl) {
        this.imgDetailUrl = imgDetailUrl;
    }

}
