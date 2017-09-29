package com.whatmedia.ttia.page.main.communication.roaming.detail;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.RoamingDetailData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

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

        mPresenter = RoamingDetailPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getRoamingDetailAPI(getArguments().get("key").toString());


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
    public void getRoamingDetailSucceed(final List<RoamingDetailData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (response != null && response.size() > 0) {
                String image = "";
                if (!TextUtils.isEmpty(response.get(0).getIrHtml()) && response.get(0).getIrHtml().contains("http") && response.get(0).getIrHtml().contains("JPG")) {
                    image = response.get(0).getIrHtml().substring(response.get(0).getIrHtml().indexOf("http"), response.get(0).getIrHtml().indexOf("JPG") + 3);
                }
                final String finalImage = image;
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(finalImage))
                            Picasso.with(getContext()).load(finalImage).into(mImageView);

                        mTextQuery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri uri = Uri.parse(response.get(0).getIrUrl());
                                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(it);
                            }
                        });
                        tool(response.get(0).getTiName());
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
    public void getRoamingDetailFailed(final String message, boolean timeout) {
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

    public void tool(String name) {
        mMainActivity.getMyToolbar().clearState()
                .setTitleText(name)
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
