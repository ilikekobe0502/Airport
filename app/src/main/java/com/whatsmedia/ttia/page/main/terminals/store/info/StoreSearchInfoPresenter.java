package com.whatsmedia.ttia.page.main.terminals.store.info;

import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class StoreSearchInfoPresenter implements StoreSearchInfoContract.Presenter {
    private final static String TAG = StoreSearchInfoPresenter.class.getSimpleName();

    private static StoreSearchInfoPresenter mStoreSearchInfoPresenter;
    private static ApiConnect mApiConnect;
    private static StoreSearchInfoContract.View mView;


    public static StoreSearchInfoPresenter getInstance(Context context, StoreSearchInfoContract.View view) {
        mStoreSearchInfoPresenter = new StoreSearchInfoPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mStoreSearchInfoPresenter;
    }
}
