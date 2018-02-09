package com.whatsmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/7.
 */

public class HomeParkingInfoData {
    public final static String TAG_ID_P1 = "PTYA0001";
    public final static String TAG_ID_P2 = "PTYA0002";
    public final static String TAG_ID_P3 = "PTYA0003";
    public final static String TAG_ID_P4 = "PTYA0004";

    @SerializedName("DATATIME")
    private String dataTime;
    @SerializedName("AVAILABLECAR")
    private String availableCar;

    @SerializedName("TOTALCAR")
    private String totalCar;
    @SerializedName("ADDRESS")
    private String address;
    @SerializedName("GISX")
    private String gisX;
    @SerializedName("TOTALBIKE")
    private String totalBike;
    @SerializedName("SUMMARY")
    private String Summary;
    @SerializedName("TYPE")
    private String type;
    @SerializedName("NAME")
    private String name;
    @SerializedName("ID")
    private String id;
    @SerializedName("TEL")
    private String tel;
    @SerializedName("TOTALMOTOR")
    private String totalMotor;
    @SerializedName("UPDATETIME")
    private String UpdateTime;
    @SerializedName("AREA")
    private String area;
    @SerializedName("GISY")
    private String gisY;
    @SerializedName("PAYEX")
    private String payex;
    @SerializedName("SERVICETIME")
    private String serviceTime;

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailableCar() {
        return availableCar;
    }

    public void setAvailableCar(String availableCar) {
        this.availableCar = availableCar;
    }

    public String getTotalCar() {
        return totalCar;
    }

    public void setTotalCar(String totalCar) {
        this.totalCar = totalCar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGisX() {
        return gisX;
    }

    public void setGisX(String gisX) {
        this.gisX = gisX;
    }

    public String getTotalBike() {
        return totalBike;
    }

    public void setTotalBike(String totalBike) {
        this.totalBike = totalBike;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTotalMotor() {
        return totalMotor;
    }

    public void setTotalMotor(String totalMotor) {
        this.totalMotor = totalMotor;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGisY() {
        return gisY;
    }

    public void setGisY(String gisY) {
        this.gisY = gisY;
    }

    public String getPayex() {
        return payex;
    }

    public void setPayex(String payex) {
        this.payex = payex;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
}
