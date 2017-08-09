package com.whatmedia.ttia.page.main.flights.my;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetMyFlightsResponse;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class MyFlightsInfoPresenter implements MyFlightsInfoContract.Presenter {
    private final static String TAG = MyFlightsInfoPresenter.class.getSimpleName();

    private static MyFlightsInfoPresenter mMyFlightsInfoPresenter;
    private static ApiConnect mApiConnect;
    private static MyFlightsInfoContract.View mView;


    public static MyFlightsInfoPresenter getInstance(Context context, MyFlightsInfoContract.View view) {
        mMyFlightsInfoPresenter = new MyFlightsInfoPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;

        return mMyFlightsInfoPresenter;
    }

    @Override
    public void getMyFlightsInfoAPI() {
        mApiConnect.getMyFlightsInfo(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getMyFlightsInfoFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    List<FlightsInfoData> list = GetMyFlightsResponse.newInstance(result);
                    mView.getMyFlightsInfoSucceed(list);
                } else {
                    mView.getMyFlightsInfoFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }

    @Override
    public void deleteMyFlightsInfoAPI(FlightsInfoData data) {
        mApiConnect.doMyFlights(data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.deleteMyFlightsInfoFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    mView.deleteMyFlightsInfoSucceed(result);
                } else {
                    mView.deleteMyFlightsInfoFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }
}
