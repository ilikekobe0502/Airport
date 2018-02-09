package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/10/27.
 */

public class BaseEncodeData {
    @SerializedName("encode")
    private String encode;

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
