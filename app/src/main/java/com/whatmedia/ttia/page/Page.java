package com.whatmedia.ttia.page;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.main.flights.info.FlightsInfoFragment;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoFragment;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyFragment;
import com.whatmedia.ttia.page.main.flights.result.FlightsSearchResultFragment;
import com.whatmedia.ttia.page.main.flights.search.FlightsSearchFragment;
import com.whatmedia.ttia.page.main.home.HomeFragment;
import com.whatmedia.ttia.page.main.home.arrive.ArriveFlightsFragment;
import com.whatmedia.ttia.page.main.home.departure.DepartureFlightsFragment;
import com.whatmedia.ttia.page.main.home.moreflights.MoreFlightsFragment;
import com.whatmedia.ttia.page.main.home.parking.HomeParkingInfoFragment;
import com.whatmedia.ttia.page.main.home.weather.HomeWeatherInfoFragment;
import com.whatmedia.ttia.page.main.home.weather.more.MoreWeatherFragment;
import com.whatmedia.ttia.page.main.secretary.AirportSecretaryFragment;
import com.whatmedia.ttia.page.main.secretary.detail.emergency.EmergencyDetailFragment;
import com.whatmedia.ttia.page.main.secretary.detail.news.NewsDetailFragment;
import com.whatmedia.ttia.page.main.secretary.detail.sweet.SweetNotifyDetailFragment;
import com.whatmedia.ttia.page.main.secretary.emergency.AirportEmergencyFragment;
import com.whatmedia.ttia.page.main.secretary.news.AirportUserNewsFragment;
import com.whatmedia.ttia.page.main.secretary.sweet.AirportSweetNotifyFragment;
import com.whatmedia.ttia.page.main.terminals.facility.AirportFacilityFragment;
import com.whatmedia.ttia.page.main.terminals.facility.detail.FacilityDetailFragment;
import com.whatmedia.ttia.page.main.terminals.info.TerminalInfoFragment;
import com.whatmedia.ttia.page.main.terminals.store.info.StoreSearchInfoFragment;
import com.whatmedia.ttia.page.main.terminals.store.result.StoreSearchResultFragment;
import com.whatmedia.ttia.page.main.terminals.store.search.StoreSearchFragment;
import com.whatmedia.ttia.page.main.terminals.toilet.PublicToiletFragment;
import com.whatmedia.ttia.page.main.traffic.AirportTrafficFragment;
import com.whatmedia.ttia.page.main.traffic.bus.AirportBusFragment;
import com.whatmedia.ttia.page.main.traffic.mrt.AirportMrtFragment;
import com.whatmedia.ttia.page.main.traffic.parking.ParkingInfoFragment;
import com.whatmedia.ttia.page.main.traffic.roadside.RoadsideAssistanceFragment;
import com.whatmedia.ttia.page.main.traffic.skytrain.SkyTrainFragment;
import com.whatmedia.ttia.page.main.traffic.taxi.TaxiFragment;
import com.whatmedia.ttia.page.main.traffic.tourbus.TourBusFragment;

/**
 * Created by neo_mac on 2017/6/20.
 */

public class Page {
    // TODO: Example your page TAG
    public static final int TAG_HOME = 1000;
    public static final int TAG_HOME_DEPARTURE_FLIGHTS = 1001;
    public static final int TAG_HOME_ARRIVE_FLIGHTS = 1002;
    public static final int TAG_HOME_PARKING_INFO = 1003;
    public static final int TAG_HOME_WEATHER_INFO = 1004;
    public static final int TAG_HOME_MORE_FLIGHTS = 1005;
    public static final int TAG_HOME_MORE_WEATHER = 1006;

    public static final int TAG_FIGHTS_INFO = 1101;
    public static final int TAG_FIGHTS_SEARCH = 1102;
    public static final int TAG_FIGHTS_SEARCH_RESULT = 1103;
    public static final int TAG_MY_FIGHTS_INFO = 1104;
    public static final int TAG_MY_FIGHTS_NOTIFY = 1105;

    public static final int TAG_TERMINAL_INFO = 1201;
    public static final int TAG_PUBLIC_TOILET = 1202;
    public static final int TAG_AIRPORT_FACILITY = 1203;
    public static final int TAG_STORE_SEARCH = 1204;
    public static final int TAG_STORE_SEARCH_RESULT = 1205;
    public static final int TAG_STORE_SEARCH_INFO = 1206;
    public static final int TAG_AIRPORT_FACILITY_DETAIL = 1207;

    public static final int TAG_AIRPORT_TRAFFIC = 1301;
    public static final int TAG_AIRPORT_BUS = 1302;
    public static final int TAG_ROADSIDE_ASSISTANCE = 1303;
    public static final int TAG_TAXI = 1304;
    public static final int TAG_TOUR_BUS = 1305;
    public static final int TAG_AIRPORT_MRT = 1306;
    public static final int TAG_SKY_TRAIN = 1307;
    public static final int TAG_PARK_INFO = 1308;

