package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/7.
 */

public class UserNewsData {
    @SerializedName("UserID")
    private String userId;
    @SerializedName("Devicetoken")
    private String deviceToken;
    @SerializedName("DeviceType")
    private String deviceType;
    @SerializedName("NewsID")
    private String newsID;
    @SerializedName("EmergencyID")
    private String emergencyID;
    @SerializedName("IntimateReminderID")
    private String intimateReminderID;
    @SerializedName("Title")
    private String title;
    @SerializedName("Content")
    private String content;
    @SerializedName("A_Time")
    private String aTime;
    @SerializedName("lan")
    private String lan;
    @SerializedName("ImgURL")
    private String imageUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getaTime() {
        return aTime;
    }

    public void setaTime(String aTime) {
        this.aTime = aTime;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmergencyID() {
        return emergencyID;
    }

    public void setEmergencyID(String emergencyID) {
        this.emergencyID = emergencyID;
    }

    public String getIntimateReminderID() {
        return intimateReminderID;
    }

    public void setIntimateReminderID(String intimateReminderID) {
        this.intimateReminderID = intimateReminderID;
    }
}
