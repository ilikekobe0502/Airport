package com.whatmedia.ttia.page.main.home.weather.more;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.component.dialog.MyWeatherDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreWeatherFragment extends BaseFragment implements MoreWeatherContract.View {
    private static final String TAG = MoreWeatherFragment.class.getSimpleName();

    @BindView(R.id.webView)
    WebView mWebView;

    // TODO: 2017/8/12 語言設置！
    private static String mWeatherUrl = "http://125.227.250.187:8867/weather/index.php?region=%s&lang=tw";
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
        View view = inflater.inflate(R.layout.fragemnt_airport_bus, container, false);
        ButterKnife.bind(this, view);

        mPresenter = MoreWeatherPresenter.getInstance(getContext(), this);

        mLoadingView.showLoadingView();
        settingWebView();
        switchRegion();

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
                        MyWeatherDialog dialog = new MyWeatherDialog()
                                .setTitle(getString(R.string.timezone_other_area_dialog_title))
                                .setItemClickListener(new IOnItemClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (view.getTag() != null && view.getTag() instanceof Integer) {
                                            mRegion = (int) view.getTag();
                                            MyWeatherDialog subDialog = MyWeatherDialog.newInstance()
                                                    .setTitle(getString(R.string.timezone_other_area_dialog_title))
                                                    .setRegion((Integer) view.getTag())
                                                    .setItemClickListener(new IOnItemClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (view.getTag() != null && view.getTag() instanceof Integer) {
                                                                mCountry = (int) view.getTag();
                                                                switchRegion();
                                                            } else {
                                                                Log.e(TAG, "view.getTag() is error");
                                                                showMessage(getString(R.string.data_error));
                                                            }
                                                        }
                                                    });
                                            subDialog.show(getActivity().getFragmentManager(), "dialog");
                                        } else {
                                            Log.e(TAG, "view.getTag() is error");
                                            showMessage(getString(R.string.data_error));
                                        }
                                    }
                                });
                        dialog.show(getActivity().getFragmentManager(), "dialog");
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
    private void switchRegion() {
        switch (mRegion) {
            case 0:
                mCodeArray = getResources().getStringArray(R.array.weather_taiwan_code_array);
                break;
            case 1:
                mCodeArray = getResources().getStringArray(R.array.weather_asia_oceania_code_array);
                break;
            case 2:
                mCodeArray = getResources().getStringArray(R.array.weather_america_code_array);
                break;
            case 3:
                mCodeArray = getResources().getStringArray(R.array.weather_eurpo_code_array);
                break;
            case 4:
                mCodeArray = getResources().getStringArray(R.array.weather_china_code_array);
                break;
        }


        showWebView();
    }

    /**
     * Webview Setting;
     */
    private void settingWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

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
        if (mCodeArray.length > mCountry)
            mWebView.loadUrl(String.format(mWeatherUrl, mCodeArray[mCountry]));
        else {
            Log.e(TAG, "mCodeArray.length <= mCountry");
            showMessage(getString(R.string.data_error));
        }
    }
}
