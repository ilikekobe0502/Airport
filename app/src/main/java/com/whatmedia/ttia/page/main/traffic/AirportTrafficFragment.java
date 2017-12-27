package com.whatmedia.ttia.page.main.traffic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.AirportTraffic;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.terminals.info.TerminalInfoRecyclerViewAdapter;
import com.whatmedia.ttia.utility.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportTrafficFragment extends BaseFragment implements AirportTrafficContract.View, IOnItemClickListener {
    private static final String TAG = AirportTrafficFragment.class.getSimpleName();

    @BindView(R.id.layout_frame)
    RelativeLayout mLayoutFrame;
    @BindView(R.id.viewPager_useful)
    ViewPager mViewPagerInfo;
    @BindView(R.id.info_indicator)
    TabLayout mInfoIndicator;
    @BindView(R.id.infoView)
    ImageView mInfoView;

    private IActivityTools.IMainActivity mMainActivity;
    private AirportTrafficContract.Presenter mPresenter;

    private AirportTrafficPagerAdapter mPageAdapter = new AirportTrafficPagerAdapter();

    private RelativeLayout.LayoutParams mImageParamsFrame;
    private boolean mIsScreen34Mode;

    public AirportTrafficFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AirportTrafficFragment newInstance() {
        AirportTrafficFragment fragment = new AirportTrafficFragment();
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
        View view = inflater.inflate(R.layout.fragment_usefull, container, false);
        ButterKnife.bind(this, view);

        mPresenter = AirportTrafficPresenter.getInstance(getContext(), this);
        mIsScreen34Mode = Preferences.checkScreenIs34Mode(getContext());

        mViewPagerInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mInfoView.setImageResource(R.drawable.bg_04);
                } else {
                    mInfoView.setImageResource(R.drawable.bg_04a);
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
        mInfoView.setImageResource(R.drawable.bg_04);
    }

    private void setIcon(int height) {
        mViewPagerInfo.setAdapter(mPageAdapter);
        mInfoIndicator.setupWithViewPager(mViewPagerInfo, true);
        mPageAdapter.setClickListener(this, height);
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
        if (view.getTag() instanceof AirportTraffic) {
            AirportTraffic info = (AirportTraffic) view.getTag();
            int page = -1;
            switch (info) {
                case TAG_PARKING_INFO://停車資訊
                    page = Page.TAG_PARK_INFO;
                    break;
                case TAG_AIRPORT_BUS://機場巴士
                    page = Page.TAG_AIRPORT_BUS;
                    break;
                case TAG_ROADSIDE_ASSISTANCE://道路救援服務
                    page = Page.TAG_ROADSIDE_ASSISTANCE;
                    break;
                case TAG_TAXI://計程車
                    page = Page.TAG_TAXI;
                    break;
                case TAG_TOUR_BUS://巡迴巴士
                    page = Page.TAG_TOUR_BUS;
                    break;
                case TAG_AIRPORT_MRT://機場捷運/高鐵
                    page = Page.TAG_AIRPORT_MRT;
                    break;
                case TAG_SKY_TRAIN://電車
                    page = Page.TAG_SKY_TRAIN;
                    break;
            }

            if (page != -1)
                mMainActivity.replaceFragment(page, null, true);
        } else {
            Log.e(TAG, "View.getTag() is not instance of FlightInfo");
        }
    }
}
