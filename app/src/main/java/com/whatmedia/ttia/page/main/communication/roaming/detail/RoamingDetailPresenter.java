package com.whatmedia.ttia.page.main.communication.roaming.detail;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetRoamingDetailResponse;
import com.whatmedia.ttia.response.data.RoamingDetailData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RoamingDetailPresenter implements RoamingDetailContract.Presenter {
    private final static String TAG = RoamingDetailPresenter.class.getSimpleName();

    private static RoamingDetailPresenter mRoamingServicePresenter;
    private static ApiConnect mApiConnect;
    private static RoamingDetailContract.View mView;


    public static RoamingDetailPresenter getInstance(Context context, RoamingDetailContract.View view) {
        mRoamingServicePresenter = new RoamingDetailPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mRoamingServicePresenter;
    }

    @Override
    public void getRoamingDetailAPI(String id) {
        mApiConnect.getRoamingDetail(id, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getRoamingDetailFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<RoamingDetailData> list = GetRoamingDetailResponse.newInstance(result);
                    mView.getRoamingDetailSucceed(list);
                } else {
                    mView.getRoamingDetailFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
