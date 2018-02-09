package com.whatsmedia.ttia.page.main.terminals.info;

import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class TerminalInfoPresenter implements TerminalInfoContract.Presenter {
    private final static String TAG = TerminalInfoPresenter.class.getSimpleName();

    private static TerminalInfoPresenter mTerminalInfoPresenter;
    private static ApiConnect mApiConnect;
    private static TerminalInfoContract.View mView;


    public static TerminalInfoPresenter getInstance(Context context, TerminalInfoContract.View view) {
        mTerminalInfoPresenter = new TerminalInfoPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mTerminalInfoPresenter;
    }
}
