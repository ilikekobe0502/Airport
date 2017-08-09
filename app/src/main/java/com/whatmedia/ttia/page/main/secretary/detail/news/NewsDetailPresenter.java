package com.whatmedia.ttia.page.main.secretary.detail.news;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private final static String TAG = NewsDetailPresenter.class.getSimpleName();

    private static NewsDetailPresenter mAirportSweetNotifyPresenter;
    private static ApiConnect mApiConnect;
    private static NewsDetailContract.View mView;


    public static NewsDetailPresenter getInstance(Context context, NewsDetailContract.View view) {
        mAirportSweetNotifyPresenter = new NewsDetailPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportSweetNotifyPresenter;
    }
}
