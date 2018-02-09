package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class RegisterUserData {
    @SerializedName("deviceId")
    private String deviceID;
    @SerializedName("pushToken")
    private String pushToken;
    @SerializedName("langId")
    private int langId;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }
}
