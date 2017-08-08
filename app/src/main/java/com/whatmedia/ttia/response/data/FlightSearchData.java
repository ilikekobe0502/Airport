package com.whatmedia.ttia.response.data;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class FlightSearchData {
    private String keyWord;
    private String queryType;
    private String expressTime;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getExpressTime() {
        return expressTime;
    }

    public void setExpressTime(String expressTime) {
        this.expressTime = expressTime;
    }
}
