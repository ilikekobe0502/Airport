package com.whatmedia.ttia.page.main.home.weather;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class HomeWeatherInfoPresenter implements HomeWeatherInfoContract.Presenter {
    private final static String TAG = HomeWeatherInfoPresenter.class.getSimpleName();

    private static HomeWeatherInfoPresenter mHomeWeatherInfoPresenter;
    private static ApiConnect mApiConnect;
    private static HomeWeatherInfoContract.View mView;


    public static HomeWeatherInfoPresenter getInstance(Context context, HomeWeatherInfoContract.View view) {
        mHomeWeatherInfoPresenter = new HomeWeatherInfoPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mHomeWeatherInfoPresenter;
    }
}
