package com.whatmedia.ttia.newresponse.data;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.R;

import java.io.Serializable;

/**
 * Created by neo on 2017/10/31.
 */

public class StoreInfoData implements Serializable{
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
    @SerializedName("storeTypeName")
    private String storeTypeName;
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

    public String getStoreTypeName() {
        return storeTypeName;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
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

}
