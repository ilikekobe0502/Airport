package com.whatmedia.ttia.response.data;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.R;

import java.io.Serializable;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class RestaurantInfoData implements Serializable {
    @SerializedName("RestaurantID")
    private String restaurantId;
    @SerializedName("RestaurantName")
    private String restaurantName;
    @SerializedName("TerminalsID")
    private String terminalsId;
    @SerializedName("TerminalsName")
    private String terminalsName;
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
    @SerializedName("Content")
    private String Content;
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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
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

    public String getTerminalsName() {
        return terminalsName;
    }

    public void setTerminalsName(String terminalsName) {
        this.terminalsName = terminalsName;
    }

    public static String getFloorShowText(Context context, String floor) {
        String floorText = context.getString(R.string.floor_1);
        if (floor.equals("1樓")) {
            floorText = context.getString(R.string.floor_1);
        } else if (floor.equals("2樓")) {
            floorText = context.getString(R.string.floor_2);
        } else if (floor.equals("3樓")) {
            floorText = context.getString(R.string.floor_3);
        } else if (floor.equals("4樓")) {
            floorText = context.getString(R.string.floor_4);
        } else if (floor.equals("B1")) {
            floorText = context.getString(R.string.floor_b1);
        } else if (floor.equals("B2")) {
            floorText = context.getString(R.string.floor_b2);
        }

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
