package com.whatmedia.ttia.page.main.communication.emergency;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.data.onlyContentData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyCallFragment extends BaseFragment implements EmergencyCallContract.View {

    private static final String TAG = EmergencyCallFragment.class.getSimpleName();

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private EmergencyCallContract.Presenter mPresenter;

    @BindView(R.id.webView)
    WebView mWebView;

    public EmergencyCallFragment() {
        // Required empty public constructor
    }

    public static EmergencyCallFragment newInstance() {
        EmergencyCallFragment fragment = new EmergencyCallFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency_call, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new  EmergencyCallPresenter(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getEmergencyCallAPI();

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        return view;
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLoadingView = (IActivityTools.ILoadingView) context;
            mMainActivity = (IActivityTools.IMainActivity) context;
        } catch (ClassCastException castException) {
            Log.e(TAG, castException.toString());
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void getEmergencyCallSucceed(final onlyContentData onlyContentData) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (onlyContentData != null) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingView.goneLoadingView();
                        mWebView.loadData(onlyContentData.getIcHtml(), "text/html; charset=utf-8", "UTF-8");
                        mWebView.setBackgroundColor(0);
                    }
                });
            } else {
//            mLoadingView.goneLoadingView();
                Log.e(TAG, "Response is error");
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getEmergencyCallFailed(final String message, final int status) {
        Log.d(TAG, message);
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
