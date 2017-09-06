package com.whatmedia.ttia.page.main.flights.result;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetFlightsInfoResponse;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
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

    @Override
    public void getFlightAPI(FlightSearchData searchData) {
        mApiConnect.getSearchFlightsInfoByDate(searchData, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    List<FlightsInfoData> list = GetFlightsInfoResponse.newInstance(result);
                    mView.getFlightSucceed(list);
                } else {
                    mView.getFlightFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }

    @Override
    public void getFlightByKeywordAPI(FlightSearchData searchData) {
        mApiConnect.getSearchFlightsInfoByKeyWord(searchData, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    List<FlightsInfoData> list = GetFlightsInfoResponse.newInstance(result);
                    mView.getFlightSucceed(list);
                } else {
                    mView.getFlightFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
