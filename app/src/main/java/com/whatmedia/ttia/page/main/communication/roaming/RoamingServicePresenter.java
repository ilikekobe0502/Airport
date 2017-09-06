package com.whatmedia.ttia.page.main.communication.roaming;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetRoamingServiceResponse;
import com.whatmedia.ttia.response.data.RoamingServiceData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RoamingServicePresenter implements RoamingServiceContract.Presenter {
    private final static String TAG = RoamingServicePresenter.class.getSimpleName();

    private static RoamingServicePresenter mRoamingServicePresenter;
    private static ApiConnect mApiConnect;
    private static RoamingServiceContract.View mView;


    public static RoamingServicePresenter getInstance(Context context, RoamingServiceContract.View view) {
        mRoamingServicePresenter = new RoamingServicePresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mRoamingServicePresenter;
    }

    @Override
    public void getRoamingServiceAPI() {
        mApiConnect.getRoamingService(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getRoamingServiceFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<RoamingServiceData> list = GetRoamingServiceResponse.newInstance(result);
                    mView.getRoamingServiceSucceed(list);
                } else {
                    mView.getRoamingServiceFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
