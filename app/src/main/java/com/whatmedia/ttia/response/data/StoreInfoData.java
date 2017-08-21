package com.whatmedia.ttia.response.data;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.R;

import java.io.Serializable;

/**
 * Created by neo_mac on 2017/8/16.
 */

public class StoreInfoData implements Serializable {
    @SerializedName("Store_ID")
    private String storeId;
    @SerializedName("Store_Name")
    private String storeName;
    @SerializedName("Store_Tel")
    private String storeTel;
    @SerializedName("Store_IMGPath")
    private String storeIMGPath;
    @SerializedName("Store_type")
    private String storeType;
    @SerializedName("OpenStime")
    private String openStime;
    @SerializedName("OpenEtime")
    private String openEtime;
    @SerializedName("Content")
    private String conetnt;
    @SerializedName("lan")
    private String lan;
    @SerializedName("TerminalsID")
    private String terminalsId;
    @SerializedName("AreaID")
    private String areaId;
    @SerializedName("FloorID")
    private String floorId;
    @SerializedName("FloorCode")
    private String floorCode;
    @SerializedName("FloorName")
    private String floorName;
    @SerializedName("TerminalsName")
    private String terminalsName;
    @SerializedName("AreaName")
    private String areaName;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreTel() {
        return storeTel;
    }

    public void setStoreTel(String storeTel) {
        this.storeTel = storeTel;
    }

    public String getStoreIMGPath() {
        return storeIMGPath;
    }

    public void setStoreIMGPath(String storeIMGPath) {
        this.storeIMGPath = storeIMGPath;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
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

    public String getConetnt() {
        return conetnt;
    }

    public void setConetnt(String conetnt) {
        this.conetnt = conetnt;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
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

    public String getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(String floorCode) {
        this.floorCode = floorCode;
    }

    public static String getFloorShowText(Context context, String floor) {
        String floorText = context.getString(R.string.floor_1);
        if (floor.contains("B1")) {
            floorText = context.getString(R.string.floor_b1);
        } else if (floor.contains("B2")) {
            floorText = context.getString(R.string.floor_b2);
        } else if (floor.contains("1")) {
            floorText = context.getString(R.string.floor_1);
        } else if (floor.contains("2")) {
            floorText = context.getString(R.string.floor_2);
        } else if (floor.contains("3")) {
            floorText = context.getString(R.string.floor_3);
        } else if (floor.contains("4"))
            floorText = context.getString(R.string.floor_4);

        return floorText;
    }

    public static String getTerminalText(Context context, String terminalsName) {
        String terminal = "";
        if (!TextUtils.isEmpty(terminalsName)) {
            if (terminalsName.contains("一"))
                terminal =context.getString(R.string.terminal_1);
            else if (terminalsName.contains("二"))
                terminal =context.getString(R.string.terminal_2);
            else if (terminalsName.contains("1"))
                terminal =context.getString(R.string.terminal_1);
            else if (terminalsName.contains("2"))
                terminal =context.getString(R.string.terminal_2);
        }
        return terminal;
    }

    public static String getSimpleTerminalText(Context context, String terminalsName) {
        String terminal = "";
        if (!TextUtils.isEmpty(terminalsName)) {
            if (terminalsName.contains("一"))
                terminal =context.getString(R.string.store_terminal_1);
            else if (terminalsName.contains("二"))
                terminal =context.getString(R.string.store_terminal_2);
            else if (terminalsName.contains("1"))
                terminal =context.getString(R.string.store_terminal_1);
            else if (terminalsName.contains("2"))
                terminal =context.getString(R.string.store_terminal_2);
        }
        return terminal;
    }
}
