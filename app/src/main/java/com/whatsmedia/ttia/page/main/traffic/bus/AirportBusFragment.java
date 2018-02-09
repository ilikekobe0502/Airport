package com.whatsmedia.ttia.page.main.traffic.bus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.component.MyToolbar;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.enums.LanguageSetting;
import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.utility.Preferences;
import com.whatsmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportBusFragment extends BaseFragment implements AirportBusContract.View {
    private static final String TAG = AirportBusFragment.class.getSimpleName();

    @BindView(R.id.webView)
    WebView mWebView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AirportBusContract.Presenter mPresenter;
    private final static String TAG_URL = "http://www.taoyuan-airport.com/chinese/Buses";
    private final static String TAG_URL_JAPANESE = "http://www.taoyuan-airport.com/japanese/Buses";
    private final static String TAG_URL_ENGLISH = "http://www.taoyuan-airport.com/english/Buses";
    private boolean mLoadError;//WebView load error

    public AirportBusFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AirportBusFragment newInstance() {
        AirportBusFragment fragment = new AirportBusFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragemnt_airport_bus, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new AirportBusPresenter(getContext(), this);

        mMainActivity.getMyToolbar().clearState()
                .setTitleText(getString(R.string.title_airport_bus))
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

        mLoadingView.showLoadingView();
//        mPresenter.getAirportBusAPI();

        String localeCache = Preferences.getLocaleSetting(getContext());
        if (TextUtils.equals(localeCache, LanguageSetting.TAG_JAPANESE.getLocale().toString())) {
            mWebView.loadUrl(TAG_URL_JAPANESE);
        } else if (TextUtils.equals(localeCache, LanguageSetting.TAG_ENGLISH.getLocale().toString())) {
            mWebView.loadUrl(TAG_URL_ENGLISH);
        } else {
            mWebView.loadUrl(TAG_URL);
        }

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mLoadError) {
                    mWebView.setVisibility(View.VISIBLE);
                    mLoadingView.goneLoadingView();
                } else {
                    if (getContext() != null && isAdded() && !isDetached())
                        Util.showTimeoutDialog(getContext());
                }
                Log.d(TAG, "onPageFinished");
            }

//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                super.onReceivedHttpError(view, request, errorResponse);
//                Log.e(TAG, "ERROR = " + errorResponse);
//                mLoadError = true;
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//
//                Log.e(TAG, "ERROR code = " + errorCode);
//                Log.e(TAG, "ERROR description = " + description);
//                mLoadError = true;
//            }
        });
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
    public void onPause() {
        mMainActivity.setWebView(null);
        super.onPause();
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

    @Override
    public void getAirportBusSucceed(final BaseTrafficInfoData response) {
        if (isAdded() && !isDetached()) {
            if (!TextUtils.isEmpty(response.getContent())) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.setVisibility(View.VISIBLE);
                        mWebView.loadData(response.getContent(), "text/html; charset=utf-8", "UTF-8");
                        mWebView.setBackgroundColor(0);
                    }
                });
            } else {
                mLoadingView.goneLoadingView();
                Log.e(TAG, "Response is error");
            }
        } else {
            mLoadingView.goneLoadingView();
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getAirportBusFailed(final String message, final int status) {
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
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            if (getContext() != null && isAdded() && !isDetached())
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
