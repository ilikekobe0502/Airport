package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/11/1.
 */

public class BaseTrafficInfoData {
    @SerializedName("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
