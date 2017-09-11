package com.whatmedia.ttia.page.main.flights.search;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class FlightsSearchPresenter implements FlightsSearchContract.Presenter {
    private final static String TAG = FlightsSearchPresenter.class.getSimpleName();

    private static FlightsSearchPresenter mFlightsSearchPresenter;
    private static ApiConnect mApiConnect;
    private static FlightsSearchContract.View mView;


    public static FlightsSearchPresenter getInstance(Context context, FlightsSearchContract.View view) {
        mFlightsSearchPresenter = new FlightsSearchPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mFlightsSearchPresenter;
    }

    @Override
    public void getFlightsInfoAPI(final FlightSearchData searchData) {
        mApiConnect.getSearchFlightsInfoByKeyWord(searchData, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                if (TextUtils.equals(searchData.getQueryType(), FlightsInfoData.TAG_KIND_ARRIVE))
                    mView.getFlightsArriveFailed(e.toString(),timeout);
                else
                    mView.getFlightsDepartureFailed(e.toString(),timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    if (TextUtils.equals(searchData.getQueryType(), FlightsInfoData.TAG_KIND_ARRIVE))
                        mView.getFlightsArriveSucceed(result);
                    else
                        mView.getFlightsDepartureSucceed(result);
                } else {
                    if (TextUtils.equals(searchData.getQueryType(), FlightsInfoData.TAG_KIND_ARRIVE))
                        mView.getFlightsArriveFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                    else
                        mView.getFlightsDepartureFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
