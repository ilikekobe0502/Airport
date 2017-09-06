package com.whatmedia.ttia.page.main.home.arrive;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class ArriveFlightsPresenter implements ArriveFlightsContract.Presenter {
    private final static String TAG = ArriveFlightsPresenter.class.getSimpleName();

    private static ArriveFlightsPresenter mArriveFlightsPresenter;
    private static ApiConnect mApiConnect;
    private static ArriveFlightsContract.View mView;


    public static ArriveFlightsPresenter getInstance(Context context, ArriveFlightsContract.View view) {
        mArriveFlightsPresenter = new ArriveFlightsPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mArriveFlightsPresenter;
    }

    @Override
    public void getArriveFlightAPI(FlightSearchData searchData) {
        mApiConnect.getSearchFlightsInfoByDate(searchData, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getArriveFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    mView.getArriveFlightSucceed(result);
                } else {
                    mView.getArriveFlightFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }

    @Override
    public void saveMyFlightsAPI(FlightsInfoData data) {
        mApiConnect.doMyFlights(data, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.saveMyFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    mView.saveMyFlightSucceed(result);
                } else {
                    mView.saveMyFlightFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
