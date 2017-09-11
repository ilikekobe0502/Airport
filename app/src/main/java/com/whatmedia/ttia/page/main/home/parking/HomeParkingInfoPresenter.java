package com.whatmedia.ttia.page.main.home.parking;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetHomeParkingInfoListResponse;
import com.whatmedia.ttia.response.data.HomeParkingFrameData;
import com.whatmedia.ttia.response.data.HomeParkingInfoData;
import com.whatmedia.ttia.response.data.HomeParkingListData;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class HomeParkingInfoPresenter implements HomeParkingInfoContract.Presenter {
    private final static String TAG = HomeParkingInfoPresenter.class.getSimpleName();

    private static HomeParkingInfoPresenter mHomeParkingInfoPresenter;
    private static ApiConnect mApiConnect;
    private static HomeParkingInfoContract.View mView;


    public static HomeParkingInfoPresenter getInstance(Context context, HomeParkingInfoContract.View view) {
        mHomeParkingInfoPresenter = new HomeParkingInfoPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mHomeParkingInfoPresenter;
    }

    @Override
    public void getParkingInfoAPI() {
        mApiConnect.getHomeParkIngInfo(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getParkingInfoFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);

                    HomeParkingFrameData frameData = GetHomeParkingInfoListResponse.newInstance(Util.xmlToSJsonString(result));
                    HomeParkingListData listData;
                    List<HomeParkingInfoData> infoData;
                    if (frameData != null) {
                        listData = frameData.getParkingListData();
                    } else
                        listData = new HomeParkingListData();

                    if (listData.getPark() != null) {
                        infoData = listData.getPark();
                    } else {
                        infoData = new ArrayList<HomeParkingInfoData>();
                    }
                    mView.getParkingInfoSucceed(infoData);
                } else {
                    mView.getParkingInfoFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
