package com.whatmedia.ttia.page.main.terminals.toilet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.data.ToiletFacilityListData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicToiletFragment extends BaseFragment implements PublicToiletContract.View, ViewPager.OnPageChangeListener {
    private static final String TAG = PublicToiletFragment.class.getSimpleName();
    @BindView(R.id.textView_subtitle)
    TextView mTextViewSubtitle;
    @BindView(R.id.imageView_left)
    ImageView mImageViewLeft;
    @BindView(R.id.imageView_right)
    ImageView mImageViewRight;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private PublicToiletContract.Presenter mPresenter;

    private PublicToiletViewPagerAdapter mAdapter;

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

        mPresenter = new PublicToiletPresenter(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getPublicToiletAPI();

        mAdapter = new PublicToiletViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        setLeftView();
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
    public void getPublicToiletSucceed(final List<ToiletFacilityListData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (response != null) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(response);
                    }
                });
            } else {
                Log.e(TAG, "GetPublicToilet response is error");
                showMessage(getString(R.string.data_error));
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getPublicFailed(final String message, final int status) {
        Log.e(TAG, "message: " + message);
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
                            Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @OnClick({R.id.imageView_left, R.id.imageView_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_left:
                setLeftImageState();
                mViewPager.setCurrentItem(0, true);
                setSubTitle(0);
                break;
            case R.id.imageView_right:
                setLRightImageState();

                mViewPager.setCurrentItem(1, true);
                setSubTitle(1);
                break;
        }
    }

    private void setLeftImageState() {
        mImageViewLeft.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_on));
        mImageViewRight.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_off));
    }

    private void setLRightImageState() {
        mImageViewLeft.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_off));
        mImageViewRight.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_on));
    }

    /**
     * Set left view
     */
    private void setLeftView() {
        mViewPager.setCurrentItem(0, true);
//        mWebViewTerminalTwo.setVisibility(View.GONE);
//        mWebView.setVisibility(View.VISIBLE);
        mTextViewSubtitle.setText(getString(R.string.terminal_1));
    }

    /**
     * Set right view
     */
    private void setRightView() {

        mViewPager.setCurrentItem(1, true);
//        mWebViewTerminalTwo.setVisibility(View.VISIBLE);
//        mWebView.setVisibility(View.GONE);
        mTextViewSubtitle.setText(getString(R.string.terminal_2));
    }

    private void setSubTitle(int position) {
        if (mAdapter != null) {
            mTextViewSubtitle.setText(mAdapter.getSubTitle(position));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSubTitle(position);
        if (position == 0) {
            setLeftImageState();
        } else {
            setLRightImageState();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
