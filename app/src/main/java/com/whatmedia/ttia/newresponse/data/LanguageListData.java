package com.whatmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/10/28.
 */

public class LanguageListData {
    private final static int TAG_ZH_TW = 1;
    private final static int TAG_ZH_CH = 2;
    private final static int TAG_EN = 3;
    private final static int TAG_JP = 4;

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}