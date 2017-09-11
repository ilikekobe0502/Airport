package com.whatmedia.ttia.page.main.communication.emergency;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.GetEmergenctCallResponse;
import com.whatmedia.ttia.response.data.EmergenctCallData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class EmergencyCallPresenter implements EmergencyCallContract.Presenter {
    private final static String TAG = EmergencyCallPresenter.class.getSimpleName();

    private static EmergencyCallPresenter mEmergencyCallPresenter;
    private static ApiConnect mApiConnect;
    private static EmergencyCallContract.View mView;


    public static EmergencyCallPresenter getInstance(Context context, EmergencyCallContract.View view) {
        mEmergencyCallPresenter = new EmergencyCallPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mEmergencyCallPresenter;
    }

    @Override
    public void getEmergencyCallAPI() {
        mApiConnect.getEmergencyCall(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getEmergencyCallFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<EmergenctCallData> list = GetEmergenctCallResponse.newInstance(result);
                    mView.getEmergencyCallSucceed(list);
                } else {
                    mView.getEmergencyCallFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