    public static final int TAG_AIRPORT_SECRETARY = 1401;
    public static final int TAG_AIRPORT_USER_NEWS = 1402;
    public static final int TAG_AIRPORT_EMERGENCY = 1403;
    public static final int TAG_AIRPORT_SWEET_NOTIFY = 1404;
    public static final int TAG_AIRPORT_NEWS_DETAIL = 1405;
    public static final int TAG_AIRPORT_EMERGENCY_DETAIL = 1406;
    public static final int TAG_AIRPORT_SWEET_DETAIL = 1407;

    /**
     * Switch AirportEmergencyFragment
     *
     * @param activity
     * @param page
     * @param bundle
     * @param backStack
     */
    public static void switchFragment(AppCompatActivity activity, int page, Bundle bundle, boolean backStack) {
        android.support.v4.app.Fragment fragment = getFragment(page);
        String fragmentName = fragment.getClass().getSimpleName();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layout_container, fragment, fragmentName);
        if (backStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    /**
     * Add AirportEmergencyFragment
     *
     * @param activity
     * @param page
     * @param bundle
     * @param backStack
     */
    public static void addFragment(AppCompatActivity activity, int page, Bundle bundle, boolean backStack) {
        android.support.v4.app.Fragment fragment = getFragment(page);
        String fragmentName = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.layout_container, fragment, fragmentName);
        if (backStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    public static void clearBackStack() {

    }

    public static void setBackStackChangedListener(AppCompatActivity activity, FragmentManager.OnBackStackChangedListener listener) {
        activity.getSupportFragmentManager().addOnBackStackChangedListener(listener);
    }

    private static android.support.v4.app.Fragment getFragment(int page) {
        switch (page) {
            // TODO Example to page
            case TAG_HOME:
                return new HomeFragment().newInstance();
            case TAG_FIGHTS_INFO:
                return new FlightsInfoFragment().newInstance();
            case TAG_FIGHTS_SEARCH:
                return new FlightsSearchFragment().newInstance();
            case TAG_FIGHTS_SEARCH_RESULT:
                return new FlightsSearchResultFragment().newInstance();
            case TAG_MY_FIGHTS_INFO:
                return new MyFlightsInfoFragment().newInstance();
            case TAG_TERMINAL_INFO:
                return new TerminalInfoFragment().newInstance();
            case TAG_PUBLIC_TOILET:
                return new PublicToiletFragment().newInstance();
            case TAG_AIRPORT_FACILITY:
                return new AirportFacilityFragment().newInstance();
            case TAG_STORE_SEARCH:
                return new StoreSearchFragment().newInstance();
            case TAG_STORE_SEARCH_RESULT:
                return new StoreSearchResultFragment().newInstance();
            case TAG_STORE_SEARCH_INFO:
                return new StoreSearchInfoFragment().newInstance();
            case TAG_AIRPORT_TRAFFIC:
                return new AirportTrafficFragment().newInstance();
            case TAG_AIRPORT_BUS:
                return new AirportBusFragment().newInstance();
            case TAG_ROADSIDE_ASSISTANCE:
                return new RoadsideAssistanceFragment().newInstance();
            case TAG_TAXI:
                return new TaxiFragment().newInstance();
            case TAG_TOUR_BUS:
                return new TourBusFragment().newInstance();
            case TAG_AIRPORT_MRT:
                return new AirportMrtFragment().newInstance();
            case TAG_SKY_TRAIN:
                return new SkyTrainFragment().newInstance();
            case TAG_PARK_INFO:
                return new ParkingInfoFragment().newInstance();
            case TAG_AIRPORT_SECRETARY:
                return new AirportSecretaryFragment().newInstance();
            case TAG_HOME_DEPARTURE_FLIGHTS:
                return new DepartureFlightsFragment().newInstance();
            case TAG_HOME_ARRIVE_FLIGHTS:
                return new ArriveFlightsFragment().newInstance();
            case TAG_HOME_PARKING_INFO:
                return new HomeParkingInfoFragment().newInstance();
            case TAG_HOME_WEATHER_INFO:
                return new HomeWeatherInfoFragment().newInstance();
            case TAG_HOME_MORE_FLIGHTS:
                return new MoreFlightsFragment().newInstance();
            case TAG_HOME_MORE_WEATHER:
                return new MoreWeatherFragment().newInstance();
            case TAG_AIRPORT_USER_NEWS:
                return new AirportUserNewsFragment().newInstance();
            case TAG_AIRPORT_EMERGENCY:
                return new AirportEmergencyFragment().newInstance();
            case TAG_AIRPORT_SWEET_NOTIFY:
                return new AirportSweetNotifyFragment().newInstance();
            case TAG_MY_FIGHTS_NOTIFY:
                return new MyFlightsNotifyFragment().newInstance();
            case TAG_AIRPORT_NEWS_DETAIL:
                return new NewsDetailFragment().newInstance();
            case TAG_AIRPORT_EMERGENCY_DETAIL:
                return new EmergencyDetailFragment().newInstance();
            case TAG_AIRPORT_SWEET_DETAIL:
                return new SweetNotifyDetailFragment().newInstance();
            case TAG_AIRPORT_FACILITY_DETAIL:
                return new FacilityDetailFragment().newInstance();
        }

        return null;
    }
}
