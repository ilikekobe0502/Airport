package com.whatmedia.ttia.page.main.traffic.tourbus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.TourBusData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TourBusFragment extends BaseFragment implements TourBusContract.View {
    private static final String TAG = TourBusFragment.class.getSimpleName();

    @BindView(R.id.webView)
    WebView mWebView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private TourBusContract.Presenter mPresenter;

    public TourBusFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TourBusFragment newInstance() {
        TourBusFragment fragment = new TourBusFragment();
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

        mPresenter = TourBusPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getTourBusAPI();
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadingView.goneLoadingView();
            }
        });
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

    @Override
    public void getTourBusSucceed(final List<TourBusData> response) {
        if (response != null && response.size() > 0 && !TextUtils.isEmpty(response.get(0).getShuttlesHtml())) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {

                    mWebView.loadData(response.get(0).getShuttlesHtml(), "text/html; charset=utf-8", "UTF-8");
                    mWebView.setBackgroundColor(0);
                }
            });
        } else {
            mLoadingView.goneLoadingView();
            Log.e(TAG, "response Error");
        }
    }

    @Override
    public void getTourBusFailed(final String message) {
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
