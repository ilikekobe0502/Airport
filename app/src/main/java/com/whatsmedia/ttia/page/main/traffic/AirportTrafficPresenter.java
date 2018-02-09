package com.whatsmedia.ttia.page.main.traffic;

import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportTrafficPresenter implements AirportTrafficContract.Presenter {
    private final static String TAG = AirportTrafficPresenter.class.getSimpleName();

    private static AirportTrafficPresenter mAirportTrafficPresenter;
    private static ApiConnect mApiConnect;
    private static AirportTrafficContract.View mView;


    public static AirportTrafficPresenter getInstance(Context context, AirportTrafficContract.View view) {
        mAirportTrafficPresenter = new AirportTrafficPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportTrafficPresenter;
    }
}
