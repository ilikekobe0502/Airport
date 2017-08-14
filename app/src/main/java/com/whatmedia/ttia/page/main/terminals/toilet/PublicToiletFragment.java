package com.whatmedia.ttia.page.main.terminals.toilet;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicToiletFragment extends BaseFragment implements PublicToiletContract.View {
    private static final String TAG = PublicToiletFragment.class.getSimpleName();
    @BindView(R.id.textView_subtitle)
    TextView mTextViewSubtitle;
    @BindView(R.id.imageView_left)
    ImageView mImageViewLeft;
    @BindView(R.id.imageView_right)
    ImageView mImageViewRight;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.webView_terminal_two)
    WebView mWebViewTerminalTwo;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private PublicToiletContract.Presenter mPresenter;

    private AirportFacilityData mTerminalOne;
    private AirportFacilityData mTerminalTwo;

    public PublicToiletFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PublicToiletFragment newInstance() {
        PublicToiletFragment fragment = new PublicToiletFragment();
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
        View view = inflater.inflate(R.layout.fragment_public_toilet, container, false);
        ButterKnife.bind(this, view);

        mPresenter = PublicToiletPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getPublicToiletAPI();

        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setBackgroundColor(0);
        mWebView.setInitialScale(100);
        mWebViewTerminalTwo.getSettings().setBuiltInZoomControls(true);
        mWebViewTerminalTwo.getSettings().setDisplayZoomControls(false);
        mWebViewTerminalTwo.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebViewTerminalTwo.setBackgroundColor(0);
        mWebViewTerminalTwo.setInitialScale(100);
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
    public void getPublicToiletSucceed(final List<AirportFacilityData> response) {
        mLoadingView.goneLoadingView();
        if (response != null) {
            mTerminalOne = response.size() > 0 ? response.get(0) : new AirportFacilityData();
            mTerminalTwo = response.size() >= 1 ? response.get(1) : new AirportFacilityData();

            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {

                    if (!TextUtils.isEmpty(mTerminalOne.getContent())) {
                        mWebView.loadData(mTerminalOne.getContent(), "text/html; charset=utf-8", "UTF-8");
                    } else {
                        Log.e(TAG, "mTerminalTwo.getContent() is error");
                        showMessage(getString(R.string.data_error));
                    }

                    if (!TextUtils.isEmpty(mTerminalTwo.getContent())) {
                        mWebViewTerminalTwo.loadData(mTerminalTwo.getContent(), "text/html; charset=utf-8", "UTF-8");
                    } else {
                        Log.e(TAG, "mTerminalTwo.getContent() is error");
                        showMessage(getString(R.string.data_error));
                    }
                    setLeftView();
                }
            });
        } else {
            Log.e(TAG, "GetPublicToilet response is error");
            showMessage(getString(R.string.data_error));
        }
    }

    @Override
    public void getPublicFailed(final String message) {
        Log.e(TAG, "message: " + message);
        mLoadingView.showLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(message);
            }
        });
    }

    @OnClick({R.id.imageView_left, R.id.imageView_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_left:
                mImageViewLeft.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_on));
                mImageViewRight.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_off));

                setLeftView();
                break;
            case R.id.imageView_right:
                mImageViewLeft.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_off));
                mImageViewRight.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_on));

                setRightView();
                break;
        }
    }

    /**
     * Set left view
     */
    private void setLeftView() {
        mWebViewTerminalTwo.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
        mTextViewSubtitle.setText(!TextUtils.isEmpty(mTerminalOne.getTerminalsName()) ? mTerminalOne.getTerminalsName() : "");
    }

    /**
     * Set right view
     */
    private void setRightView() {
        mWebViewTerminalTwo.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.GONE);
        mTextViewSubtitle.setText(!TextUtils.isEmpty(mTerminalTwo.getTerminalsName()) ? mTerminalTwo.getTerminalsName() : "");
    }
}
