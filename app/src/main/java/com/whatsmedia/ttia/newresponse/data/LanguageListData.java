package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/10/28.
 */

public class LanguageListData {
    public final static int TAG_ZH_TW = 1;
    public final static int TAG_ZH_CH = 2;
    public final static int TAG_EN = 3;
    public final static int TAG_JP = 4;

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
