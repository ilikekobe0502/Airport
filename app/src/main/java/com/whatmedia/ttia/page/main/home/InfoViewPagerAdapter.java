package com.whatmedia.ttia.page.main.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.whatmedia.ttia.page.main.home.arrive.ArriveFlightsFragment;
import com.whatmedia.ttia.page.main.home.departure.DepartureFlightsFragment;
import com.whatmedia.ttia.page.main.home.parking.HomeParkingInfoFragment;
import com.whatmedia.ttia.page.main.home.weather.HomeWeatherInfoFragment;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class InfoViewPagerAdapter extends FragmentPagerAdapter {
    private final static String TAG = InfoViewPagerAdapter.class.getSimpleName();

    private ArriveFlightsFragment.IOnSetCurrentPositionListener mListener;
    private ArriveFlightsFragment.IAPIErrorListener mErrorListener;

    public InfoViewPagerAdapter(FragmentManager fm) {
        super(fm);
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
                if (mErrorListener != null) {
                    departureFlightsFragment.setErrorListener(mErrorListener);
                } else {
                    departureFlightsFragment.setErrorListener(null);
                }
                return departureFlightsFragment;
            case 1://抵達航班
                ArriveFlightsFragment fragment = ArriveFlightsFragment.newInstance();
                if (mListener != null) {
                    fragment.setListener(mListener);
                } else {
                    fragment.setListener(null);
                }

                if (mErrorListener != null) {
                    fragment.setErrorListener(mErrorListener);
                } else {
                    fragment.setErrorListener(null);
                }
                return fragment;
            case 2://停車資訊
                HomeParkingInfoFragment homeParkingInfoFragment = HomeParkingInfoFragment.newInstance();
                if (mErrorListener != null) {
                    homeParkingInfoFragment.setErrorListener(mErrorListener);
                } else {
                    homeParkingInfoFragment.setErrorListener(null);
                }
                return homeParkingInfoFragment;
            case 3://天氣資訊
                HomeWeatherInfoFragment homeWeatherInfoFragment = HomeWeatherInfoFragment.newInstance();
                if (mErrorListener != null) {
                    homeWeatherInfoFragment.setmErrorListener(mErrorListener);
                } else {
                    homeWeatherInfoFragment.setmErrorListener(null);
                }
                return homeWeatherInfoFragment;
            default:
                return DepartureFlightsFragment.newInstance();
        }
    }

    public void setCurrentPositionListener(ArriveFlightsFragment.IOnSetCurrentPositionListener listener) {
        mListener = listener;
    }

    public void setErrorListener(ArriveFlightsFragment.IAPIErrorListener listener) {
        mErrorListener = listener;
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
