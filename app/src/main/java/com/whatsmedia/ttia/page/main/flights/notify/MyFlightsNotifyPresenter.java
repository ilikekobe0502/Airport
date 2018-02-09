package com.whatsmedia.ttia.page.main.flights.notify;

import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class MyFlightsNotifyPresenter implements MyFlightsNotifyContract.Presenter {
    private final static String TAG = MyFlightsNotifyPresenter.class.getSimpleName();

    private static MyFlightsNotifyPresenter mMyFlightsNotifyPresenter;
    private static ApiConnect mApiConnect;
    private static MyFlightsNotifyContract.View mView;


    public static MyFlightsNotifyPresenter getInstance(Context context, MyFlightsNotifyContract.View view) {
        mMyFlightsNotifyPresenter = new MyFlightsNotifyPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mMyFlightsNotifyPresenter;
    }
}
