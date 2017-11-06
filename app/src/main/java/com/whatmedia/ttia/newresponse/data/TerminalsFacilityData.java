package com.whatmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by neo_mac on 2017/10/31.
 */

public class TerminalsFacilityData implements Serializable {
    @SerializedName("floorName")
    private String floorName;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("imgDetailUrl")
    private String imgDetailUrl;

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDetailUrl() {
        return imgDetailUrl;
    }

    public void setImgDetailUrl(String imgDetailUrl) {
        this.imgDetailUrl = imgDetailUrl;
    }
}
