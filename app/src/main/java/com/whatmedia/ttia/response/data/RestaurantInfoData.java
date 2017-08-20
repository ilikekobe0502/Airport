package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class RestaurantInfoData implements Serializable{
    @SerializedName("RestaurantID")
    private String restaurantId;
    @SerializedName("RestaurantName")
    private String restaurantName;
    @SerializedName("TerminalsID")
    private String terminalsId;
    @SerializedName("AreaID")
    private String areaId;
    @SerializedName("FloorID")
    private String floorId;
    @SerializedName("FloorName")
    private String floorName;
    @SerializedName("FloorCode")
    private String floorCode;
    @SerializedName("RestaurantTypeID")
    private String restaurantTypeId;
    @SerializedName("Contenct")
    private String contenct;
    @SerializedName("WebURL")
    private String webURL;
    @SerializedName("OpenStime")
    private String openStime;
    @SerializedName("OpenEtime")
    private String openEtime;
    @SerializedName("Tel")
    private String tel;
    @SerializedName("lan")
    private String lan;
    @SerializedName("ImgPath")
    private String ImgPath;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTerminalsId() {
        return terminalsId;
    }

    public void setTerminalsId(String terminalsId) {
        this.terminalsId = terminalsId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getRestaurantTypeId() {
        return restaurantTypeId;
    }

    public void setRestaurantTypeId(String restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
    }

    public String getContenct() {
        return contenct;
    }

    public void setContenct(String contenct) {
        this.contenct = contenct;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getOpenStime() {
        return openStime;
    }

    public void setOpenStime(String openStime) {
        this.openStime = openStime;
    }

    public String getOpenEtime() {
        return openEtime;
    }

    public void setOpenEtime(String openEtime) {
        this.openEtime = openEtime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(String floorCode) {
        this.floorCode = floorCode;
    }
}
