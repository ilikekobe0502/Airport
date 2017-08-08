package com.whatmedia.ttia.page.main.flights.result;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class FlightsSearchResultPresenter implements FlightsSearchResultContract.Presenter {
    private final static String TAG = FlightsSearchResultPresenter.class.getSimpleName();

    private static FlightsSearchResultPresenter mFlightsSearchResultPresenter;
    private static ApiConnect mApiConnect;
    private static FlightsSearchResultContract.View mView;


    public static FlightsSearchResultPresenter getInstance(Context context, FlightsSearchResultContract.View view) {
        mFlightsSearchResultPresenter = new FlightsSearchResultPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mFlightsSearchResultPresenter;
    }

    @Override
    public void saveMyFlightsAPI(FlightsInfoData data) {
        mApiConnect.doMyFlights(data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.saveMyFlightFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    mView.saveMyFlightSucceed(result);
                } else {
                    mView.saveMyFlightFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }
}
