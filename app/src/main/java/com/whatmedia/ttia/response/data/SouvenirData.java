package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;

public class SouvenirData {
    @SerializedName("Souvenir_ID")
    private String id;
    @SerializedName("Souvenir_Name")
    private String name;
    @SerializedName("TerminalsName")
    private String terminalsName;
    @SerializedName("AreaName")
    private String areaName;
    @SerializedName("FloorName")
    private String floorName;
    @SerializedName("FloorID")
    private String floorId;
    @SerializedName("Content")
    private String content;
    @SerializedName("OpenStime")
    private String openTime;
    @SerializedName("OpenEtime")
    private String closeTime;
    @SerializedName("Souvenir_Tel")
    private String tel;
    @SerializedName("Souvenir_IMGPath")
    private String imgPath;
    @SerializedName("Souvenir_Price")
    private String price;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }
}
