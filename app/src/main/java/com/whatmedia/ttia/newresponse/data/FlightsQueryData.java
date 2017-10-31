package com.whatmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class FlightsQueryData {

    public final static int TAG_ARRIVE_TOP4 = 1;//入境前四筆
    public final static int TAG_DEPARTURE_TOP4 = 2;//出境前四筆
    public final static int TAG_ARRIVE_ALL = 3;//入境全部
    public final static int TAG_DEPARTURE_ALL = 4;//出境全部

    @SerializedName("queryType")
    private int queryType;
    @SerializedName("keyWord")
    private String keyWord;
    @SerializedName("expressDate")
    private String expressDate;

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getExpressDate() {
        return expressDate;
    }

    public void setExpressDate(String expressDate) {
        this.expressDate = expressDate;
    }
}
