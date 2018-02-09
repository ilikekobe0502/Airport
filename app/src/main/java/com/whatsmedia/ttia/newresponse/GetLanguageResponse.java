package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.whatsmedia.ttia.newresponse.data.LanguageData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetLanguageResponse {
    private LanguageData data;

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public LanguageData getData() {
        return data;
    }

    public void setData(LanguageData data) {
        this.data = data;
    }
}
