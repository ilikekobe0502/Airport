package com.whatmedia.ttia.page.main.terminals.store.result;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class StoreSearchResultPresenter implements StoreSearchResultContract.Presenter {
    private final static String TAG = StoreSearchResultPresenter.class.getSimpleName();

    private static StoreSearchResultPresenter mStoreSearchPresenter;
    private static ApiConnect mApiConnect;
    private static StoreSearchResultContract.View mView;


    public static StoreSearchResultPresenter getInstance(Context context, StoreSearchResultContract.View view) {
        mStoreSearchPresenter = new StoreSearchResultPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;

        return mStoreSearchPresenter;
    }
}
