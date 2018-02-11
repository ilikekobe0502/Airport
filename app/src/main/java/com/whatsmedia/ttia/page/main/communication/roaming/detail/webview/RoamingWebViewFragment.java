package com.whatsmedia.ttia.page.main.communication.roaming.detail.webview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.component.MyToolbar;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.main.communication.roaming.RoamingServiceContract;
import com.whatsmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoamingWebViewFragment extends BaseFragment implements RoamingWebViewContract.View {
    private static final String TAG = RoamingWebViewFragment.class.getSimpleName();

    @BindView(R.id.webView)
    WebView mWebView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private RoamingWebViewContract.Presenter mPresenter;
    private boolean mLoadError;//WebView load error
    private boolean mURLError;//URL error

    public RoamingWebViewFragment() {
        // Required empty public constructor
    }

    public static RoamingWebViewFragment newInstance() {
        RoamingWebViewFragment fragment = new RoamingWebViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragemnt_airport_bus, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new RoamingWebViewPresenter(getContext(), this);
        mLoadingView.showLoadingView();

        tool(getArguments().getString(RoamingServiceContract.ARG_TITLE));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mLoadError && !mURLError) {
                    mLoadingView.goneLoadingView();
                    mWebView.setVisibility(View.VISIBLE);
                } else {
                    if (getContext() != null && isAdded() && !isDetached()) {
                        if (mURLError) {
                            Util.showDialog(getContext(), getString(R.string.data_error));
                        } else {
                            Util.showTimeoutDialog(getContext());
                        }
                    }
                }
                Log.d(TAG, "onPageFinished");
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.e(TAG, "ERROR = " + errorResponse);
                if (errorResponse.toString().contains("ERR_NAME_NOT_RESOLVED")) {
                    mURLError = true;
                }
                mLoadError = true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Log.e(TAG, "ERROR code = " + errorCode);
                Log.e(TAG, "ERROR description = " + description);
                if (description.contains("ERR_NAME_NOT_RESOLVED")) {
                    mURLError = true;
                }
                mLoadError = true;
            }
        });

        if (getArguments() != null && getArguments().containsKey(RoamingWebViewContract.TAG_WEBURL)) {
            final String url = getArguments().getString(RoamingWebViewContract.TAG_WEBURL, "");
            if (!TextUtils.isEmpty(url)) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl(url);
                        mWebView.setBackgroundColor(0);
                    }
                });
            } else {
                mLoadingView.goneLoadingView();
            }
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        mMainActivity.setWebView(mWebView);
        mLoadError = false;
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
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
    public void onDetach() {
        super.onDetach();
    }

    public void tool(String name) {
        mMainActivity.getMyToolbar().clearState()
                .setTitleText(name)
                .setToolbarBackground(ContextCompat.getDrawable(getContext(), R.drawable.toolbar_top_bg))
                .setBackVisibility(View.VISIBLE)
                .setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.imageView_back:
                                if (mWebView.canGoBack())
                                    mWebView.goBack();
                                else
                                    mMainActivity.backPress();
                                break;
                        }
                    }
                });
    }
}
