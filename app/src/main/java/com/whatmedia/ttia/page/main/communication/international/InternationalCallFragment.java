package com.whatmedia.ttia.page.main.communication.international;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.InternationCallData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InternationalCallFragment extends BaseFragment implements InternationalCallContract.View {

    //    @BindView(R.id.text_title1)
//    TextView mTextTitle1;
//    @BindView(R.id.text_title2)
//    TextView mTextTitle2;
    @BindView(R.id.webView)
    WebView mWebView;
//    @BindView(R.id.webView2)
//    WebView mWebView2;

    private static final String TAG = InternationalCallFragment.class.getSimpleName();

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private InternationalCallContract.Presenter mPresenter;

    public InternationalCallFragment() {
        // Required empty public constructor
    }

    public static InternationalCallFragment newInstance() {
        InternationalCallFragment fragment = new InternationalCallFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragemnt_airport_bus, container, false);
        ButterKnife.bind(this, view);

        mPresenter = InternationalCallPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getInternationalCallAPI();

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mWebView2.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mLoadingView.goneLoadingView();
//            }
//        });
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
    public void getInternationalCallSucceed(final List<InternationCallData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (response != null && response.size() > 0) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
//                    mTextTitle1.setText(response.get(0).getIcTitle().trim());
                        mWebView.loadData(response.get(0).getIcHtml(), "text/html; charset=utf-8", "UTF-8");
                        mWebView.setBackgroundColor(Color.TRANSPARENT);
//                    mTextTitle2.setText(response.get(1).getIcTitle().trim());
//                    mWebView2.loadData(response.get(1).getIcHtml(), "text/html; charset=utf-8", "UTF-8");
//                    mWebView2.setBackgroundColor(Color.TRANSPARENT);
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
    public void getInternationalCallFailed(final String message, boolean timeout) {
        Log.d(TAG, message);
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (timeout) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Util.showTimeoutDialog(getContext());
                    }
                });
            } else {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        showMessage(message);
                    }
                });
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
