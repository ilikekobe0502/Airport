package com.whatmedia.ttia.page.main.terminals.toilet;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.GetAirportToiletResponse;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class PublicToiletPresenter implements PublicToiletContract.Presenter {
    private final static String TAG = PublicToiletPresenter.class.getSimpleName();

    private static PublicToiletPresenter mPublicToiletPresenter;
    private static ApiConnect mApiConnect;
    private static PublicToiletContract.View mView;


    public static PublicToiletPresenter getInstance(Context context, PublicToiletContract.View view) {
        mPublicToiletPresenter = new PublicToiletPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mPublicToiletPresenter;
    }

    @Override
    public void getPublicToiletAPI() {
        mApiConnect.getPublicToilet(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getPublicFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<AirportFacilityData> list = GetAirportToiletResponse.newInstance(result);
                    mView.getPublicToiletSucceed(list);
                } else {
                    mView.getPublicFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
