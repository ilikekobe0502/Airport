package com.whatsmedia.ttia.page.main.useful.info;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.enums.UsefulInfo;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.utility.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UsefulInfoFragment extends BaseFragment implements UsefulInfoContract.View, IOnItemClickListener {
    private static final String TAG = UsefulInfoFragment.class.getSimpleName();

    @BindView(R.id.layout_frame)
    RelativeLayout mLayoutFrame;
    @BindView(R.id.viewPager_useful)
    ViewPager mViewPagerInfo;
    @BindView(R.id.info_indicator)
    TabLayout mInfoIndicator;
    @BindView(R.id.infoView)
    ImageView mInfoView;

    private IActivityTools.IMainActivity mMainActivity;
    private UsefulInfoContract.Presenter mPresenter;

    private UsefulInfoPagerAdapter mPageAdapter;

    private RelativeLayout.LayoutParams mImageParamsFrame;
    private boolean mIsScreen34Mode;

    public UsefulInfoFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static UsefulInfoFragment newInstance() {
        UsefulInfoFragment fragment = new UsefulInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usefull, container, false);
        ButterKnife.bind(this, view);

        mPresenter = UsefulInfoPresenter.getInstance(getContext(), this);
        mIsScreen34Mode = Preferences.checkScreenIs34Mode(getContext());

        mViewPagerInfo.setAdapter(mPageAdapter);

        mViewPagerInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mInfoView.setImageResource(R.drawable.bg_05);
                } else {
                    mInfoView.setImageResource(R.drawable.bg_05a);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void setImage(int h) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mImageParamsFrame = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        mInfoView.setLayoutParams(mImageParamsFrame);

        setImageSource();

    }

    private void setImageSource() {
        mInfoView.setVisibility(View.VISIBLE);
        mInfoView.setImageResource(R.drawable.bg_05);
    }

    private void setIcon(int height) {
        mPageAdapter = new UsefulInfoPagerAdapter(height);
        mViewPagerInfo.setAdapter(mPageAdapter);
        mInfoIndicator.setupWithViewPager(mViewPagerInfo, true);
        mPageAdapter.setClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsScreen34Mode) {
            mLayoutFrame.post(new Runnable() {
                @Override
                public void run() {
                    int frameLayoutHeight = mLayoutFrame.getHeight();
                    int mLayoutHeight = (int) (frameLayoutHeight * 0.33);
                    setImage(mLayoutHeight);
                    setIcon(frameLayoutHeight - mLayoutHeight);
                }
            });
        } else {
            mInfoView.setScaleType(ImageView.ScaleType.FIT_XY);
            setImageSource();
            setIcon(-1);
        }
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
//            mLoadingView = (IActivityTools.ILoadingView) context;
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
    public void onClick(View view) {
        if (view.getTag() instanceof UsefulInfo) {
            UsefulInfo info = (UsefulInfo) view.getTag();
            int page = -1;
            switch (info) {
                case TAG_LANGUAGE://旅行外文
                    page = Page.TAG_USERFUL_LANGUAGE;
                    break;
                case TAG_CURRENCY://匯率換算
                    page = Page.TAG_USERFUL_CURRENCY_CONVERSION;
                    break;
                case TAG_WEATHER://天氣
                    page = Page.TAG_HOME_MORE_WEATHER;
                    break;
                case TAG_TIMEZONE://時區查詢
                    page = Page.TAG_USERFUL_TIMEZONE;
                    break;
                case TAG_LOST://失物協尋
                    page = Page.TAG_USERFUL_LOST;
                    break;
                case TAG_QUESTIONNAIRE://問卷調查
                    page = Page.TAG_USERFUL_QUEST;
                    break;
            }

            if (page != -1)
                mMainActivity.replaceFragment(page, null, true);
        } else {
            Log.e(TAG, "View.getTag() is not instance of FlightInfo");
        }
    }
}
