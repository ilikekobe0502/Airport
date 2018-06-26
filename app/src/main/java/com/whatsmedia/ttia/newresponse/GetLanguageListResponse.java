package com.whatsmedia.ttia.newresponse;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.LanguageListData;
import com.whatsmedia.ttia.utility.Preferences;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/28.
 */

public class GetLanguageListResponse extends GetBaseResponse {
    private final static String TAG = GetLanguageListResponse.class.getSimpleName();

    @SerializedName("langList")
    private List<LanguageListData> data;

    public static GetLanguageListResponse getInstance(Context context, String response) {
        Gson gson = new Gson();

        if (!TextUtils.isEmpty(response)) {
            GetLanguageListResponse getLanguageListResponse;
            try {
                Preferences.saveLanguageList(context, response);
                getLanguageListResponse = gson.fromJson(response, GetLanguageListResponse.class);
            } catch (JsonSyntaxException e) {
                Log.e(TAG, "[GetLanguageListResponse getGson is not Json]", e);
                getLanguageListResponse = new GetLanguageListResponse();
            }
            return getLanguageListResponse;
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
