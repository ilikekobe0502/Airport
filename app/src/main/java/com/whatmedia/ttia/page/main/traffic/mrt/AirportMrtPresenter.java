package com.whatmedia.ttia.page.main.traffic.mrt;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.GetAirportMrtResponse;
import com.whatmedia.ttia.response.data.AirportMrtData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportMrtPresenter implements AirportMrtContract.Presenter {
    private final static String TAG = AirportMrtPresenter.class.getSimpleName();

    private static AirportMrtPresenter mAirportMrtPresenter;
    private static ApiConnect mApiConnect;
    private static AirportMrtContract.View mView;


    public static AirportMrtPresenter getInstance(Context context, AirportMrtContract.View view) {
        mAirportMrtPresenter = new AirportMrtPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;

        return mAirportMrtPresenter;
    }

    @Override
    public void getAirportMrtAPI() {
        mApiConnect.getAirportMrt(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getAirportMrtFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<AirportMrtData> list = GetAirportMrtResponse.newInstance(result);
                    mView.getAirportMrtSucceed(list);
                } else {
                    mView.getAirportMrtFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });

    }
}
