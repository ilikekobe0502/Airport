package com.whatmedia.ttia.page.main.traffic.skytrain;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.GetSkyTrainResponse;
import com.whatmedia.ttia.response.data.SkyTrainData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class SkyTrainPresenter implements SkyTrainContract.Presenter {
    private final static String TAG = SkyTrainPresenter.class.getSimpleName();

    private static SkyTrainPresenter mSkyTrainPresenter;
    private static ApiConnect mApiConnect;
    private static SkyTrainContract.View mView;


    public static SkyTrainPresenter getInstance(Context context, SkyTrainContract.View view) {
        mSkyTrainPresenter = new SkyTrainPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mSkyTrainPresenter;
    }

    @Override
    public void getSkyTrainAPI() {
        mApiConnect.getSkyTrain(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getSkyTrainFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<SkyTrainData> list = GetSkyTrainResponse.newInstance(result);
                    mView.getSkyTrainSucceed(list);
                } else {
                    mView.getSkyTrainFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });

    }
}
