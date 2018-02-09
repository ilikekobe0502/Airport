package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/11/1.
 */

public class WeatherQueryData {

    public final static String TAG_WEEK_WEATHER = "1";
    public final static String TAG_NOW_WEATHER = "2";

    @SerializedName("cityId")
    private String cityId;
    @SerializedName("queryType")
    private String queryType;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
