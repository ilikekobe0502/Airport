package com.whatmedia.ttia.page.main.traffic.bus;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetAirportBusResponse;
import com.whatmedia.ttia.response.data.AirportBusData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportBusPresenter implements AirportBusContract.Presenter {
    private final static String TAG = AirportBusPresenter.class.getSimpleName();

    private static AirportBusPresenter mAirportBusPresenter;
    private static ApiConnect mApiConnect;
    private static AirportBusContract.View mView;


    public static AirportBusPresenter getInstance(Context context, AirportBusContract.View view) {
        mAirportBusPresenter = new AirportBusPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportBusPresenter;
    }

    @Override
    public void getAirportBusAPI() {
        mApiConnect.getAirportBus(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getAirportBusFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<AirportBusData> list = GetAirportBusResponse.newInstance(result);
                    mView.getAirportBusSucceed(list);
                } else {
                    mView.getAirportBusFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });

    }
}
