package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.whatsmedia.ttia.newresponse.data.SweetDeleteData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetSweetDeleteResponse {
    private SweetDeleteData data;

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public SweetDeleteData getData() {
        return data;
    }

    public void setData(SweetDeleteData data) {
        this.data = data;
    }
}
