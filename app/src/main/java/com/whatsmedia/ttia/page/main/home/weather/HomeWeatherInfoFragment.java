package com.whatsmedia.ttia.page.main.home.weather;

import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.main.home.arrive.ArriveFlightsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeWeatherInfoFragment extends BaseFragment implements HomeWeatherInfoContract.View {
    private static final String TAG = HomeWeatherInfoFragment.class.getSimpleName();

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.loadingView)
    ProgressBar mFragmentLoading;

    //    private final static String mWeatherUrl = "http://210.241.14.99/weather2/index.php?region=ASI|TW|TW018|TAOYUAN&lang=%s";
//    private final static String mWeatherUrl = "http://125.227.250.187:8867/weather2/index.php?region=ASI|TW|TW018|TAOYUAN&lang=%s";//現在先塞舊的IP
    private final static String mWeatherUrl = "http://210.241.14.99/weather?deviceID=%1$s&cityId=ASI|TW|TW018|TAOYUAN&queryType=2";
//    private static String mLocale;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private HomeWeatherInfoContract.Presenter mPresenter;
    private boolean mLoadingError;//Web view loading error
    private ArriveFlightsFragment.IAPIErrorListener mErrorListener;

    public HomeWeatherInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeWeatherInfoFragment newInstance() {
        HomeWeatherInfoFragment fragment = new HomeWeatherInfoFragment();
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_weather_info, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new HomeWeatherInfoPresenter(getContext(), this);
//        mLoadingView.showLoadingView();
//        if (TextUtils.isEmpty(mLocale))
        mFragmentLoading.setVisibility(View.VISIBLE);
//        mLocale = Preferences.getLocaleSetting(getContext());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mPresenter.getWeatherAPI("ASI|TW|TW018|TAOYUAN");
        //因為語系的字串跟查天氣送的字串不相同，所以我在此轉換一下(bug82)
//        switch (mLocale) {
//            case "zh_TW":
//                mWebView.loadUrl(String.format(mWeatherUrl, "tw"));
//                break;
//            case "zh_CN":
//                mWebView.loadUrl(String.format(mWeatherUrl, "ch"));
//                break;
//            case "en":
//                mWebView.loadUrl(String.format(mWeatherUrl, "en"));
//                break;
//            case "ja":
//                mWebView.loadUrl(String.format(mWeatherUrl, "jp"));
//                break;
//            default:
//                mWebView.loadUrl(String.format(mWeatherUrl, "tw"));
//                break;
//        }
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mLoadingError) {
                    mFragmentLoading.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                mLoadingError = true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoadingError = true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mLoadingError = true;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getDeviceId();
        mLoadingError = false;
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
    public void getDeviceIdSuccess(String deviceId) {
        mWebView.loadUrl(String.format(mWeatherUrl, deviceId));
    }

    @Override
    public void getDeviceIdFailed(String deviceId) {

    }

    public void setmErrorListener(ArriveFlightsFragment.IAPIErrorListener mErrorListener) {
        this.mErrorListener = mErrorListener;
    }
}
