package com.whatsmedia.ttia.page.main.flights.info;

import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class FlightsInfoPresenter implements FlightsInfoContract.Presenter {
    private final static String TAG = FlightsInfoPresenter.class.getSimpleName();

    private static FlightsInfoPresenter mFlightsInfoPresenter;
    private static ApiConnect mApiConnect;
    private static FlightsInfoContract.View mView;


    public static FlightsInfoPresenter getInstance(Context context, FlightsInfoContract.View view) {
        mFlightsInfoPresenter = new FlightsInfoPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mFlightsInfoPresenter;
    }
}
