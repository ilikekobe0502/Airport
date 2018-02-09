package com.whatsmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatsmedia.ttia.newresponse.data.StoreQueryData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetStoreQueryResponse extends GetBaseResponse {
    private static final String TAG = GetStoreQueryResponse.class.getSimpleName();
    private StoreQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public StoreQueryData getData() {
        return data;
    }

    public void setData(StoreQueryData data) {
        this.data = data;
    }
}
