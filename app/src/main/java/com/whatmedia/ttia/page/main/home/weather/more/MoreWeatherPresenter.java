package com.whatmedia.ttia.page.main.home.weather.more;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class MoreWeatherPresenter implements MoreWeatherContract.Presenter {
    private final static String TAG = MoreWeatherPresenter.class.getSimpleName();

    private static MoreWeatherPresenter mMoreWeatherPresenter;
    private static ApiConnect mApiConnect;
    private static MoreWeatherContract.View mView;


    public static MoreWeatherPresenter getInstance(Context context, MoreWeatherContract.View view) {
            mMoreWeatherPresenter = new MoreWeatherPresenter();
            mApiConnect = ApiConnect.getInstance(context);
            mView = view;
        return mMoreWeatherPresenter;
    }
}
