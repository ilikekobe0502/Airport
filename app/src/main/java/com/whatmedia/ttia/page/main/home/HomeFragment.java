package com.whatmedia.ttia.page.main.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.enums.HomeFeature;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.GetLanguageListResponse;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.IndoorMap.IndoorMapActivity;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.home.arrive.ArriveFlightsFragment;
import com.whatmedia.ttia.page.main.home.moreflights.MoreFlightsContract;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeContract.View, IOnItemClickListener, ViewPager.OnPageChangeListener, ArriveFlightsFragment.IOnSetCurrentPositionListener {
    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.viewPager_info)
    ViewPager mViewPagerInfo;
    @BindView(R.id.viewPager_feature)
    ViewPager mViewPagerFeature;
    @BindView(R.id.tab_indicator)
    TabLayout mTabIndicator;
    @BindView(R.id.tab_info_indicator)
    TabLayout mTabInfoIndicator;
    @BindView(R.id.layout_info)
    RelativeLayout mTabInfoInfo;

    private static final String TAG_MY_FLIGHTS = "1";
    private static final String TAG_SWEET_NOTIFICATION = "2";
    private static final String TAG_NEWS = "3";
    private static final String TAG_URGENT = "4";
    private static final String TAG_ACHIEVEMENT = "5";

    private FeatureViewPagerAdapter mFeatureAdapter = new FeatureViewPagerAdapter();
    private InfoViewPagerAdapter mInfoAdapter;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private HomePresenter mPresenter;
    private int mTopFrameHeight = 0;
    private ArriveFlightsFragment.IOnSetCurrentPositionListener mPositionListener = this;

    @Override
    public void setCurrentPosition(int position) {
        mViewPagerInfo.setCurrentItem(position);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new HomePresenter(getContext(), this);
        mPresenter.getLanguageList();

        mTabInfoIndicator.setupWithViewPager(mViewPagerInfo, true);
        mViewPagerInfo.addOnPageChangeListener(this);
        //Init toolbar
        setDepartureToolbar();

        mViewPagerFeature.setAdapter(mFeatureAdapter);
        mFeatureAdapter.setClickListener(this);
        mTabIndicator.setupWithViewPager(mViewPagerFeature, true);

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(HomeContract.TAG_TYPE))) {
            goToNotificationPage(getArguments().getString(HomeContract.TAG_TYPE));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTabInfoInfo.post(new Runnable() {
            @Override
            public void run() {
                mTopFrameHeight = mTabInfoInfo.getHeight();
                Log.d("TAG", "FrameH = " + mTopFrameHeight);

                mInfoAdapter = new InfoViewPagerAdapter(getChildFragmentManager(), mTopFrameHeight);
                mInfoAdapter.setCurrentPositionListener(mPositionListener);
                mViewPagerInfo.setAdapter(mInfoAdapter);
            }
        });
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
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof HomeFeature) {
            mViewPagerInfo.setCurrentItem(0);
            HomeFeature feature = (HomeFeature) view.getTag();
            switch (feature) {
                case TAG_FLIGHTS_INFO://航班資訊
                    mMainActivity.addFragment(Page.TAG_FIGHTS_INFO, null, true);
                    break;
                case TAG_TERMINAL_INFO://航廈資訊
                    mMainActivity.addFragment(Page.TAG_TERMINAL_INFO, null, true);
                    break;
                case TAG_AIRPORT_TRAFFIC://機場交通
                    mMainActivity.addFragment(Page.TAG_AIRPORT_TRAFFIC, null, true);
                    break;
                case TAG_PRACTICAL_INFO://實用資訊
                    mMainActivity.addFragment(Page.TAG_USERFUL_INFO, null, true);
                    break;
                case TAG_STORE_OFFERS://商店優惠
                    mMainActivity.addFragment(Page.TAG_STORE_OFFERS, null, true);
                    break;
                case TAG_COMMUNICATION_SERVICE://通訊服務
                    mMainActivity.addFragment(Page.TAG_COMMUNICATION_SERVICE, null, true);
                    break;
                case TAG_LANGUAGE_SETTING://語言設定
                    mMainActivity.addFragment(Page.TAG_LANGUAGE_SETTING, null, true);
                    break;
                case TAG_AIRPORT_SECRETARY://機場秘書
                    mMainActivity.addFragment(Page.TAG_AIRPORT_SECRETARY, null, true);
                    break;
//                case TAG_INDOOR_MAP://室內地圖導航
//                    try {
//                        Intent i = new Intent(getContext(), IndoorMapActivity.class);
//                        getActivity().startActivity(i);
//                    } catch (ActivityNotFoundException e) {
//                        e.printStackTrace();
//                        showMessage(getString(R.string.data_error));
//                    }
//                    break;
//                case TAG_AIRPORT_ACHIEVEMENT://機場成就
//                    mMainActivity.addFragment(Page.TAG_ACHIEVEMENT, null, true);
//                    break;
            }
        } else {
            Log.e(TAG, "view.getTag is not instance of HomeFeature");
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0://出發航班
                setDepartureToolbar();
                break;
            case 1://抵達航班
                mMainActivity.getMyToolbar().clearState()
                        .setBackground(ContextCompat.getColor(getContext(), R.color.colorBackgroundHomeArrive))
                        .setBackIcon(ContextCompat.getDrawable(getContext(), R.drawable.refresh))
                        .setLeftText(getString(R.string.tableview_header_arrival, Util.getNowDate(Util.TAG_FORMAT_MD)))
                        .setRightText(getString(R.string.home_more))
                        .setLeftIcon(ContextCompat.getDrawable(getContext(), R.drawable.dow))
                        .setMoreIcon(ContextCompat.getDrawable(getContext(), R.drawable.home_more))
                        .setOnMoreClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewPagerInfo.setCurrentItem(0);
                                Bundle bundle = new Bundle();
                                bundle.putString(MoreFlightsContract.TAG_KIND, FlightsInfoData.TAG_KIND_ARRIVE);
                                mMainActivity.addFragment(Page.TAG_HOME_MORE_FLIGHTS, bundle, true);
                            }
                        }).setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInfoAdapter.notifyDataSetChanged();
                    }
                });

                mMainActivity.setTopViewColor(ContextCompat.getColor(getContext(), R.color.colorMainTop));
                break;
            case 2://停車場資訊
                mMainActivity.getMyToolbar().clearState()
                        .setBackground(ContextCompat.getColor(getContext(), R.color.colorBackgroundHomeParkingInfo))
                        .setBackIcon(ContextCompat.getDrawable(getContext(), R.drawable.refresh))
                        .setLeftText(getString(R.string.title_parking_infomation))
                        .setRightText(getString(R.string.home_more))
                        .setMoreIcon(ContextCompat.getDrawable(getContext(), R.drawable.home_more))
                        .setOnMoreClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewPagerInfo.setCurrentItem(0);
                                mMainActivity.addFragment(Page.TAG_PARK_INFO, null, true);
                            }
                        }).setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInfoAdapter.notifyDataSetChanged();
                    }
                });

                mMainActivity.setTopViewColor(ContextCompat.getColor(getContext(), R.color.colorMainTopSecondary));
                break;
            case 3://天氣資訊
                mMainActivity.getMyToolbar().clearState()
                        .setBackground(ContextCompat.getColor(getContext(), R.color.colorHomeWeather))
                        .setBackIcon(ContextCompat.getDrawable(getContext(), R.drawable.refresh))
                        .setLeftText(getString(R.string.home_weather_title))
                        .setRightText(getString(R.string.home_more))
                        .setMoreIcon(ContextCompat.getDrawable(getContext(), R.drawable.home_more))
                        .setOnMoreClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewPagerInfo.setCurrentItem(0);
                                mMainActivity.addFragment(Page.TAG_HOME_MORE_WEATHER, null, true);
                            }
                        }).setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInfoAdapter.notifyDataSetChanged();
                    }
                });

                mMainActivity.setTopViewColor(ContextCompat.getColor(getContext(), R.color.colorMainTop));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Set Departure toolbar state
     */
    private void setDepartureToolbar() {
        mMainActivity.getMyToolbar().clearState()
                .setBackground(ContextCompat.getColor(getContext(), R.color.colorBackgroundHomeDeparture))
                .setBackIcon(ContextCompat.getDrawable(getContext(), R.drawable.refresh))
                .setLeftText(getString(R.string.tableview_header_takeoff, Util.getNowDate(Util.TAG_FORMAT_MD)))
                .setLeftIcon(ContextCompat.getDrawable(getContext(), R.drawable.up))
                .setRightText(getString(R.string.home_more))
                .setMoreIcon(ContextCompat.getDrawable(getContext(), R.drawable.home_more))
                .setOnMoreClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPagerInfo.setCurrentItem(0);
                        Bundle bundle = new Bundle();
                        bundle.putString(MoreFlightsContract.TAG_KIND, FlightsInfoData.TAG_KIND_DEPARTURE);
                        mMainActivity.addFragment(Page.TAG_HOME_MORE_FLIGHTS, bundle, true);
                    }
                }).setOnBackClickListener(new MyToolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoAdapter.notifyDataSetChanged();
            }
        });

        mMainActivity.setTopViewColor(ContextCompat.getColor(getContext(), R.color.colorMainTop));
    }

    @Override
    public void getLanguageListSuccess(GetLanguageListResponse response) {
    }

    @Override
    public void getLanguageListFailed(String e, boolean timeout) {
        if (!timeout) {
            if (isAdded() && !isDetached()) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.note)
                                .setMessage(R.string.data_not_found)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });
            } else {
                Log.d(TAG, "Fragment is not add");
            }
        }
    }

    @Override
    public void goToNotificationPage(String type) {
        mViewPagerInfo.setCurrentItem(0);
        int page;
        if (TextUtils.equals(type, TAG_MY_FLIGHTS)) {
            page = Page.TAG_MY_FIGHTS_INFO;
        } else if (TextUtils.equals(type, TAG_SWEET_NOTIFICATION)) {
            page = Page.TAG_AIRPORT_SWEET_NOTIFY;
        } else if (TextUtils.equals(type, TAG_NEWS)) {
            page = Page.TAG_AIRPORT_USER_NEWS;
        } else if (TextUtils.equals(type, TAG_URGENT)) {
            page = Page.TAG_AIRPORT_EMERGENCY;
        } else if (TextUtils.equals(type, TAG_ACHIEVEMENT)) {
            page = Page.TAG_ACHIEVEMENT;
        } else {
            return;
        }
        mMainActivity.addFragment(page, null, true);
        getArguments().putString(HomeContract.TAG_TYPE, "");
    }
}