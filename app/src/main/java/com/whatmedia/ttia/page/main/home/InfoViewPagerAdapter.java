package com.whatmedia.ttia.page.main.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.HomeFeature;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.main.flights.info.FlightsInfoFragment;
import com.whatmedia.ttia.page.main.flights.result.FlightsSearchResultFragment;
import com.whatmedia.ttia.page.main.home.arrive.ArriveFlightsFragment;
import com.whatmedia.ttia.page.main.home.departure.DepartureFlightsFragment;
import com.whatmedia.ttia.page.main.home.parking.HomeParkingInfoFragment;
import com.whatmedia.ttia.page.main.home.weather.HomeWeatherInfoFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class InfoViewPagerAdapter extends FragmentPagerAdapter {
    private final static String TAG = InfoViewPagerAdapter.class.getSimpleName();

    private ArriveFlightsFragment.IOnSetCurrentPositionListener mListener;
    private int mTopFrameHeight;

    public InfoViewPagerAdapter(FragmentManager fm, int topFrameHeight) {
        super(fm);
        mTopFrameHeight = topFrameHeight;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0://出發航班
                DepartureFlightsFragment departureFlightsFragment = DepartureFlightsFragment.newInstance();
                departureFlightsFragment.setTopFrameHeight(mTopFrameHeight);
                return departureFlightsFragment;
            case 1://抵達航班
                ArriveFlightsFragment fragment = ArriveFlightsFragment.newInstance();
                if (mListener != null) {
                    fragment.setListener(mListener);
                } else {
                    fragment.setListener(null);
                }
                fragment.setTopFrameHeight(mTopFrameHeight);
                return fragment;
            case 2://停車資訊
                return HomeParkingInfoFragment.newInstance();
            case 3://天氣資訊
                return HomeWeatherInfoFragment.newInstance();
            default:
                return DepartureFlightsFragment.newInstance();
        }
    }

    public void setCurrentPositionListener(ArriveFlightsFragment.IOnSetCurrentPositionListener listener) {
        mListener = listener;
    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//       if (position >= getCount()) {
//            FragmentManager manager = ((Fragment) object).getFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove((Fragment) object);
//            trans.commit();
//        }
//    }
//

}
