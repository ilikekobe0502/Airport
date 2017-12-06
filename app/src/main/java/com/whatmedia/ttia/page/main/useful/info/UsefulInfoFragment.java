package com.whatmedia.ttia.page.main.useful.info;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.UsefulInfo;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UsefulInfoFragment extends BaseFragment implements UsefulInfoContract.View, IOnItemClickListener {
    private static final String TAG = UsefulInfoFragment.class.getSimpleName();

    @BindView(R.id.viewPager_useful)
    ViewPager mViewPagerInfo;
    @BindView(R.id.info_indicator)
    TabLayout mInfoIndicator;
    @BindView(R.id.infoView)
    ImageView mInfoView;

    private IActivityTools.IMainActivity mMainActivity;
    private UsefulInfoContract.Presenter mPresenter;

    private UsefulInfoPagerAdapter mPageAdapter = new UsefulInfoPagerAdapter();

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

        mViewPagerInfo.setAdapter(mPageAdapter);
        mInfoIndicator.setupWithViewPager(mViewPagerInfo, true);
        mPageAdapter.setClickListener(this);
        mViewPagerInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    mInfoView.setImageResource(R.drawable.bg_05);
                }else{
                    mInfoView.setImageResource(R.drawable.bg_05a);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
