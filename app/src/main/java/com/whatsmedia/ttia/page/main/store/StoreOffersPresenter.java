package com.whatsmedia.ttia.page.main.store;

import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class StoreOffersPresenter implements StoreOffersContract.Presenter {
    private final static String TAG = StoreOffersPresenter.class.getSimpleName();

    private static StoreOffersPresenter mFlightsInfoPresenter;
    private static ApiConnect mApiConnect;
    private static StoreOffersContract.View mView;


    public static StoreOffersPresenter getInstance(Context context, StoreOffersContract.View view) {
        mFlightsInfoPresenter = new StoreOffersPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mFlightsInfoPresenter;
    }
}
