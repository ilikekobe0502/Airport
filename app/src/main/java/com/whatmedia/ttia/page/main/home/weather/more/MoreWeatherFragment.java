package com.whatmedia.ttia.page.main.home.weather.more;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreWeatherFragment extends BaseFragment implements MoreWeatherContract.View {
    private static final String TAG = MoreWeatherFragment.class.getSimpleName();

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.layout_selector)
    RelativeLayout mLayoutSelector;
    @BindView(R.id.number_picker_left)
    NumberPicker mNumberPickerLeft;
    @BindView(R.id.number_picker_right)
    NumberPicker mNumberPickerRight;
    @BindView(R.id.layout_ok)
    RelativeLayout mLayoutOk;

    private static String mWeatherUrl = "http://125.227.250.187:8867/weather/index.php?region=%1$s&lang=%2$s";
    private static String mLocale;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private MoreWeatherContract.Presenter mPresenter;

    private int mRegion = 0;
    private int mCountry = 1;
    private String[] mCodeArray;

    public MoreWeatherFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MoreWeatherFragment newInstance() {
        MoreWeatherFragment fragment = new MoreWeatherFragment();
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
        View view = inflater.inflate(R.layout.fragment_more_weather, container, false);
        ButterKnife.bind(this, view);

        mPresenter = MoreWeatherPresenter.getInstance(getContext(), this);

        mLoadingView.showLoadingView();

//        if (TextUtils.isEmpty(mLocale))
            mLocale = Preferences.getLocaleSetting(getContext());

        settingWebView();
        switchRegion();
        showWebView();

        tool();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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

    private void tool() {
        mMainActivity.getMyToolbar().clearState()
                .setTitleText(getString(R.string.home_weather_title))
                .setBackground(ContextCompat.getColor(getContext(), R.color.colorSubTitle))
                .setMoreLayoutVisibility(View.GONE)
                .setRightText(getString(R.string.timezone_other_area))
                .setOnAreaClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLayoutSelector.isShown()) {
                            mLayoutSelector.setVisibility(View.GONE);
                        } else {
                            mLayoutSelector.setVisibility(View.VISIBLE);
                            initLeftPicker();
                        }
                    }
                })
                .setAreaLayoutVisibility(View.VISIBLE)
                .setBackVisibility(View.VISIBLE)
                .setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.imageView_back:
                                mMainActivity.backPress();
                                break;
                        }
                    }
                });
    }

    /**
     * Switch region
     */
    private String[] switchRegion() {
        switch (mRegion) {
            case 0:
                mCodeArray = getResources().getStringArray(R.array.weather_taiwan_code_array);
                return getResources().getStringArray(R.array.weather_taiwan_city_array);
            case 1:
                mCodeArray = getResources().getStringArray(R.array.weather_asia_oceania_code_array);
                return getResources().getStringArray(R.array.weather_asia_oceania_city_array);
            case 2:
                mCodeArray = getResources().getStringArray(R.array.weather_america_code_array);
                return getResources().getStringArray(R.array.weather_america_city_array);
            case 3:
                mCodeArray = getResources().getStringArray(R.array.weather_eurpo_code_array);
                return getResources().getStringArray(R.array.weather_eurpo_city_array);
            case 4:
                mCodeArray = getResources().getStringArray(R.array.weather_china_code_array);
                return getResources().getStringArray(R.array.weather_china_city_array);
            default:
                return new String[0];
        }
    }

    /**
     * Webview Setting;
     */
    private void settingWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadingView.goneLoadingView();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoadingView.goneLoadingView();
            }
        });
    }

    /**
     * Show select webView
     */
    private void showWebView() {
        if (mCodeArray.length > mCountry) {
//            if (TextUtils.equals(mLocale, Locale.TRADITIONAL_CHINESE.toString()))
//                mLocale = "tw";
//            else if (TextUtils.equals(mLocale, Locale.SIMPLIFIED_CHINESE.toString()))
//                mLocale = "cn";
//            mWebView.loadUrl(String.format(mWeatherUrl, mCodeArray[mCountry], mLocale));
            switch (mLocale){
                case "zh_TW":
                    mWebView.loadUrl(String.format(mWeatherUrl, mCodeArray[mCountry], "tw"));
                    break;
                case "zh_CN":
                    mWebView.loadUrl(String.format(mWeatherUrl, mCodeArray[mCountry], "cn"));
                    break;
                case "en":
                    mWebView.loadUrl(String.format(mWeatherUrl, mCodeArray[mCountry], "en"));
                    break;
                case "ja":
                    mWebView.loadUrl(String.format(mWeatherUrl, mCodeArray[mCountry], "jp"));
                    break;
                default:
                    mWebView.loadUrl(String.format(mWeatherUrl, mCodeArray[mCountry], "tw"));
                    break;
            }
        } else {
            Log.e(TAG, "mCodeArray.length <= mCountry");
            showMessage(getString(R.string.data_error));
        }
    }

    /**
     * set Left picker
     */
    private void initLeftPicker() {
        String[] data = getContext().getResources().getStringArray(R.array.weather_region_array);
        Util.setNumberPickerTextColor(mNumberPickerLeft, ContextCompat.getColor(getContext(), android.R.color.white));
        mNumberPickerLeft.setMinValue(0);
        mNumberPickerLeft.setMaxValue(data.length - 1);
        mNumberPickerLeft.setWrapSelectorWheel(false);
        mNumberPickerLeft.setDisplayedValues(data);
        mNumberPickerLeft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", "old = " + oldVal + " new = " + newVal);
                mRegion = newVal;
                setRightPicker();
            }
        });

        setRightPicker();
    }

    /**
     * Set Right picker
     */
    private void setRightPicker() {
        String[] data = switchRegion();
        Util.setNumberPickerTextColor(mNumberPickerRight, ContextCompat.getColor(getContext(), android.R.color.white));
        mNumberPickerRight.setMinValue(0);
        mNumberPickerRight.setMaxValue(0);
        try {
            mNumberPickerRight.setDisplayedValues(data);
        } catch (Exception e) {
            Log.e(TAG, "[mNumberPickerRight.setDisplayedValues(data) error ] " + e.toString());
        }
        mNumberPickerRight.setMaxValue(data.length - 1);
        mNumberPickerRight.setWrapSelectorWheel(false);
        mNumberPickerRight.setValue(0);
        mCountry = 0;
        mNumberPickerRight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCountry = newVal;
            }
        });
    }

    @OnClick(R.id.layout_ok)
    public void onClick() {
        showWebView();
        mLayoutSelector.setVisibility(View.GONE);
    }
}
