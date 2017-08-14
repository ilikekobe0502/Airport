package com.whatmedia.ttia.page.main.communication.emergency;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.EmergenctCallData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyCallFragment extends BaseFragment implements EmergencyCallContract.View{

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

        mPresenter = EmergencyCallPresenter.getInstance(getContext(), this);
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
    public void getEmergencyCallSucceed(final List<EmergenctCallData> response) {
        mLoadingView.goneLoadingView();
        if (response != null && response.size() > 0 && !TextUtils.isEmpty(response.get(0).getEcHtml())) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mLoadingView.goneLoadingView();
                    mWebView.loadData(response.get(0).getEcHtml(), "text/html; charset=utf-8", "UTF-8");
                    mWebView.setBackgroundColor(0);
                }
            });
        } else {
//            mLoadingView.goneLoadingView();
            Log.e(TAG, "Response is error");
        }
    }

    @Override
    public void getEmergencyCallFailed(final String message) {
        Log.d(TAG, message);
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(message);
            }
        });
    }
}
