package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo on 2017/11/2.
 */

public class TravelListData {
    @SerializedName("tw")
    private String tw;
    @SerializedName("en")
    private String en;
    @SerializedName("roman")
    private String roman;
    @SerializedName("jp")
    private String jp;

    public String getTw() {
        return tw;
    }

    public void setTw(String tw) {
        this.tw = tw;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getRoman() {
        return roman;
    }

    public void setRoman(String roman) {
        this.roman = roman;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }
}
