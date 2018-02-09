package com.whatsmedia.ttia.newresponse;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.LanguageListData;
import com.whatsmedia.ttia.utility.Preferences;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/28.
 */

public class GetLanguageListResponse extends GetBaseResponse {
    @SerializedName("langList")
    private List<LanguageListData> data;

    public static GetLanguageListResponse getInstance(Context context, String response) {
        Gson gson = new Gson();

        if (!TextUtils.isEmpty(response)) {
            Preferences.saveLanguageList(context, response);
            return gson.fromJson(response, GetLanguageListResponse.class);
        } else {
            if (!TextUtils.isEmpty(Preferences.getLanguageList(context))) {
                return gson.fromJson((Preferences.getLanguageList(context)), GetLanguageListResponse.class);
            } else
                return new GetLanguageListResponse();
        }
    }

    public List<LanguageListData> getData() {
        return data;
    }

    public void setData(List<LanguageListData> data) {
        this.data = data;
    }
}
