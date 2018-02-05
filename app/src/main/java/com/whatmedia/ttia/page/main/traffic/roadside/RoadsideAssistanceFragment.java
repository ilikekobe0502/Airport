package com.whatmedia.ttia.page.main.traffic.roadside;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoadsideAssistanceFragment extends BaseFragment implements RoadsideAssistanceContract.View {
    private static final String TAG = RoadsideAssistanceFragment.class.getSimpleName();

    @BindView(R.id.webView)
    WebView mWebView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private RoadsideAssistanceContract.Presenter mPresenter;
    private boolean mLoadError;//WebView load error

    public RoadsideAssistanceFragment() {
        // Required empty public constructor
    }

    public static RoadsideAssistanceFragment newInstance() {
        RoadsideAssistanceFragment fragment = new RoadsideAssistanceFragment();
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

        mPresenter = new RoadsideAssistancePresenter(getContext(), this);

        mMainActivity.getMyToolbar().clearState()
                .setTitleText(getString(R.string.title_road_rescue))
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
        mPresenter.getRoadsideAssistanceAPI();
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    view.reload();
                    return true;
                }

                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    view.reload();
                    return true;
                }

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                mWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
//                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
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

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.e(TAG, "ERROR = " + errorResponse);
                mLoadError = true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Log.e(TAG, "ERROR code = " + errorCode);
                Log.e(TAG, "ERROR description = " + description);
                mLoadError = true;
            }
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
    public void getRoadsideAssistanceSucceed(final BaseTrafficInfoData response) {
        if (isAdded() && !isDetached()) {
            if (response != null && !TextUtils.isEmpty(response.getContent())) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadData(response.getContent(), "text/html; charset=utf-8", "UTF-8");
                        mWebView.setBackgroundColor(0);
                    }
                });
            } else {
                mLoadingView.goneLoadingView();
                Log.e(TAG, "response Error");
            }
        } else {
            mLoadingView.goneLoadingView();
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getRoadsideAssistanceFailed(final String message, final int status) {
        Log.d(TAG, message);
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
