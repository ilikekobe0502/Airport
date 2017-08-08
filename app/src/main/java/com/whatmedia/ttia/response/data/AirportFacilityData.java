package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class AirportFacilityData {
    public final static String TAG_TERMINAL_FIRST = "1";//第一航廈ID
    public final static String TAG_TERMINAL_SECOND = "2";//第二航廈ID

    @SerializedName("FloorID")
    private String floorId;
    @SerializedName("FloorName")
    private String floorName;
    @SerializedName("lan")
    private String lan;
    @SerializedName("MainImgPath")
    private String mainImgPath;
    @SerializedName("legendImgPath")
    private String legendImgPath;
    @SerializedName("ClassImgPath")
    private String classImgPath;
    @SerializedName("TerminalsID")
    private String terminalsId;
    @SerializedName("TerminalsName")
    private String terminalsName;
    @SerializedName("Content")
    private String content;

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getMainImgPath() {
        return mainImgPath;
    }

    public void setMainImgPath(String mainImgPath) {
        this.mainImgPath = mainImgPath;
    }

    public String getLegendImgPath() {
        return legendImgPath;
    }

    public void setLegendImgPath(String legendImgPath) {
        this.legendImgPath = legendImgPath;
    }

    public String getClassImgPath() {
        return classImgPath;
    }

    public void setClassImgPath(String classImgPath) {
        this.classImgPath = classImgPath;
    }

    public String getTerminalsId() {
        return terminalsId;
    }

    public void setTerminalsId(String terminalsId) {
        this.terminalsId = terminalsId;
    }

    public String getTerminalsName() {
        return terminalsName;
    }

    public void setTerminalsName(String terminalsName) {
        this.terminalsName = terminalsName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
