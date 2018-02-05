package com.whatmedia.ttia.page.main.communication.roaming.detail;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.communication.roaming.RoamingServiceContract;
import com.whatmedia.ttia.page.main.communication.roaming.detail.webview.RoamingWebViewContract;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoamingDetailFragment extends BaseFragment implements RoamingDetailContract.View {
    private static final String TAG = RoamingDetailFragment.class.getSimpleName();

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private RoamingDetailContract.Presenter mPresenter;

    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.text_query)
    TextView mTextQuery;
    @BindView(R.id.imageDetailView)
    ImageView mImageDetailView;

    private String mTitle;

    public RoamingDetailFragment() {
        // Required empty public constructor
    }

    public static RoamingDetailFragment newInstance() {
        RoamingDetailFragment fragment = new RoamingDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roaming_detail, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new RoamingDetailPresenter(getContext(), this);
        mLoadingView.showLoadingView();
        if (getArguments() != null) {
            mPresenter.getRoamingDetailAPI(getArguments().get(RoamingServiceContract.ARG_KEY).toString());
            mTitle = !TextUtils.isEmpty(getArguments().getString(RoamingServiceContract.ARG_TITLE)) ? getArguments().getString(RoamingServiceContract.ARG_TITLE) : "";
            tool(mTitle);
        }

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
    public void getRoamingDetailSucceed(final String url, final String image, final String detailImage) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (url != null && image != null) {

                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(image))
                            Util.getHttpsPicasso(getContext()).load(image).into(mImageView);

                        if (!TextUtils.isEmpty(detailImage)) {
                            mImageDetailView.setVisibility(View.VISIBLE);
                            Util.getHttpsPicasso(getContext()).load(detailImage).into(mImageDetailView);
                        } else {
                            mImageDetailView.setVisibility(View.INVISIBLE);
                        }


                        mTextQuery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Uri uri = Uri.parse(url);
//                                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                                startActivity(it);
                                Bundle bundle = new Bundle();
                                bundle.putString(RoamingServiceContract.ARG_TITLE, mTitle);
                                bundle.putString(RoamingWebViewContract.TAG_WEBURL, url);
                                mMainActivity.replaceFragment(Page.TAG_COMMUNICATION_ROAMING_WEBVIEW, bundle, true);
                            }
                        });
                    }
                });
            } else {
                Log.e(TAG, "Response is error");
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getRoamingDetailFailed(final String message, final int status) {
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
                                mMainActivity.backPress();
                                break;
                        }
                    }
                });
    }
}
