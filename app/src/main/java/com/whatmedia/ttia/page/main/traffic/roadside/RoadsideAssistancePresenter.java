package com.whatmedia.ttia.page.main.traffic.roadside;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.GetRoadsideAssistanceResponse;
import com.whatmedia.ttia.response.data.RoadsideAssistanceData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class RoadsideAssistancePresenter implements RoadsideAssistanceContract.Presenter {
    private final static String TAG = RoadsideAssistancePresenter.class.getSimpleName();

    private static RoadsideAssistancePresenter mRoadsideAssistancePresenter;
    private static ApiConnect mApiConnect;
    private static RoadsideAssistanceContract.View mView;


    public static RoadsideAssistancePresenter getInstance(Context context, RoadsideAssistanceContract.View view) {
        mRoadsideAssistancePresenter = new RoadsideAssistancePresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mRoadsideAssistancePresenter;
    }

    @Override
    public void getRoadsideAssistanceAPI() {
        mApiConnect.getRoadsideAssistance(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getRoadsideAssistanceFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<RoadsideAssistanceData> list = GetRoadsideAssistanceResponse.newInstance(result);
                    mView.getRoadsideAssistanceSucceed(list);
                } else {
                    mView.getRoadsideAssistanceFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });

    }
}
