package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by neo on 2017/10/31.
 */

public class RestaurantInfoData implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("terminalsName")
    private String terminalsName;
    @SerializedName("areaName")
    private String areaName;
    @SerializedName("floorName")
    private String floorName;
    @SerializedName("restaurantTypeName")
    private String restaurantTypeName;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;
    @SerializedName("openTime")
    private String openTime;
    @SerializedName("closeTime")
    private String closeTime;
    @SerializedName("tel")
    private String tel;
    @SerializedName("imgUrl")
    private String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerminalsName() {
        return terminalsName;
    }

    public void setTerminalsName(String terminalsName) {
        this.terminalsName = terminalsName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRestaurantTypeName() {
        return restaurantTypeName;
    }

    public void setRestaurantTypeName(String restaurantTypeName) {
        this.restaurantTypeName = restaurantTypeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
