package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.newresponse.data.BaseEncodeData;

import java.lang.reflect.Type;

/**
 * Created by neo_mac on 2017/10/27.
 */

public class GetBaseEncodeResponse {
    private BaseEncodeData data;

    public BaseEncodeData getGson(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEncodeData>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public BaseEncodeData getData() {
        return data;
    }

    public void setData(BaseEncodeData data) {
        this.data = data;
    }
}
